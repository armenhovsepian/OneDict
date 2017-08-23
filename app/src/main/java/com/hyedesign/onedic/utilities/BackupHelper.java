package com.hyedesign.onedic.utilities;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hyedesign.onedic.db.OneDicDBOpenHelper;

/**
 * Created by Albaloo on 4/14/2017.
 */

public class BackupHelper {
    static SQLiteOpenHelper dbHelper = new OneDicDBOpenHelper(App.getAppContext());
    static SQLiteDatabase database = dbHelper.getReadableDatabase();

    public static int getCount(String table){
        int count = (int) DatabaseUtils.longForQuery(database, "SELECT COUNT(*) FROM " + table, null);
        dbHelper.close();
        return count;
    }
}
