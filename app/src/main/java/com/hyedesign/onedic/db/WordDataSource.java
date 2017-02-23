package com.hyedesign.onedic.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hyedesign.onedic.interfaces.IDataSource;
import com.hyedesign.onedic.models.Word;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Albaloo on 2/18/2017.
 */

public class WordDataSource implements IDataSource<Word> {

    SQLiteOpenHelper dbHelper = null;
    SQLiteDatabase database = null;

    private static final String[] allColumns ={
            OneDicDBOpenHelper.COLUMN_ID,
            OneDicDBOpenHelper.COLUMN_TITLE,
            OneDicDBOpenHelper.COLUMN_PRONUNCIATION,
            OneDicDBOpenHelper.COLUMN_TRANSLATION,
            OneDicDBOpenHelper.COLUMN_DESCRIPTION,
            OneDicDBOpenHelper.COLUMN_ISDELETED,
            OneDicDBOpenHelper.COLUMN_ISFAVORITE,
            OneDicDBOpenHelper.COLUMN_ISSYNCED,
            OneDicDBOpenHelper.COLUMN_CREATED,
            OneDicDBOpenHelper.COLUMN_MODIFIED
    };

    public WordDataSource(Context context){
        dbHelper = new OneDicDBOpenHelper(context);
    }

    private void open(){
        Log.i(LOGTAG,"Database opened");
        database = dbHelper.getReadableDatabase();
    }

    private void close(){
        Log.i(LOGTAG,"Database closed");
        dbHelper.close();
    }

    @Override
    public Word create(Word model){
        open();
        ContentValues values = new ContentValues();
        values.put(OneDicDBOpenHelper.COLUMN_TITLE,model.getTitle());
        values.put(OneDicDBOpenHelper.COLUMN_PRONUNCIATION,model.getPronunciation());
        values.put(OneDicDBOpenHelper.COLUMN_TRANSLATION,model.getTranslation());
        values.put(OneDicDBOpenHelper.COLUMN_DESCRIPTION,model.getDescription());
        values.put(OneDicDBOpenHelper.COLUMN_ISDELETED, 0);
        values.put(OneDicDBOpenHelper.COLUMN_ISFAVORITE, model.getFavorite()?"1":"0");
        values.put(OneDicDBOpenHelper.COLUMN_ISSYNCED, 0);
        SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");
        values.put(OneDicDBOpenHelper.COLUMN_CREATED, sdfr.format(new Date()));
        long id = database.insert(OneDicDBOpenHelper.TABLE_WORD,null,values);
        model.setId(id);
        close();
        return model;
    }

    @Override
    public Word update(Word word){
        open();
        ContentValues values = new ContentValues();
        values.put(OneDicDBOpenHelper.COLUMN_TITLE,word.getTitle());
        values.put(OneDicDBOpenHelper.COLUMN_PRONUNCIATION,word.getPronunciation());
        values.put(OneDicDBOpenHelper.COLUMN_TRANSLATION,word.getTranslation());
        values.put(OneDicDBOpenHelper.COLUMN_DESCRIPTION,word.getDescription());
        SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");
        values.put(OneDicDBOpenHelper.COLUMN_MODIFIED,sdfr.format(new Date()));
        database.update(OneDicDBOpenHelper.TABLE_WORD,
                values,
                OneDicDBOpenHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(word.getId())} );

        close();
        return word;
    }

    @Override
    public void delete(long id){
        open();
        database.delete(OneDicDBOpenHelper.TABLE_WORD,
                OneDicDBOpenHelper.COLUMN_ID + "=?",
                new String[] { String.valueOf(id) });
        close();
    }

    @Override
    public List<Word> getAll(){
        open();
        List<Word> words = new ArrayList<>();

        Cursor cursor = database.query(OneDicDBOpenHelper.TABLE_WORD,allColumns,
                null,null,null,null, OneDicDBOpenHelper.COLUMN_ID + " DESC");

        Log.i(LOGTAG,"Returned " + cursor.getCount() + " rows.");

        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                Word model = new Word();
                model.setId(cursor.getLong(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_ID)));
                model.setTitle(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_TITLE)));
                model.setPronunciation(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_PRONUNCIATION)));
                model.setTranslation(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_TRANSLATION)));
                model.setDescription(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_DESCRIPTION)));
                model.setCreated(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_CREATED)));
                model.setModified(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_MODIFIED)));
                model.setDeleted(cursor.getInt(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_ISDELETED)));
                model.setFavorite(cursor.getInt(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_ISFAVORITE)));
                model.setSynced(cursor.getInt(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_ISSYNCED)));
                words.add(model);
            }
        }
        close();
        return words;
    }

    @Override
    public void synce(long id) {
        open();
        ContentValues values = new ContentValues();
        values.put(OneDicDBOpenHelper.COLUMN_ISSYNCED,"1");
        database.update(OneDicDBOpenHelper.TABLE_WORD,
                values,
                OneDicDBOpenHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
        close();
    }

    @Override
    public void softDelete(long id) {
        open();
        ContentValues values = new ContentValues();
        values.put(OneDicDBOpenHelper.COLUMN_ISDELETED,"1");
        database.update(OneDicDBOpenHelper.TABLE_WORD,values,OneDicDBOpenHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
        close();
    }

    @Override
    public void favorite(long id, String status) {
        open();
        //fav = false;
        ContentValues values = new ContentValues();
        values.put(OneDicDBOpenHelper.COLUMN_ISFAVORITE, status);
        //database.update(OneDicDBOpenHelper.TABLE_WORD,values, OneDicDBOpenHelper.COLUMN_ID + "=" + id ,null);
        /*
        database.update(OneDicDBOpenHelper.TABLE_WORD,
                values,
                OneDicDBOpenHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
        */
        long myid=database.update(OneDicDBOpenHelper.TABLE_WORD, values, OneDicDBOpenHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});

        close();
    }

    @Override
    public Word getById(long id){
        open();
        String selectQuery = "SELECT  * FROM " + OneDicDBOpenHelper.TABLE_WORD  + " WHERE "
                + OneDicDBOpenHelper.COLUMN_ID + " = " + id;

        Cursor cursor = database.rawQuery(selectQuery,null);

        if (cursor!=null)
            cursor.moveToFirst();

        Word word = new Word();
        word.setId(cursor.getLong(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_ID)));
        word.setTitle(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_TITLE)));
        word.setPronunciation(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_PRONUNCIATION)));
        word.setTranslation(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_TRANSLATION)));
        word.setDescription(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_DESCRIPTION)));
        word.setCreated(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_CREATED)));
        word.setModified(cursor.getString(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_MODIFIED)));
        word.setDeleted(cursor.getInt(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_ISDELETED)));
        word.setFavorite(cursor.getInt(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_ISFAVORITE)));
        word.setSynced(cursor.getInt(cursor.getColumnIndex(OneDicDBOpenHelper.COLUMN_ISSYNCED)));

        close();
        return word;
    }

}
