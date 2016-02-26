package me.liujia95.videoalarmclock.base;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import me.liujia95.videoalarmclock.R;
import me.liujia95.videoalarmclock.utils.IOUtils;
import me.liujia95.videoalarmclock.utils.LogUtils;

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

        //把默认视频资源copy到手机根目录下
        new Thread() {
            @Override
            public void run() {
                copyVideoToLocal();
            }
        }.start();
    }

    private void copyVideoToLocal() {
        //如果文件不存在,将视频文件拷贝到该目录下
        File writeFile = new File(Environment.getExternalStorageDirectory(), "video.mp4");
        if (writeFile.exists()) {
            LogUtils.d("存在");
            return;
        } else {
            LogUtils.d("不存在");
        }
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = getResources().openRawResource(R.raw.test);

            fos = new FileOutputStream(writeFile);

            LogUtils.d("writeFile path:" + writeFile.getAbsolutePath());

            byte[] buf = new byte[1024];
            int i = 0;
            while ((i = is.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
            LogUtils.d("copy视频资源完毕");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(is);
            IOUtils.close(fos);
        }
    }
}
