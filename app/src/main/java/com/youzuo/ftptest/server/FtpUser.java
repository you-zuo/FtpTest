package com.youzuo.ftptest.server;

import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;

public class FtpUser {

    final private String mUsername;
    final private String mPassword;
    final private String mChroot;

    public FtpUser(@NonNull String username, @NonNull String password, @NonNull String chroot) {
        mUsername = username;
        mPassword = password;

        final File rootPath = new File(chroot);
        mChroot = rootPath.isDirectory() ? chroot : Environment.getRootDirectory().getPath();
        Log.e("Ftp", rootPath.isDirectory() + "");
        Log.e("Ftp", chroot);
        Log.e("Ftp", Environment.getRootDirectory().getPath());

    }

    public String getUsername() {
        return mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getChroot() {
        return mChroot;
    }
}
