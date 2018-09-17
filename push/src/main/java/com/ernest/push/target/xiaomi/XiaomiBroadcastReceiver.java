package com.ernest.push.target.xiaomi;

/**
 * 自定义的小米推送接收
 * Created by luoming on 2018/5/28.
 */

public class XiaomiBroadcastReceiver {
    //        extends PushMessageReceiver {
//    @Override
//    public void onReceivePassThroughMessage(Context var1, MiPushMessage var2) {
//        PushReceiverHandleManager.getInstance().onMessageReceived(var1, convert2ReceiverInfo(var2));
//    }
//
//    @Override
//    public void onNotificationMessageClicked(Context var1, MiPushMessage var2) {
//        PushReceiverHandleManager.getInstance().onNotificationOpened(var1, convert2ReceiverInfo(var2));
//    }
//
//    @Override
//    public void onNotificationMessageArrived(Context var1, MiPushMessage var2) {
//        PushReceiverHandleManager.getInstance().onNotificationReceived(var1, convert2ReceiverInfo(var2));
//    }
//
//    @Override
//    public void onReceiveRegisterResult(Context var1, MiPushCommandMessage var2) {
//        ReceiverInfo info = convert2ReceiverInfo(var2);
//        info.setContent("小米推送注册成功");
//        PushReceiverHandleManager.getInstance().onRegistration(var1, info);
//    }
//
//    /**
//     * 将intent的数据转化为ReceiverInfo用于处理
//     *
//     * @param miPushMessage
//     * @return
//     */
//    private ReceiverInfo convert2ReceiverInfo(MiPushMessage miPushMessage) {
//        ReceiverInfo info = new ReceiverInfo();
//        info.setContent(miPushMessage.getContent());
//        info.setPushTarget(PushTarget.XIAOMI);
//        info.setTitle(miPushMessage.getTitle());
//        info.setRawData(miPushMessage);
//        if (miPushMessage.getExtra() != null) {
//            info.setExtra(new Gson().toJson(miPushMessage.getExtra()));
//        }
//        return info;
//    }
//
//    /**
//     * 将intent的数据转化为ReceiverInfo用于处理
//     *
//     * @param miPushCommandMessage
//     * @return
//     */
//    private ReceiverInfo convert2ReceiverInfo(MiPushCommandMessage miPushCommandMessage) {
//        ReceiverInfo info = new ReceiverInfo();
//        info.setContent(miPushCommandMessage.getCommand());
//        info.setPushTarget(PushTarget.XIAOMI);
//        return info;
//    }
}
