/*
Copyright 2009 David Revell

This file is part of SwiFTP.

SwiFTP is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

SwiFTP is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with SwiFTP.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.youzuo.ftptest.server;

import android.util.Log;

import com.youzuo.ftptest.ftp.FsSettings;
import com.youzuo.ftptest.ftp.Util;


/**
 * 设置用户名和密码 还有文件夹目录
 */
public class CmdPASS extends FtpCmd implements Runnable {
    private static final String TAG = CmdPASS.class.getSimpleName();
    /**
     * PassWordType true 不需要验证
     * false 需要验证
     */
    private boolean PassWordType = false;
    String input;

    public CmdPASS(SessionThread sessionThread, String input) {
        super(sessionThread);
        this.input = input;
    }

    @Override
    public void run() {
        Log.d(TAG, "Executing PASS");
        String attemptPassword = getParameter(input, true); // silent
        // Always first USER command, then PASS command
        String attemptUsername = sessionThread.getUserName();
        Log.e("attemptUsername", attemptUsername);
        if (attemptUsername == null) {
            sessionThread.writeString("503 Must send USER first\r\n");
            return;
        }
        if (attemptUsername.equals("anonymous") && PassWordType) {
            Log.e("1111", "11111");
            sessionThread.writeString("230 Guest login ok, read only access.\r\n");
            sessionThread.authAttempt(true);
            sessionThread.setChrootDir("/sdcard/ftp");
            return;
        }
        /**ftp密码*/
        FtpUser user = new FtpUser("admin", "admin", "/sdcard/ftp");
        if (user == null) {

            Log.e("1111", "22222");
            Log.i(TAG, "Failed authentication, username does not exist!");
            Util.sleepIgnoreInterrupt(1000); // sleep to foil brute force attack
            sessionThread.writeString("500 Login incorrect.\r\n");
            sessionThread.authAttempt(false);
        } else if (user.getPassword().equals(attemptPassword)) {
            Log.e("1111", "33333");
            Log.i(TAG, "User " + user.getUsername() + " password verified");
            sessionThread.writeString("230 Access granted\r\n");
            sessionThread.authAttempt(true);
            sessionThread.setChrootDir(user.getChroot());
        } else {
            Log.e("1111", "44444");
            Log.i(TAG, "Failed authentication, incorrect password");
            Util.sleepIgnoreInterrupt(1000); // sleep to foil brute force attack
            sessionThread.writeString("530 Login incorrect.\r\n");
            sessionThread.authAttempt(false);
        }
    }
}
