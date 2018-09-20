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

    private static final String BRAND_HUAWEI = "HUAWEI"; //华为
    private static final String BRAND_XIAOMI = "Xiaomi"; //小米

    public static final int TARGET_GETUI = 0;
    public static final int TARGET_HUAWEI = 1;
    public static final int TARGET_XIAOMI = 2;

    public int mTarget;

    public static PushIntegratedManager getInstance() {
        if (instance == null) {
            instance = new PushIntegratedManager();
        }
        return instance;
    }

    public void initInApplication(Application context) {
        String mobile_brand = android.os.Build.MANUFACTURER;
        if (BRAND_HUAWEI.equals(mobile_brand)) {
            mTarget = TARGET_HUAWEI;
            HMSAgent.init(context);
        } else {
            mTarget = TARGET_GETUI;
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
