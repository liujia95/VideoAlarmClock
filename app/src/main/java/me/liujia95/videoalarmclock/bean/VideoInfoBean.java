package me.liujia95.videoalarmclock.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/2/23 13:21.
 */
public class VideoInfoBean implements Parcelable {
    public String displayName;
    public String path;

    public VideoInfoBean(){

    }

    protected VideoInfoBean(Parcel in) {
        displayName = in.readString();
        path = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(displayName);
        dest.writeString(path);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VideoInfoBean> CREATOR = new Creator<VideoInfoBean>() {
        @Override
        public VideoInfoBean createFromParcel(Parcel in) {
            return new VideoInfoBean(in);
        }

        @Override
        public VideoInfoBean[] newArray(int size) {
            return new VideoInfoBean[size];
        }
    };
}
