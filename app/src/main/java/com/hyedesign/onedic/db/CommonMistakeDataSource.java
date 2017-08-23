package com.hyedesign.onedic.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.hyedesign.onedic.interfaces.IDataSource;
import com.hyedesign.onedic.models.CommonMistake;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Albaloo on 2/23/2017.
 */

public class CommonMistakeDataSource implements IDataSource <CommonMistake> {
    SQLiteOpenHelper dbHelper = null;
    SQLiteDatabase database = null;

    private static final String[] allColumns ={
            OneDicDBOpenHelper.COLUMN_ID,
            OneDicDBOpenHelper.COLUMN_TITLE,
            OneDicDBOpenHelper.COLUMN_COMMONMISTAKE,
            OneDicDBOpenHelper.COLUMN_DESCRIPTION,
            OneDicDBOpenHelper.COLUMN_ISFAVORITE,
            //OneDicDBOpenHelper.COLUMN_ISDELETED,
            //OneDicDBOpenHelper.COLUMN_ISSYNCED,
            //OneDicDBOpenHelper.COLUMN_CREATED,
            //OneDicDBOpenHelper.COLUMN_MODIFIED
    };

    public CommonMistakeDataSource(Context context){
        dbHelper = new OneDicDBOpenHelper(context);
    }

    @Override
    public int getCount() {
        open();
        int count = (int) DatabaseUtils.longForQuery(database, "SELECT COUNT(*) FROM " + OneDicDBOpenHelper.TABLE_COMMONMISTAKE, null);
        close();
        return count;
    }

    @Override
    public CommonMistake create(CommonMistake model) {
        open();
        ContentValues values = new ContentValues();
        values.put(OneDicDBOpenHelper.COLUMN_TITLE,model.getTitle());
        values.put(OneDicDBOpenHelper.COLUMN_COMMONMISTAKE,model.getCommonMistake());
        values.put(OneDicDBOpenHelper.COLUMN_DESCRIPTION,model.getDescription());
        values.put(OneDicDBOpenHelper.COLUMN_ISFAVORITE, 0);
        //values.put(OneDicDBOpenHelper.COLUMN_ISDELETED, 0);
        //values.put(OneDicDBOpenHelper.COLUMN_ISSYNCED, 0);
        //SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");
        //values.put(OneDicDBOpenHelper.COLUMN_CREATED, sdfr.format(new Date()));
        long id = database.insert(OneDicDBOpenHelper.TABLE_COMMONMISTAKE,null,values);
        model.setId(id);
        close();
        return model;
    }

    @Override
    public CommonMistake update(CommonMistake model) {
        open();
        ContentValues values = new ContentValues();
        values.put(OneDicDBOpenHelper.COLUMN_TITLE,model.getTitle());
        values.put(OneDicDBOpenHelper.COLUMN_COMMONMISTAKE,model.getCommonMistake());
        values.put(OneDicDBOpenHelper.COLUMN_DESCRIPTION,model.getDescription());
        //SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");
        //values.put(OneDicDBOpenHelper.COLUMN_MODIFIED,sdfr.format(new Date()));
        database.update(OneDicDBOpenHelper.TABLE_COMMONMISTAKE,
                values,
                OneDicDBOpenHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(model.getId())} );

        close();
        Log.i(LOGTAG,"common mistake created with id " + model.getId());
        return model;
    }

    @Override
    public void delete(long id) {
        open();
        database.delete(OneDicDBOpenHelper.TABLE_COMMONMISTAKE,
                OneDicDBOpenHelper.COLUMN_ID + "=?",
                new String[] { String.valueOf(id) });
        close();
    }

    @Override
    public void delete() {
        open();
        database.delete(OneDicDBOpenHelper.TABLE_COMMONMISTAKE,
                null,
                null);
        close();
    }

    @Override
    public CommonMistake getById(long id) {
        open();
        String selectQuery = "SELECT * FROM " + OneDicDBOpenHelper.TABLE_COMMONMISTAKE  + " WHERE "
                + OneDicDBOpenHelper.COLUMN_ID + " = " + id;

        Cursor cursor = database.rawQuery(selectQuery,null);

        if (cursor!=null)
            cursor.moveToFirst();

        CommonMistake model = new CommonMistake();
        model.setId(cursor.getLong(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_ID)));
        model.setTitle(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_TITLE)));
        model.setCommonMistake(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_COMMONMISTAKE)));
        model.setDescription(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_DESCRIPTION)));
        model.setFavorite(cursor.getInt(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_ISFAVORITE)));
        //model.setCreated(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_CREATED)));
        //model.setModified(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_MODIFIED)));
        //model.setDeleted(cursor.getInt(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_ISDELETED)));
        //model.setSynced(cursor.getInt(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_ISSYNCED)));

        close();
        return model;

    }

    @Override
    public List<CommonMistake> getAll() {
        open();
        Cursor cursor = database.query(OneDicDBOpenHelper.TABLE_COMMONMISTAKE,allColumns,
                null,null,null,null, OneDicDBOpenHelper.COLUMN_ID + " DESC");

        Log.i(LOGTAG,"Returned " + cursor.getCount() + " rows.");

        List<CommonMistake> antonyms = cursorToList(cursor);
        close();
        return antonyms;
    }

    @NonNull
    protected List<CommonMistake> cursorToList(Cursor cursor) {
        List<CommonMistake> antonyms = new ArrayList<>();
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                CommonMistake model = new CommonMistake();
                model.setId(cursor.getLong(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_ID)));
                model.setTitle(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_TITLE)));
                model.setCommonMistake(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_COMMONMISTAKE)));
                model.setDescription(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_DESCRIPTION)));
                model.setFavorite(cursor.getInt(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_ISFAVORITE)));
                //model.setCreated(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_CREATED)));
                //model.setModified(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_MODIFIED)));
                //model.setDeleted(cursor.getInt(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_ISDELETED)));
                //model.setSynced(cursor.getInt(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_ISSYNCED)));
                antonyms.add(model);
            }
        }
        return antonyms;
    }

    @Override
    public List<CommonMistake> getFiltered(String selection, String orderby) {
        open();
        Cursor cursor = database.query(OneDicDBOpenHelper.TABLE_COMMONMISTAKE,allColumns,
                selection,null,null,null, orderby);

        Log.i(LOGTAG,"Returned " + cursor.getCount() + " rows.");

        List<CommonMistake> antonyms = cursorToList(cursor);
        close();
        return antonyms;
    }

    @Override
    public void synce(long id) {
        open();
        ContentValues values = new ContentValues();
        //values.put(OneDicDBOpenHelper.COLUMN_ISSYNCED,"1");
        database.update(OneDicDBOpenHelper.TABLE_COMMONMISTAKE,
                values,
                OneDicDBOpenHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
        close();
    }

    @Override
    public void softDelete(long id) {
        open();
        ContentValues values = new ContentValues();
        //values.put(OneDicDBOpenHelper.COLUMN_ISDELETED,"1");
        database.update(OneDicDBOpenHelper.TABLE_COMMONMISTAKE,
                values,
                OneDicDBOpenHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
        close();
    }

    @Override
    public void favorite(long id, String status) {
        open();
        ContentValues values = new ContentValues();
        values.put(OneDicDBOpenHelper.COLUMN_ISFAVORITE, status);
        database.update(OneDicDBOpenHelper.TABLE_COMMONMISTAKE,
                values,
                OneDicDBOpenHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});

        close();
    }

    @Override
    public void seed() {
        CommonMistake model = new CommonMistake();
        model.setTitle("My shirt is loose.");
        model.setCommonMistake("My shirt is lose.");
        model.setDescription("");
        model.setFavorite(1);
        create(model);

        model.setTitle("One of my friends lives in Canada.");
        model.setCommonMistake("One of my friend lives in Canada.");
        model.setDescription("");
        model.setFavorite(1);
        create(model);

        model.setTitle("She is afraid of the dog.");
        model.setCommonMistake("She is afraid from the dog");
        model.setDescription("");
        model.setFavorite(1);
        create(model);
    }

    private void open(){
        Log.i(LOGTAG,"Database opened");
        database = dbHelper.getReadableDatabase();
    }

    private void close(){
        Log.i(LOGTAG,"Database closed");
        dbHelper.close();
    }
}
