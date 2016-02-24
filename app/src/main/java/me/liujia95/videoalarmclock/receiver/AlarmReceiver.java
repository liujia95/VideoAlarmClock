package me.liujia95.videoalarmclock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import me.liujia95.videoalarmclock.activity.ClockSettingActivity;
import me.liujia95.videoalarmclock.activity.VideoViewActivity;
import me.liujia95.videoalarmclock.bean.AlarmClockBean;
import me.liujia95.videoalarmclock.utils.LogUtils;

/**
 * Created by Administrator on 2016/2/23 22:40.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmClockBean bean = intent.getParcelableExtra(ClockSettingActivity.KEY_ALARMCLOCK);
        LogUtils.d("AlarmReceiver onReceive + bean:"+bean);

        Intent i = new Intent(context, VideoViewActivity.class);
        i.putExtra(ClockSettingActivity.KEY_ALARMCLOCK, bean);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
