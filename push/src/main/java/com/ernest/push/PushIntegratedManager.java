package com.ernest.push;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.ernest.push.target.getui.GIntentService;
import com.ernest.push.target.getui.GPushService;
import com.ernest.push.target.huawei.HuaweiPushRevicer;
import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.common.handler.ConnectHandler;
import com.huawei.android.hms.agent.push.handler.GetTokenHandler;
import com.igexin.sdk.PushManager;


public class PushIntegratedManager {

    private static PushIntegratedManager instance;


    public static final int TARGET_GETUI = 0;
    public static final int TARGET_HUAWEI = 1;
    public static final int TARGET_XIAOMI = 2;
    public static final int TARGET_OPPO = 3;
    public static final int TARGET_VIVO = 4;

    public int mTarget;

    public static PushIntegratedManager getInstance() {
        if (instance == null) {
            instance = new PushIntegratedManager();
        }
        return instance;
    }

    public void initInApplication(Application context) {
        String MARK = android.os.Build.MANUFACTURER.toLowerCase();
        mTarget = TARGET_GETUI;

        if (MARK.contains("huawei")) {
//            mTarget = TARGET_HUAWEI;
//            HMSAgent.init(context);
        } else if (MARK.contains("xiaomi")) {
        } else if (MARK.contains("oppo")) {
        } else if (MARK.contains("vivo")) {
        } else if (MARK.contains("samsung")) {
        } else if (MARK.contains("smartisan")) {
        }
    }

    public void initInMainActivity(Activity activity, IPushCallback callback) {

        switch (mTarget) {
            case TARGET_GETUI:
                GIntentService.registerPushCallback(callback);
                PushManager.getInstance().initialize(activity.getApplicationContext(), GPushService.class);
                PushManager.getInstance().registerPushIntentService(activity, GIntentService.class);
                break;
            case TARGET_HUAWEI:
                HuaweiPushRevicer.registerPushCallback(callback);
                initHuaweiPushInActivity(activity);

                break;
            case TARGET_XIAOMI:
                break;
        }
    }


    public void registerPushCallback(IPushCallback callback) {
        switch (mTarget) {
            case TARGET_GETUI:
                GIntentService.registerPushCallback(callback);
                break;
            case TARGET_HUAWEI:
                HuaweiPushRevicer.registerPushCallback(callback);
                break;
            case TARGET_XIAOMI:
                break;
        }

    }


    public void initHuaweiPushInActivity(Activity activity) {
        HMSAgent.connect(activity, new ConnectHandler() {
            @Override
            public void onConnect(int rst) {
                Log.e("ernest", "connect:" + rst);
                if (rst == HMSAgent.AgentResultCode.HMSAGENT_SUCCESS) {
                    //连接成功就获取token和设置打开推送等
                    HMSAgent.Push.getToken(new GetTokenHandler() {
                        @Override
                        public void onResult(int rst) {
                            Log.e("ernest", "getToken:" + rst);
                        }
                    });
//                    HMSAgent.Push.enableReceiveNormalMsg(true, null);
//                    HMSAgent.Push.enableReceiveNotifyMsg(true,null);
                }
            }
        });
    }

    public int getTarget() {
        return mTarget;
    }
}
