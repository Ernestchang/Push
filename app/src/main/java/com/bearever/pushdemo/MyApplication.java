package com.bearever.pushdemo;

import android.app.Application;

import com.ernest.push.PushIntegratedManager;


/**
 * Created by luoming on 2018/5/28.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        PushIntegratedManager.getInstance().initInApplication(this);
    }
}
