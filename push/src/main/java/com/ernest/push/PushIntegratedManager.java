package com.ernest.push;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ernest.push.target.getui.GIntentService;
import com.ernest.push.target.getui.GPushService;
import com.ernest.push.util.ApplicationUtil;
import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.common.handler.ConnectHandler;
import com.huawei.android.hms.agent.push.handler.GetTokenHandler;
import com.igexin.sdk.PushManager;
import com.xiaomi.mipush.sdk.MiPushClient;


public class PushIntegratedManager {


    //    private static List<IPushCallback> pushCallbacks = new ArrayList<IPushCallback>();
    private static IPushCallback pushCallback;

    public static void registerPushCallback(IPushCallback callback) {
        pushCallback = callback;
    }

    public static void callBack(Intent intent) {
        if (pushCallback != null) {
            pushCallback.onReceive(intent);
        }
    }


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
            mTarget = TARGET_HUAWEI;
            HMSAgent.init(context);
        } else if (MARK.contains("xiaomi")) {
            mTarget = TARGET_XIAOMI;
            initXiaomiPushInApplication(context);
        } else if (MARK.contains("oppo")) {
        } else if (MARK.contains("vivo")) {
        } else if (MARK.contains("samsung")) {
        } else if (MARK.contains("smartisan")) {
        }
    }

    public void initInMainActivity(Activity activity, IPushCallback callback) {
        registerPushCallback(callback);
        switch (mTarget) {
            case TARGET_GETUI:
                PushManager.getInstance().initialize(activity.getApplicationContext(), GPushService.class);
                PushManager.getInstance().registerPushIntentService(activity, GIntentService.class);
                break;
            case TARGET_HUAWEI:
                initHuaweiPushInActivity(activity);

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

    public void initXiaomiPushInApplication(Context context) {
        String appId = ApplicationUtil.getMetaData(context, "XMPUSH_APPID").replace(" ", "");
        String appKey = ApplicationUtil.getMetaData(context, "XMPUSH_APPKEY").replace(" ", "");
        MiPushClient.registerPush(context, appId, appKey);
    }

    public int getTarget() {
        return mTarget;
    }
}
