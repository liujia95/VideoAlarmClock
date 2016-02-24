package me.liujia95.videoalarmclock.bean;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/2/24 10:35.
 */
public class AlarmClockBean implements Parcelable {
    public int    id; //ID
    public long   alarmClockMillis; //闹钟的毫秒值
    public String title; //闹钟标题
    public String videoTitle;//视频标题
    public String videoPath;//视频本地位置

    public AlarmClockBean(long alarmClockMillis, String title, String videoTitle, String videoPath) {
        this.alarmClockMillis = alarmClockMillis;
        this.title = title;
        this.videoTitle = videoTitle;
        this.videoPath = videoPath;
    }

    public AlarmClockBean(int id, long alarmClockMillis, String title, String videoTitle, String videoPath) {
        this.id = id;
        this.alarmClockMillis = alarmClockMillis;
        this.title = title;
        this.videoTitle = videoTitle;
        this.videoPath = videoPath;
    }

    protected AlarmClockBean(Parcel in) {
        id = in.readInt();
        alarmClockMillis = in.readLong();
        title = in.readString();
        videoTitle = in.readString();
        videoPath = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(alarmClockMillis);
        dest.writeString(title);
        dest.writeString(videoTitle);
        dest.writeString(videoPath);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AlarmClockBean> CREATOR = new Creator<AlarmClockBean>() {
        @Override
        public AlarmClockBean createFromParcel(Parcel in) {
            return new AlarmClockBean(in);
        }

        @Override
        public AlarmClockBean[] newArray(int size) {
            return new AlarmClockBean[size];
        }
    };
}
