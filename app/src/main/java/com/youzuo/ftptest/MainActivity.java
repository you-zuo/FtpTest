package com.youzuo.ftptest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.youzuo.ftptest.ftp.FsService;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);

        /**启动服务*/
        if (FsService.isConnectedToLocalNetwork()) {
            startService(new Intent(this, FsService.class));

        }
        /**终止服务*/
        textView.setOnClickListener(view -> stopService(new Intent(this, FsService.class)));
        try {
            Log.e("eee", FsService.getLocalInetAddress().getHostAddress());
        } catch (Exception e) {
            Log.e("EEEEE", e + "");
        }
    }
}
