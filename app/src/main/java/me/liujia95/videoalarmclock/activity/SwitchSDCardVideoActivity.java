package me.liujia95.videoalarmclock.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.liujia95.videoalarmclock.R;
import me.liujia95.videoalarmclock.adapter.VideoThumbnailsAdapter;
import me.liujia95.videoalarmclock.bean.VideoInfoBean;
import me.liujia95.videoalarmclock.utils.LogUtils;
import me.liujia95.videoalarmclock.utils.UIUtils;
import me.liujia95.videoalarmclock.utils.VideoUtils;

/**
 * Created by Administrator on 2016/2/23 13:26.
 */
public class SwitchSDCardVideoActivity extends AppCompatActivity implements VideoThumbnailsAdapter.OnItemClickListener {

    public List<VideoInfoBean> mVideoList          = null;// 视频信息集合
    public List<Bitmap>        mVideoThumbnailList = null;//视频缩略图集合

    public static final String KEY_VIDEO_INFO = "key_video_info";

    @InjectView(R.id.switchvideo_recyclerview)
    RecyclerView mRecyclerview;
    private VideoThumbnailsAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switchvideo);

        initView();
        initData();
        initListener();
    }

    private void initView() {
        ButterKnife.inject(this);
    }

    private void initData() {
        mVideoList = new ArrayList<>();
        mVideoThumbnailList = new ArrayList<>();

        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new VideoThumbnailsAdapter(mVideoList, mVideoThumbnailList);
        mRecyclerview.setAdapter(mAdapter);

        //弹出Progress Dialog
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("提示");
        dialog.setMessage("正在搜索资源...");
        dialog.setCancelable(false);// 是否可以按退回键取消

        if (!this.isFinishing() && !dialog.isShowing()) {
            dialog.show();
        }

        //子线程处理耗时操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取SD卡的所有视频信息
                VideoUtils.getVideoFile(mVideoList, Environment.getExternalStorageDirectory());

                LogUtils.d("mVideoList count: ---" + mVideoList.size());

                //获取所有的视频缩略图
                for (VideoInfoBean bean : mVideoList) {
                    LogUtils.d("bean displayName: " + bean.displayName);
                    LogUtils.d("bean path: " + bean.path);

                    Bitmap bitmap = VideoUtils.getVideoThumbnail(bean.path, UIUtils.dip2px(120), UIUtils.dip2px(80), MediaStore.Images.Thumbnails.MINI_KIND);
                    //Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(bean.path,MediaStore.Images.Thumbnails.MINI_KIND );
                    mVideoThumbnailList.add(bitmap);

                    //数据更新
                    mAdapter.setData(mVideoList, mVideoThumbnailList);
                    UIUtils.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }

                //关闭Progress Dialog
                dialog.dismiss();
            }
        }).start();
    }

    private void initListener() {
        //给item设置点击事件
        mAdapter.setOnItemClickListener(this);
    }

    /**
     * RecyclerView中item的点击事件
     *
     * @param view
     * @param bean
     */
    @Override
    public void onItemClick(View view, VideoInfoBean bean) {
        Intent intent = new Intent();
        intent.putExtra(KEY_VIDEO_INFO, bean);
        setResult(RESULT_OK, intent);
        finish();
    }
}
