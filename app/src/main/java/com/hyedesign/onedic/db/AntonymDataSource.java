package com.hyedesign.onedic.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.util.Log;

import com.hyedesign.onedic.interfaces.IDataSource;
import com.hyedesign.onedic.models.Antonym;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Albaloo on 2/23/2017.
 */

public class AntonymDataSource implements IDataSource<Antonym> {
    SQLiteOpenHelper dbHelper = null;
    SQLiteDatabase database = null;

    private static final String[] allColumns ={
            OneDicDBOpenHelper.COLUMN_ID,
            OneDicDBOpenHelper.COLUMN_TITLE,
            OneDicDBOpenHelper.COLUMN_TRANSLATION,
            OneDicDBOpenHelper.COLUMN_ANTONYM,
            OneDicDBOpenHelper.COLUMN_ANTONYM_TRANSLATION,
            OneDicDBOpenHelper.COLUMN_DESCRIPTION,
            OneDicDBOpenHelper.COLUMN_ISFAVORITE,
            //OneDicDBOpenHelper.COLUMN_ISDELETED,
            //OneDicDBOpenHelper.COLUMN_ISSYNCED,
            //OneDicDBOpenHelper.COLUMN_CREATED,
            //OneDicDBOpenHelper.COLUMN_MODIFIED
    };

    public AntonymDataSource(Context context){
        dbHelper = new OneDicDBOpenHelper(context);
    }

    @Override
    public int getCount() {
        open();
        int count = (int) DatabaseUtils.longForQuery(database, "SELECT COUNT(*) FROM " + OneDicDBOpenHelper.TABLE_ANTONYM, null);
        close();
        return count;
    }

    @Override
    public Antonym create(Antonym model) {
        open();
        ContentValues values = new ContentValues();
        values.put(OneDicDBOpenHelper.COLUMN_TITLE,model.getTitle());
        values.put(OneDicDBOpenHelper.COLUMN_TRANSLATION,model.getTranslation());
        values.put(OneDicDBOpenHelper.COLUMN_ANTONYM,model.getAntonym());
        values.put(OneDicDBOpenHelper.COLUMN_ANTONYM_TRANSLATION,model.getAntonymTranslation());
        values.put(OneDicDBOpenHelper.COLUMN_DESCRIPTION,model.getDescription());
        values.put(OneDicDBOpenHelper.COLUMN_ISFAVORITE, 0);
        //values.put(OneDicDBOpenHelper.COLUMN_ISDELETED, 0);

        //values.put(OneDicDBOpenHelper.COLUMN_ISSYNCED, 0);
        //SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");
        //values.put(OneDicDBOpenHelper.COLUMN_CREATED, sdfr.format(new Date()));
        long id = database.insert(OneDicDBOpenHelper.TABLE_ANTONYM,null,values);
        model.setId(id);
        close();
        Log.i(LOGTAG,"antonym created with id " + model.getId());
        return model;
    }

    @Override
    public Antonym update(Antonym model) {
        open();
        ContentValues values = new ContentValues();
        values.put(OneDicDBOpenHelper.COLUMN_TITLE,model.getTitle());
        values.put(OneDicDBOpenHelper.COLUMN_TRANSLATION,model.getTranslation());
        values.put(OneDicDBOpenHelper.COLUMN_ANTONYM,model.getAntonym());
        values.put(OneDicDBOpenHelper.COLUMN_ANTONYM_TRANSLATION,model.getAntonymTranslation());
        values.put(OneDicDBOpenHelper.COLUMN_DESCRIPTION,model.getDescription());
        //values.put(OneDicDBOpenHelper.COLUMN_MODIFIED,"1");
        //SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");
        //values.put(OneDicDBOpenHelper.COLUMN_MODIFIED,sdfr.format(new Date()));
        database.update(OneDicDBOpenHelper.TABLE_ANTONYM,
                values,
                OneDicDBOpenHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(model.getId())} );

        close();
        return model;
    }

    @Override
    public void delete(long id) {
        open();
        database.delete(OneDicDBOpenHelper.TABLE_ANTONYM,
                OneDicDBOpenHelper.COLUMN_ID + "=?",
                new String[] { String.valueOf(id) });
        close();
    }

    @Override
    public void delete() {
        open();
        database.delete(OneDicDBOpenHelper.TABLE_ANTONYM,
                null,
                null);
        close();
    }

    @Override
    public Antonym getById(long id) {
        open();
        String selectQuery = "SELECT * FROM " + OneDicDBOpenHelper.TABLE_ANTONYM  + " WHERE "
                + OneDicDBOpenHelper.COLUMN_ID + " = " + id;

        Cursor cursor = database.rawQuery(selectQuery,null);

        if (cursor!=null)
            cursor.moveToFirst();

        Antonym model = new Antonym();
        model.setId(cursor.getLong(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_ID)));
        model.setTitle(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_TITLE)));
        model.setTranslation(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_TRANSLATION)));
        model.setAntonym(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_ANTONYM)));
        model.setAntonymTranslation(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_ANTONYM_TRANSLATION)));
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
    public List<Antonym> getAll() {
        open();

        Cursor cursor = database.query(OneDicDBOpenHelper.TABLE_ANTONYM,allColumns,
                null,null,null,null, OneDicDBOpenHelper.COLUMN_ID + " DESC");

        Log.i(LOGTAG,"Returned " + cursor.getCount() + " rows.");
        List<Antonym> antonyms = cursorToList(cursor);
        close();
        return antonyms;
    }


    @NonNull
    protected List<Antonym> cursorToList(Cursor cursor) {
        List<Antonym> antonyms = new ArrayList<>();
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                Antonym model = new Antonym();
                model.setId(cursor.getLong(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_ID)));
                model.setTitle(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_TITLE)));
                model.setTranslation(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_TRANSLATION)));
                model.setAntonym(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_ANTONYM)));
                model.setAntonymTranslation(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_ANTONYM_TRANSLATION)));
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
    public List<Antonym> getFiltered(String selection, String orderby) {
        open();

        Cursor cursor = database.query(OneDicDBOpenHelper.TABLE_ANTONYM,allColumns,
                selection,null,null,null, orderby);

        Log.i(LOGTAG,"Returned " + cursor.getCount() + " rows.");
        List<Antonym> antonyms = cursorToList(cursor);
        close();
        return antonyms;
    }

    @Override
    public void synce(long id) {
        open();
        ContentValues values = new ContentValues();
        //values.put(OneDicDBOpenHelper.COLUMN_ISSYNCED,"1");
        database.update(OneDicDBOpenHelper.TABLE_ANTONYM,
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
        database.update(OneDicDBOpenHelper.TABLE_ANTONYM,
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
        database.update(OneDicDBOpenHelper.TABLE_ANTONYM,
                values,
                OneDicDBOpenHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});

        close();
    }

    @Override
    public void seed() {
        Antonym model = new Antonym();
        model.setTitle("happy");
        model.setTranslation("خوشحال");
        model.setAntonym("sad");
        model.setAntonymTranslation("غمگین");
        model.setDescription("");
        model.setFavorite(1);
        create(model);

        model.setTitle("angel");
        model.setTranslation("فرشته");
        model.setAntonym("evil");
        model.setAntonymTranslation("شیطان");
        model.setDescription("");
        model.setFavorite(1);
        create(model);

        model.setTitle("day");
        model.setTranslation("روز");
        model.setAntonym("night");
        model.setAntonymTranslation("شب");
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
