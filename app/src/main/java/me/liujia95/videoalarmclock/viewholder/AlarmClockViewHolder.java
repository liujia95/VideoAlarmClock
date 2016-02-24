package me.liujia95.videoalarmclock.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.liujia95.videoalarmclock.R;
import me.liujia95.videoalarmclock.bean.AlarmClockBean;

/**
 * Created by Administrator on 2016/2/24 11:19.
 */
public class AlarmClockViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.alarmclock_tv_time)
    TextView mTvTime;
    @InjectView(R.id.alarmclock_tv_title)
    TextView mTvTitle;
    @InjectView(R.id.alarmclock_tv_videotitle)
    TextView mTvVideotitle;


    public AlarmClockViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }

    public void loadData(final AlarmClockBean bean) {
        //将毫秒值转换成日期格式
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(new Date(bean.alarmClockMillis));

        mTvTime.setText(date);
        mTvTitle.setText(bean.title);
        mTvVideotitle.setText(bean.videoTitle);
    }
}
