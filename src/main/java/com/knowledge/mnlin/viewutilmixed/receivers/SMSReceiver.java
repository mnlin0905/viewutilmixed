package com.knowledge.mnlin.viewutilmixed.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.SmsMessage;
import android.util.Pair;

import com.knowledge.mnlin.viewutilmixed.interfaces.HttpCallback;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created on 2018/4/11
 * function :
 *
 * @author ACChain
 */

public class SMSReceiver extends BroadcastReceiver {
    final String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private HttpCallback<Pair<String, String>> httpCallback;

    /**
     * 有新短信时系统发出的广播，有序，可拦截。
     */
    public SMSReceiver(@NonNull HttpCallback<Pair<String, String>> httpCallback) {
        this.httpCallback = httpCallback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Observable.just(intent)
                .map(intentPairFunction -> {
                    //发送短信的号码
                    String contact;

                    String action = intentPairFunction.getAction();
                    if (action != null && action.equals(ACTION_SMS_RECEIVED)) {
                        StringBuilder builder = new StringBuilder();
                        Bundle bundle = intentPairFunction.getExtras();

                        if (bundle != null) {
                            //从Intent中获取bundle对象，此对象包含了所有的信息，短信是以“pdus”字段存储的。得到的是一个object数组，每个object都包含一条短信，（可能会获取到多条信息）。
                            Object[] pdus = (Object[]) bundle.get("pdus");
                            if (pdus == null) {
                                throw new RuntimeException("短信无内容");
                            }

                            //新建SmsMessage数组对象存储短信，每个SmsMessage对应一条短信类。
                            SmsMessage[] messages = new SmsMessage[pdus.length];
                            for (int i = 0; i < messages.length; i++) {
                                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                            }

                            //获取得到的最末端短信的联系人地址，赋值给contact
                            contact = messages[pdus.length - 1].getDisplayOriginatingAddress();

                            //读取短信内容，getDisplayMessageBody()是获取正文消息。
                            for (SmsMessage message : messages) {
                                builder.append(message.getDisplayMessageBody());
                            }

                            String result = builder.toString();
                            String reg = "^.*.*[^\\d]([\\d]{6})[^\\d].*$";
                            if (result.matches(reg)) {
                                result = result.replaceAll(reg, "$1");
                                return Pair.create(contact, result);
                            }
                        }
                    }
                    throw new RuntimeException("非可解析的短信");
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringStringPair -> httpCallback.run(stringStringPair));
    }
}

