package me.liujia95.videoalarmclock.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/2/24 0:36.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "alarmclock.db"; //数据库名称
    private static final int    VERSION = 1; //数据库版本

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //id ,时间毫秒值 , 闹钟标题,视频资源位置
        String sql = "create table alarmclock(id INTEGER PRIMARY KEY AUTOINCREMENT,alarmclock_millis varchar(255) not null ,title varchar(60) not null,video_title varchar(60) not null, video_path varchar(255) not null);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
