package com.bearever.pushdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ernest.push.PushTargetManager;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PushTargetManager.getInstance().initHuaweiPush(this);
    }
}
