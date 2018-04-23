package com.knowledge.mnlin.viewutilmixed.interfaces;

/**
 * 网络请求成功回调接口
 * <p>
 * 如果有该callback,则网络请求成功后将执行该callback方法
 */
public interface HttpCallback<CB> {
    /**
     * @param tag 回调时用于区分某个请求,tag为null则表示dataSet字段自身为null或异常
     */
    void run(CB tag);
}
