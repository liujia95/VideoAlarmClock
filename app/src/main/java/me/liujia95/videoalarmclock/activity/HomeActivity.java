package me.liujia95.videoalarmclock.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.liujia95.videoalarmclock.R;
import me.liujia95.videoalarmclock.adapter.AlarmClockAdapter;
import me.liujia95.videoalarmclock.bean.AlarmClockBean;
import me.liujia95.videoalarmclock.dao.AlarmClockDao;
import me.liujia95.videoalarmclock.utils.UIUtils;

public class HomeActivity extends AppCompatActivity {

    @InjectView(R.id.home_toolbar)
    Toolbar      mToolbar;
    @InjectView(R.id.home_recyclerview)
    RecyclerView mRecyclerView;

    public static final String KEY_ALARMCLOCK = "key_alarmclock";
    public static final String KEY_IS_ADD     = "is_add";

    private AlarmClockAdapter    mAdapter;
    private List<AlarmClockBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    //TODO:时间过了的闹钟 需改变CardView背景颜色
    private void initData() {
        mDatas = AlarmClockDao.queryAll();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(UIUtils.getContext()));
        mAdapter = new AlarmClockAdapter(mDatas);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new AlarmClockAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, AlarmClockBean data) {
                Intent intent = new Intent(HomeActivity.this,ClockSettingActivity.class);
                intent.putExtra(KEY_IS_ADD, false);
                intent.putExtra(KEY_ALARMCLOCK, data);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_date:
                Intent intent = new Intent(this, ClockSettingActivity.class);
                intent.putExtra(KEY_IS_ADD, true);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 更新数据
     */
    public void updateDatas() {
        //从数据库中重新查一次数据
        mDatas = AlarmClockDao.queryAll();
        mAdapter.setData(mDatas);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        updateDatas();
        super.onResume();
    }

}
