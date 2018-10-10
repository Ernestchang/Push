package com.ernest.push;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.ernest.push.target.getui.GIntentService;
import com.ernest.push.target.getui.GPushService;
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

    public void initInApplication(Application context, IPushCallback callback) {
        registerPushCallback(callback);
        String MARK = android.os.Build.MANUFACTURER.toLowerCase();
        mTarget = TARGET_GETUI;

        if (MARK.contains("huawei")) {
            mTarget = TARGET_HUAWEI;
            HMSAgent.init(context);
        } else if (MARK.contains("xiaomi")) {
            mTarget = TARGET_XIAOMI;
//            initXiaomiPushInApplication(context);
        } else if (MARK.contains("oppo")) {
        } else if (MARK.contains("vivo")) {
        } else if (MARK.contains("samsung")) {
        } else if (MARK.contains("smartisan")) {
        }
    }

    public void initInMainActivity(Activity activity) {
        switch (mTarget) {
            case TARGET_GETUI:
                PushManager.getInstance().initialize(activity.getApplicationContext(), GPushService.class);
                PushManager.getInstance().registerPushIntentService(activity, GIntentService.class);
                break;
            case TARGET_HUAWEI:
                initHuaweiPushInActivity(activity);

                break;
            case TARGET_XIAOMI:
                // after login, go to this
                initXiaomiPushInApplication(activity);

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
                }
            }
        });
    }

    public void initXiaomiPushInApplication(Context context) {
        String appId = getMetaData(context, "XMPUSH_APPID");
        String appKey = getMetaData(context, "XMPUSH_APPKEY");
        Log.e("ernest", "appid:" + appId + ",appKey:" + appKey);
        if (!TextUtils.isEmpty(appId) && !TextUtils.isEmpty(appKey)) {
            MiPushClient.registerPush(context, appId.replace(" ", ""), appKey.replace(" ", ""));
        }
    }

    public int getTarget() {
        return mTarget;
    }


    public static String getMetaData(Context context, String key) {
        ApplicationInfo ai = null;
        try {
            ai = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            String value = ai.metaData.getString(key);
            return value;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}
