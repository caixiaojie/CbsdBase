package com.yjhs.cbsd.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.yjhs.cbsd.Constant
import com.yjhs.cbsd.event.NetworkChangeEvent
import com.yjhs.cbsd.utils.NetWorkUtil
import com.yjhs.cbsd.utils.Preference
import org.greenrobot.eventbus.EventBus

/**
 * Created by chenxz on 2018/8/1.
 */
class NetworkChangeReceiver : BroadcastReceiver() {

    /**
     * 缓存上一次的网络状态
     */
    private var hasNetwork: Boolean by Preference(Constant.HAS_NETWORK_KEY, true)

    override fun onReceive(context: Context, intent: Intent) {
        val isConnected = NetWorkUtil.isNetworkConnected(context)
        if (isConnected) {
            if (isConnected != hasNetwork) {
                EventBus.getDefault().post(NetworkChangeEvent(isConnected))
            }
        } else {
            EventBus.getDefault().post(NetworkChangeEvent(isConnected))
        }
    }

}