package com.ernest.push.target.getui;


import android.content.Context;
import android.content.Intent;

import com.ernest.push.PushIntegratedManager;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.message.FeedbackCmdMessage;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.igexin.sdk.message.SetTagCmdMessage;

import static com.ernest.push.IPushCallback.ACTION_TOKEN;
import static com.ernest.push.IPushCallback.ACTION_UPDATEUI;

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */
public class GIntentService extends GTIntentService {


    public GIntentService() {

    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {

//        String appid = msg.getAppid();
//        String taskid = msg.getTaskId();
//        String messageid = msg.getMessageId();
//        String pkg = msg.getPkgName();
//        String cid = msg.getClientId();

        byte[] payload = msg.getPayload();

        if (payload != null) {
            String content = new String(payload);

            Intent intent = new Intent();
            intent.setAction(ACTION_UPDATEUI);
//            intent.putExtra("log", "Receive a push pass message with the message:" + content);
            intent.putExtra("log", content);
            PushIntegratedManager.callBack(intent);
        }

    }

    @Override
    public void onReceiveClientId(Context context, String cid) {
        Intent intent = new Intent();
        intent.setAction(ACTION_TOKEN);
        intent.putExtra(ACTION_TOKEN, cid);
        PushIntegratedManager.callBack(intent);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
//        Log.d(TAG, "onReceiveOnlineState -> " + (online ? "online" : "offline"));
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
//        Log.d(TAG, "onReceiveCommandResult -> " + cmdMessage);

        int action = cmdMessage.getAction();

        if (action == PushConsts.SET_TAG_RESULT) {
            setTagResult((SetTagCmdMessage) cmdMessage);
        } else if ((action == PushConsts.THIRDPART_FEEDBACK)) {
            feedbackResult((FeedbackCmdMessage) cmdMessage);
        }
    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {

    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {

    }

    private void setTagResult(SetTagCmdMessage setTagCmdMsg) {
        String sn = setTagCmdMsg.getSn();
        String code = setTagCmdMsg.getCode();
//
//        String text = "设置标签失败, 未知异常";
//        switch (Integer.valueOf(code)) {
//            case PushConsts.SETTAG_SUCCESS:
//                text = "设置标签成功";
//                break;
//
//            case PushConsts.SETTAG_ERROR_COUNT:
//                text = "设置标签失败, tag数量过大, 最大不能超过200个";
//                break;
//
//            case PushConsts.SETTAG_ERROR_FREQUENCY:
//                text = "设置标签失败, 频率过快, 两次间隔应大于1s且一天只能成功调用一次";
//                break;
//
//            case PushConsts.SETTAG_ERROR_REPEAT:
//                text = "设置标签失败, 标签重复";
//                break;
//
//            case PushConsts.SETTAG_ERROR_UNBIND:
//                text = "设置标签失败, 服务未初始化成功";
//                break;
//
//            case PushConsts.SETTAG_ERROR_EXCEPTION:
//                text = "设置标签失败, 未知异常";
//                break;
//
//            case PushConsts.SETTAG_ERROR_NULL:
//                text = "设置标签失败, tag 为空";
//                break;
//
//            case PushConsts.SETTAG_NOTONLINE:
//                text = "还未登陆成功";
//                break;
//
//            case PushConsts.SETTAG_IN_BLACKLIST:
//                text = "该应用已经在黑名单中,请联系售后支持!";
//                break;
//
//            case PushConsts.SETTAG_NUM_EXCEED:
//                text = "已存 tag 超过限制";
//                break;
//
//            default:
//                break;
//        }

//        Log.d(TAG, "settag result sn = " + sn + ", code = " + code + ", text = " + text);
    }

    private void feedbackResult(FeedbackCmdMessage feedbackCmdMsg) {
        String appid = feedbackCmdMsg.getAppid();
        String taskid = feedbackCmdMsg.getTaskId();
        String actionid = feedbackCmdMsg.getActionId();
        String result = feedbackCmdMsg.getResult();
        long timestamp = feedbackCmdMsg.getTimeStamp();
        String cid = feedbackCmdMsg.getClientId();
//
//        Log.d(TAG, "onReceiveCommandResult -> " + "appid = " + appid + "\ntaskid = " + taskid + "\nactionid = " + actionid + "\nresult = " + result
//                + "\ncid = " + cid + "\ntimestamp = " + timestamp);
    }


}


