package com.knowledge.mnlin.viewutilmixed.util;

import android.app.Activity;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;

/**
 * Created on 2018/4/17
 * function : ARouter框架回调接口,默认实现所有方法
 *
 * 默认当跳转成功后,结束自身
 * 适用于目标不存在任何权限要求的情况,在添加该callback后系统默认的全局降级策略将失效
 *
 * @author ACChain
 */

public class ARouterFinishCallback implements NavigationCallback {
    /**
     * 当前所在的activity
     */
    private Activity activity;

    public ARouterFinishCallback(Activity activity) {
        this.activity = activity;
    }

    /**
     * Callback when find the destination.
     *
     * @param postcard meta
     */
    public void onFound(Postcard postcard) {

    }

    /**
     * Callback after lose your way.
     *
     * @param postcard meta
     */
    public void onLost(Postcard postcard) {

    }

    /**
     * Callback after navigation.
     *
     * @param postcard meta
     */
    public void onArrival(Postcard postcard) {
        if (activity != null && !activity.isDestroyed()) {
            activity.finish();
        }
    }

    /**
     * Callback on interrupt.
     *
     * @param postcard meta
     */
    public void onInterrupt(Postcard postcard) {
    }
}
