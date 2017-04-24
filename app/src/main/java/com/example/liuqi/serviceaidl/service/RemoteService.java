package com.example.liuqi.serviceaidl.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.example.liuqi.serviceaidl.IMyAidlInterface;

import java.util.Timer;

/**
 * 每隔1秒就发送信息给客户端。
 * 注册服务需要设置export=true，progress才可以被其它应用访问。
 * Created by liuqi on 2017/4/24.
 */

public class RemoteService extends Service {
    private Messenger mClientMessenger;

    //创建Handler，获取客户端信使，处理来自客户端的Message
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mClientMessenger = msg.replyTo;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        final Messenger serviceMessenger = new Messenger(mHandler);
        Timer timer = new Timer();
        timer.schedule(new TimerTask(), 1000, 1000);
        IMyAidlInterface.Stub stub = new IMyAidlInterface.Stub() {
            @Override
            public Messenger getMessenger() throws RemoteException {
                return serviceMessenger;
            }
        };
        return stub;
    }

    /**
     * 定时任务
     */
    class TimerTask extends java.util.TimerTask {
        private int count;

        @Override
        public void run() {
            Message obtain = Message.obtain();
            obtain.what = count;
            try {
                mClientMessenger.send(obtain);
                count++;
            } catch (RemoteException e) {
                e.printStackTrace();
                stopSelf();
            }
        }
    }
}
