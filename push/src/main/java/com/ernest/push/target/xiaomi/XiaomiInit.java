package com.ernest.push.target.xiaomi;

import android.app.Application;

import com.ernest.push.BuildConfig;
import com.ernest.push.util.ApplicationUtil;

/**
 * 小米推送的初始化
 * Created by luoming on 2018/5/28.
 */

public class XiaomiInit {
    private static final String TAG = "mipush";

    public XiaomiInit(Application context) {
        //注册SDK
        String appId = ApplicationUtil.getMetaData(context, "XMPUSH_APPID");
        String appKey = ApplicationUtil.getMetaData(context, "XMPUSH_APPKEY");
//        MiPushClient.registerPush(context, appId.replace(" ", ""), appKey.replace(" ", ""));

        //调试
        if (BuildConfig.DEBUG) {
//            LoggerInterface newLogger = new LoggerInterface() {
//                @Override
//                public void setTag(String tag) {
//                    // ignore
//                }
//
//                @Override
//                public void log(String content, Throwable t) {
//                    Log.d(TAG, content, t);
//                }
//
//                @Override
//                public void log(String content) {
//                    Log.d(TAG, content);
//                }
//            };
//            Logger.setLogger(context, newLogger);
        }
    }

}
