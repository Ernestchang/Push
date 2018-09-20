package com.ernest.push;

import android.content.Intent;

/**
 * Created by Ernest on 2018/9/19.
 */

public interface IPushCallback {
    String ACTION_UPDATEUI = "action.updateUI";
    String ACTION_TOKEN = "action.updateToken";

    void onReceive(Intent intent);
}
