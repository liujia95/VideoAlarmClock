package me.liujia95.videoalarmclock.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import me.liujia95.videoalarmclock.bean.AlarmClockBean;
import me.liujia95.videoalarmclock.db.DatabaseHelper;
import me.liujia95.videoalarmclock.utils.UIUtils;

/**
 * Created by Administrator on 2016/2/24 10:30.
 */
public class AlarmClockDao {
    private static SQLiteOpenHelper mHelper = new DatabaseHelper(UIUtils.getContext());

    public static void insert(AlarmClockBean bean) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("INSERT INTO alarmclock(alarmclock_millis,title,video_title,video_path) VALUES(?,?,?,?)", new Object[]{bean.alarmClockMillis, bean.title, bean.videoTitle, bean.videoPath});
        db.close();
    }

    public static void delete(int id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("DELETE FROM alarmclock WHERE id=?", new Object[]{id});
        db.close();
    }

    public static void update(AlarmClockBean bean) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        db.execSQL("UPDATE alarmclock SET alarmclock_millis=?,title=?,video_title=?,video_path=? WHERE id=?", new Object[]{bean.alarmClockMillis, bean.title, bean.videoTitle, bean.videoPath, bean.id});
        db.close();
    }

    public static AlarmClockBean query(int id) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id,alarmclock_millis,title,video_title,video_path FROM alarmclock WHERE id=?", new String[]{id + ""});
        AlarmClockBean bean = null;
        if (cursor.moveToNext()) {
            bean = new AlarmClockBean(cursor.getInt(0), cursor.getLong(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        }
        cursor.close();
        db.close();
        return bean;
    }

    public static List<AlarmClockBean> queryAll() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id,alarmclock_millis,title,video_title,video_path FROM alarmclock", null);
        List<AlarmClockBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            AlarmClockBean bean = new AlarmClockBean(cursor.getInt(0), cursor.getLong(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            list.add(bean);
        }
        cursor.close();
        db.close();
        return list;
    }
}
