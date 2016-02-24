package me.liujia95.videoalarmclock.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.liujia95.videoalarmclock.R;
import me.liujia95.videoalarmclock.bean.AlarmClockBean;
import me.liujia95.videoalarmclock.utils.UIUtils;
import me.liujia95.videoalarmclock.viewholder.AlarmClockViewHolder;

/**
 * Created by Administrator on 2016/2/24 11:17.
 */
public class AlarmClockAdapter extends RecyclerView.Adapter<AlarmClockViewHolder> implements View.OnClickListener {

    private List<AlarmClockBean> mDatas;

    private OnItemClickListener mOnItemClickListener = null;

    public AlarmClockAdapter(List<AlarmClockBean> datas) {
        this.mDatas = datas;
    }

    public void setData(List<AlarmClockBean> datas){
        this.mDatas = datas;
    }

    @Override
    public AlarmClockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.item_alarmclock, parent, false);
        view.setOnClickListener(this);
        return new AlarmClockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlarmClockViewHolder holder, int position) {
        if (getItemCount() != 0) {
            holder.loadData(mDatas.get(position));
            holder.itemView.setTag(mDatas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size();
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
            mOnItemClickListener.onItemClick(v, (AlarmClockBean) v.getTag());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, AlarmClockBean data);
    }
}
