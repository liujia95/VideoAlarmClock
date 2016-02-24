package me.liujia95.videoalarmclock.activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.liujia95.videoalarmclock.R;
import me.liujia95.videoalarmclock.bean.AlarmClockBean;
import me.liujia95.videoalarmclock.bean.VideoInfoBean;
import me.liujia95.videoalarmclock.dao.AlarmClockDao;
import me.liujia95.videoalarmclock.receiver.AlarmReceiver;
import me.liujia95.videoalarmclock.utils.LogUtils;

/**
 * Created by Administrator on 2016/2/22 22:49.
 */
public class ClockSettingActivity extends AppCompatActivity implements TimePicker.OnTimeChangedListener, View.OnClickListener {

    @InjectView(R.id.clocksetting_timepicker)
    TimePicker mTimepicker; //时间选择器
    @InjectView(R.id.clocksetting_toolbar)
    Toolbar    mToolbar;
    @InjectView(R.id.clocksetting_tv_date)
    TextView   mTvDate; //
    @InjectView(R.id.clocksetting_cd_video)
    CardView   mCdVideo;
    @InjectView(R.id.clocksetting_tv_filename)
    TextView   mTvFileName;
    @InjectView(R.id.clocksetting_et_title)
    EditText   mEtTitle;

    private int year;
    private int month;
    private int day;

    public  boolean        isAdd;//true 添加闹钟/ false编辑闹钟
    private AlarmClockBean mAlarmClockBean;
    private int            mId;

    public static final int    REQUEST_SWITCH_VIDEOS = 1;
    public static final String KEY_ALARMCLOCK        = "key_alarmclock";

    private long          mTimeInMillis;
    private VideoInfoBean mVideoInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clocksetting);

        Intent intent = getIntent();
        isAdd = intent.getBooleanExtra(HomeActivity.KEY_IS_ADD, true);
        if (!isAdd) {
            mAlarmClockBean = intent.getParcelableExtra(HomeActivity.KEY_ALARMCLOCK);
        }

        initView();
        initToolbar();
        initData();
        initListener();
    }


    private void initView() {
        ButterKnife.inject(this);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("设置闹钟");
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    private void initData() {
        //是否使用24小时制
        mTimepicker.setIs24HourView(true);

        //添加闹钟
        if (isAdd) {
            //初始化Calendar日历对象
            Calendar mycalendar = Calendar.getInstance(Locale.CHINA);
            Date mydate = new Date(); //获取当前日期Date对象
            mycalendar.setTime(mydate);////为Calendar对象设置时间为当前日期

            year = mycalendar.get(Calendar.YEAR); //获取Calendar对象中的年
            month = mycalendar.get(Calendar.MONTH);//获取Calendar对象中的月
            day = mycalendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天

            mTvDate.setText("日期：" + year + "-" + (month + 1) + "-" + day);
        } else {
            //编辑闹钟
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(mAlarmClockBean.alarmClockMillis));

            //给时间赋值
            mTimepicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            mTimepicker.setCurrentMinute(calendar.get(Calendar.MINUTE));

            //给日期赋值
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            mTvDate.setText("日期：" + year + "-" + (month + 1) + "-" + day);

            //给id赋值
            mId = mAlarmClockBean.id;

            //给闹钟标题赋值
            mEtTitle.setText(mAlarmClockBean.title);

            //给视频标题赋值
            mTvFileName.setText(mAlarmClockBean.videoTitle);

            //给视频信息赋值
            mVideoInfo = new VideoInfoBean();
            mVideoInfo.displayName = mAlarmClockBean.videoTitle;
            mVideoInfo.path = mAlarmClockBean.videoPath;
        }
    }

    private void initListener() {
        mTimepicker.setOnTimeChangedListener(this);
        mTvDate.setOnClickListener(this);
        mCdVideo.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_clocksetting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                //检查闹钟数据设置是否完成
                if (checkClockSettingData()) {
                    //保存闹钟数据
                    saveClcokSettingData();
                    //开启闹钟广播
                    startAlarmBroadcast();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 检查闹钟设置数据
     * TODO:不允许设置两个时间相同的闹钟
     */
    private boolean checkClockSettingData() {
        Integer currentHour = mTimepicker.getCurrentHour();
        Integer currentMinute = mTimepicker.getCurrentMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, currentHour, currentMinute,0);
        mTimeInMillis = calendar.getTimeInMillis();
        LogUtils.d("timeInMillis :" + mTimeInMillis + " date: " + calendar.get(Calendar.MONTH) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日");

        //不允许在当前时间之前
        if (mTimeInMillis < System.currentTimeMillis()) {
            Toast.makeText(this, "该时间已过去，请重新设置时间", Toast.LENGTH_SHORT).show();
            return false;
        }
        //不允许标题为空
        else if (TextUtils.isEmpty(mEtTitle.getText())) {
            Toast.makeText(this, "请为闹钟设置一个标题", Toast.LENGTH_SHORT).show();
            return false;
        }
        //不允许视频资源为空
        else if (TextUtils.isEmpty(mTvFileName.getText())) {
            Toast.makeText(this, "请为闹钟设置视频", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 保存闹钟设置
     */
    private void saveClcokSettingData() {
        //将数据存储到数据库中
        if (isAdd) {
            AlarmClockDao.insert(new AlarmClockBean(mTimeInMillis, mEtTitle.getText().toString(), mVideoInfo.displayName, mVideoInfo.path));
        } else {
            AlarmClockDao.update(new AlarmClockBean(mId, mTimeInMillis, mEtTitle.getText().toString(), mVideoInfo.displayName, mVideoInfo.path));
        }
        finish();
    }

    /**
     * 开启闹钟广播
     */
    private void startAlarmBroadcast() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra(KEY_ALARMCLOCK, mAlarmClockBean);
        //延时意图
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, mTimeInMillis, sender);
    }

    /**
     * 时间改变的监听器
     *
     * @param view
     * @param hourOfDay 小时
     * @param minute    分钟
     */
    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        LogUtils.d("h:" + hourOfDay + " m:" + minute);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clocksetting_tv_date:
                clickSwitchDate(); //选择时间
                break;
            case R.id.clocksetting_cd_video:
                clickSwitcVideo(); //选择视频资源
                break;
        }
    }

    /**
     * 点击日期选择器
     */
    private void clickSwitchDate() {
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int myyear, int monthOfYear, int dayOfMonth) {
                year = myyear;
                month = monthOfYear;
                day = dayOfMonth;
                //更新日期
                updateDate();
            }

            private void updateDate() {
                //在TextView上显示日期
                mTvDate.setText("日期：" + year + "-" + (month + 1) + "-" + day);
            }
        }, year, month, day);
        dpd.show();
    }

    /**
     * 点击选择视频
     */
    private void clickSwitcVideo() {
        Intent intent = new Intent(this, SwitchSDCardVideoActivity.class);
        startActivityForResult(intent, REQUEST_SWITCH_VIDEOS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SWITCH_VIDEOS && resultCode == RESULT_OK) {
            mVideoInfo = data.getParcelableExtra(SwitchSDCardVideoActivity.KEY_VIDEO_INFO);
            mTvFileName.setText(mVideoInfo.displayName);
        }
    }
}
