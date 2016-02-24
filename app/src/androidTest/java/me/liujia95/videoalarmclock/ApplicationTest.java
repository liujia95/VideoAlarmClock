package me.liujia95.videoalarmclock;

import android.app.Application;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import me.liujia95.videoalarmclock.utils.LogUtils;
import me.liujia95.videoalarmclock.utils.UIUtils;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    @SmallTest
    public void test1(){
        ContentResolver contentResolver = UIUtils.getContext().getContentResolver();
        String[] projection = new String[]{MediaStore.Video.Media.TITLE};
        Cursor cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection,
                null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();
        int fileNum = cursor.getCount();

        for (int counter = 0; counter < fileNum; counter++) {
            LogUtils.d("----------------------file is: " + cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE)));
            cursor.moveToNext();
        }
        cursor.close();
    }

}