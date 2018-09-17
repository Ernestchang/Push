package com.ernest.push.target.huawei;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.ernest.push.handle.PushReceiverHandleManager;
import com.ernest.push.model.PushTarget;
import com.ernest.push.model.ReceiverInfo;
import com.huawei.hms.support.api.push.PushReceiver;

/**
 * 自定义的华为推送服务的接收器
 * Created by luoming on 2018/5/29.
 */

public class HuaweiPushBroadcastReceiver extends PushReceiver {
    private static final String TAG = "HuaweiPushBroadcastRece";

    /**
     * token获取完成；token用于标识设备
     *
     * @param context
     * @param token
     * @param extras
     */
    @Override
    public void onToken(Context context, String token, Bundle extras) {
        //获取token成功，token用于标识设备的唯一性
        ReceiverInfo alias = createReceiverInfo();
        alias.setContent(token);
        alias.setRawData(extras);
        PushReceiverHandleManager.getInstance().onAliasSet(context, alias);
    }

    /**
     * 接收到了穿透消息
     *
     * @param context
     * @param msg
     * @param bundle
     * @return
     */
    @Override
    public boolean onPushMsg(Context context, byte[] msg, Bundle bundle) {
        Log.i(TAG, "onPushMsg: " + bundle);
        try {
            //CP可以自己解析消息内容，然后做相应的处理
            String content = new String(msg, "UTF-8");
            ReceiverInfo info = createReceiverInfo();
            info.setContent(content);
            info.setRawData(bundle);
            PushReceiverHandleManager.getInstance().onMessageReceived(context, info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 点击通知栏事件处理
     *
     * @param context
     * @param event
     * @param extras
     */
    @Override
    public void onEvent(Context context, Event event, Bundle extras) {
        //点击通知事件
        if (Event.NOTIFICATION_OPENED.equals(event)) {
            ReceiverInfo info = createReceiverInfo();
            String message = extras.getString(BOUND_KEY.pushMsgKey);
            info.setContent(message);
            info.setRawData(extras);
            PushReceiverHandleManager.getInstance().onNotificationOpened(context, info);
        }
    }

    /**
     * SDK状态
     *
     * @param context
     * @param pushState
     */
    @Override
    public void onPushState(Context context, boolean pushState) {
        if (pushState) {
            ReceiverInfo info = createReceiverInfo();
            info.setContent("华为推送注册成功");
            PushReceiverHandleManager.getInstance().onRegistration(context, info);
        }
    }

    private ReceiverInfo createReceiverInfo() {
        ReceiverInfo info = new ReceiverInfo();
        info.setPushTarget(PushTarget.HUAWEI);
        return info;
    }

}
