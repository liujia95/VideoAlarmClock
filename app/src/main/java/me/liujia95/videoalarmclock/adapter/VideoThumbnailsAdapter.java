package me.liujia95.videoalarmclock.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.liujia95.videoalarmclock.R;
import me.liujia95.videoalarmclock.bean.VideoInfoBean;
import me.liujia95.videoalarmclock.utils.UIUtils;
import me.liujia95.videoalarmclock.viewholder.VideoThumbnailViewHolder;

/**
 * Created by Administrator on 2016/2/23 13:50.
 */
public class VideoThumbnailsAdapter extends RecyclerView.Adapter<VideoThumbnailViewHolder> implements View.OnClickListener {

    public List<VideoInfoBean> mVideoList          = null;// 视频信息集合
    public List<Bitmap>        mVideoThumbnailList = null;//视频缩略图集合

    private OnItemClickListener mOnItemClickListener = null;

    public VideoThumbnailsAdapter(List<VideoInfoBean> videoList, List<Bitmap> videoThumbnailList) {
        this.mVideoList = videoList;
        this.mVideoThumbnailList = videoThumbnailList;
    }

    public void setData(List<VideoInfoBean> videoList, List<Bitmap> videoThumbnailList) {
        mVideoList = videoList;
        mVideoThumbnailList = videoThumbnailList;
    }

    @Override
    public VideoThumbnailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.item_video_thumbnail, parent, false);
        view.setOnClickListener(this);
        return new VideoThumbnailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoThumbnailViewHolder holder, int position) {
        if (getItemCount() != 0) {
            holder.loadData(mVideoList.get(position), mVideoThumbnailList.get(position));
            holder.itemView.setTag(mVideoList.get(position));
        }
    }


    @Override
    public int getItemCount() {
        if (mVideoThumbnailList != null) {
            return mVideoThumbnailList.size();
        } else {
            return 0;
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //获取数据
            mOnItemClickListener.onItemClick(v, (VideoInfoBean) v.getTag());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, VideoInfoBean data);
    }
}
