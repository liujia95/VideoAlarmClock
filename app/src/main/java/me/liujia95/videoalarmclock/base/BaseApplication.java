package me.liujia95.videoalarmclock.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by Administrator on 2016/2/22 17:54.
 */
public class BaseApplication extends Application {

    private static Context mContext;
    private static Thread  mMainThread;
    private static Handler mMainHandler;

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static Handler getMainHandler() {
        return mMainHandler;
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mMainThread = Thread.currentThread();
        mMainHandler = new Handler();
    }
}
