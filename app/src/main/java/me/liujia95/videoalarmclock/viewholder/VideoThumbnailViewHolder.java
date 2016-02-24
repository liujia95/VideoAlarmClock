package me.liujia95.videoalarmclock.viewholder;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.liujia95.videoalarmclock.R;
import me.liujia95.videoalarmclock.bean.VideoInfoBean;

/**
 * Created by Administrator on 2016/2/23 13:51.
 */
public class VideoThumbnailViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.thumbnail_iv)
    ImageView mIvIcon;
    @InjectView(R.id.thumbnail_tv_title)
    TextView  mTvTitle;
    @InjectView(R.id.thumbnail_tv_date)
    TextView  mTvDate;


    public VideoThumbnailViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);

    }

    public void loadData(VideoInfoBean videoInfoBean, Bitmap bitmap) {
        mIvIcon.setImageBitmap(bitmap);
        mTvTitle.setText(videoInfoBean.displayName);
    }
}
