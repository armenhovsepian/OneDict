package com.hyedesign.onedic.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hyedesign.onedic.models.Antonym;
import com.hyedesign.onedic.models.BaseModel;
import com.hyedesign.onedic.models.CommonMistake;
import com.hyedesign.onedic.models.Idiom;
import com.hyedesign.onedic.models.Slang;
import com.hyedesign.onedic.models.Synonym;
import com.hyedesign.onedic.models.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Albaloo on 2/18/2017.
 */

public class OneDicDBOpenHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOGTAG = "ONEDICT";

    // Database Name
    private static final String DATABASE_NAME = "onedict.db";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_WORD = "words";
    public static final String TABLE_ANTONYM = "antonyms";
    public static final String TABLE_SYNONYM = "synonyms";
    public static final String TABLE_IDIOM = "idioms";
    public static final String TABLE_SLANG = "slangs";
    public static final String TABLE_COMMONMISTAKE = "commonMistakes";
    //public static final String TABLE_VERB = "verbs";


    // Commons column names
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_TITLE = "Title";
    public static final String COLUMN_TRANSLATION = "Translation";
    public static final String COLUMN_DESCRIPTION = "Description";
    public static final String COLUMN_ISFAVORITE = "IsFavorite";
    //public static final String COLUMN_ISSYNCED = "IsSynced";
    //public static final String COLUMN_ISDELETED = "IsDeleted";
    //public static final String COLUMN_CREATED = "Created";
    //public static final String COLUMN_MODIFIED = "Modified";



    // WORDS Table - column names
    public static final String COLUMN_PRONUNCIATION = "Pronunciation";


    // WORDS Table - column names

    // IDIOMS Table - column names

    // ANTONYMS Table - column names
    public static final String COLUMN_ANTONYM = "Antonym";
    public static final String COLUMN_ANTONYM_TRANSLATION = "AntonymTranslation";

    // SYNONYMS Table - column names
    public static final String COLUMN_SYNONYM = "Synonym";
    public static final String COLUMN_SYNONYM_TRANSLATION = "SynonymTranslation";

    // VERB Table - column names

    // COMMONMISTAKES Table - column names
    public static final String COLUMN_COMMONMISTAKE = "CommonMistake";


    private static final String WORDS_TABLE_CREATE =
            "CREATE TABLE " + TABLE_WORD + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_PRONUNCIATION + " TEXT, " +
                    COLUMN_TRANSLATION + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_ISFAVORITE + " INT " +
                    //COLUMN_CREATED + " DATETIME default current_timestamp, " +
                    //COLUMN_CREATED + " TEXT,  " +
                    //COLUMN_MODIFIED + " TEXT, " +
                    //COLUMN_ISDELETED + " INT, " +
                    //COLUMN_ISSYNCED + " INT " +
                    ");";


    private static final String ANTONYM_TABLE_CREATE =
            "CREATE TABLE " + TABLE_ANTONYM + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_TRANSLATION + " TEXT, " +
                    COLUMN_ANTONYM + " TEXT, " +
                    COLUMN_ANTONYM_TRANSLATION + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_ISFAVORITE + " INT " +
                    //COLUMN_CREATED + " TEXT,  " +
                    //COLUMN_MODIFIED + " TEXT, " +
                    //COLUMN_ISDELETED + " INT, " +
                    //COLUMN_ISSYNCED + " INT " +
                    ");";

    private static final String SYNONYM_TABLE_CREATE =
            "CREATE TABLE " + TABLE_SYNONYM + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_TRANSLATION + " TEXT, " +
                    COLUMN_SYNONYM + " TEXT, " +
                    COLUMN_SYNONYM_TRANSLATION + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_ISFAVORITE + " INT " +
                    //COLUMN_CREATED + " TEXT,  " +
                    //COLUMN_MODIFIED + " TEXT, " +
                    //COLUMN_ISDELETED + " INT, " +
                    //COLUMN_ISSYNCED + " INT " +
                    ");";

    private static final String COMMONMISTAKE_TABLE_CREATE =
            "CREATE TABLE " + TABLE_COMMONMISTAKE + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_COMMONMISTAKE + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_ISFAVORITE + " INT " +
                    //COLUMN_CREATED + " TEXT,  " +
                    //COLUMN_MODIFIED + " TEXT, " +
                    //COLUMN_ISDELETED + " INT, " +
                    //COLUMN_ISSYNCED + " INT " +
                    ");";

    private static final String IDIOMS_TABLE_CREATE =
            "CREATE TABLE " + TABLE_IDIOM + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_TRANSLATION + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_ISFAVORITE + " INT " +
                    //COLUMN_CREATED + " TEXT,  " +
                    //COLUMN_MODIFIED + " TEXT, " +
                    //COLUMN_ISDELETED + " INT, " +
                    //COLUMN_ISSYNCED + " INT " +
                    ");";

    private static final String SLANG_TABLE_CREATE =
            "CREATE TABLE " + TABLE_SLANG + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_TRANSLATION + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_ISFAVORITE + " INT " +
                    //COLUMN_CREATED + " TEXT,  " +
                    //COLUMN_MODIFIED + " TEXT, " +
                    //COLUMN_ISDELETED + " INT, " +
                    //COLUMN_ISSYNCED + " INT " +
                    ");";


    public OneDicDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(WORDS_TABLE_CREATE);
        db.execSQL(ANTONYM_TABLE_CREATE);
        db.execSQL(SYNONYM_TABLE_CREATE);
        db.execSQL(COMMONMISTAKE_TABLE_CREATE);
        db.execSQL(IDIOMS_TABLE_CREATE);
        db.execSQL(SLANG_TABLE_CREATE);
        Log.i(LOGTAG,"Words tabla has been created");

        seed(db);
        Log.i(LOGTAG,"Seed executed");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANTONYM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SYNONYM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMONMISTAKE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IDIOM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SLANG);

        // create new tables
        onCreate(db);
    }


    private void seed(SQLiteDatabase db){

        List<Word> words = new ArrayList<>();
        words.add(new Word("hello","həˈləʊ","سلام",""));
        words.add(new Word("good","ɡʊd","خوب",""));
        words.add(new Word("bye","baɪ","خداحافظ",""));
        for (Word model: words) {
            ContentValues values = new ContentValues();
            values.put(OneDicDBOpenHelper.COLUMN_TITLE,model.getTitle());
            values.put(OneDicDBOpenHelper.COLUMN_PRONUNCIATION,model.getPronunciation());
            values.put(OneDicDBOpenHelper.COLUMN_TRANSLATION,model.getTranslation());
            db.insert(OneDicDBOpenHelper.TABLE_WORD,null,values);
        }

        List<CommonMistake> commonMistakes = new ArrayList<>();
        commonMistakes.add(new CommonMistake("My shirt is loose","My shirt is lose","",""));
        commonMistakes.add(new CommonMistake("One of my friends lives in Canada","One of my friend lives in Canada","",""));
        commonMistakes.add(new CommonMistake("She is afraid of the dog","She is afraid from the dog","",""));
        for (CommonMistake model: commonMistakes) {
            ContentValues values = new ContentValues();
            values.put(OneDicDBOpenHelper.COLUMN_TITLE,model.getTitle());
            values.put(OneDicDBOpenHelper.COLUMN_COMMONMISTAKE,model.getCommonMistake());
            values.put(OneDicDBOpenHelper.COLUMN_DESCRIPTION,model.getDescription());
            db.insert(OneDicDBOpenHelper.TABLE_COMMONMISTAKE,null,values);
        }

        List<Antonym> antonyms = new ArrayList<>();
        antonyms.add(new Antonym("happy","خوشحال","sad","غمگین",""));
        antonyms.add(new Antonym("angel","فرشته","evil","شیطان",""));
        antonyms.add(new Antonym("day","روز","night","شب",""));
        for (Antonym model: antonyms){
            ContentValues values = new ContentValues();
            values.put(OneDicDBOpenHelper.COLUMN_TITLE,model.getTitle());
            values.put(OneDicDBOpenHelper.COLUMN_TRANSLATION,model.getTranslation());
            values.put(OneDicDBOpenHelper.COLUMN_ANTONYM,model.getAntonym());
            values.put(OneDicDBOpenHelper.COLUMN_ANTONYM_TRANSLATION,model.getAntonymTranslation());
            db.insert(OneDicDBOpenHelper.TABLE_ANTONYM,null,values);
        }

        List<Synonym> synonyms = new ArrayList<>();
        synonyms.add(new Synonym("general","عمومی","common","common",""));
        synonyms.add(new Synonym("different","مختلف","various","متفاوت",""));
        synonyms.add(new Synonym("baby","بچه","child","کودک",""));
        for (Synonym model : synonyms){
            ContentValues values = new ContentValues();
            values.put(OneDicDBOpenHelper.COLUMN_TITLE,model.getTitle());
            values.put(OneDicDBOpenHelper.COLUMN_TRANSLATION,model.getTranslation());
            values.put(OneDicDBOpenHelper.COLUMN_SYNONYM,model.getSynonym());
            values.put(OneDicDBOpenHelper.COLUMN_SYNONYM_TRANSLATION,model.getSynonymTranslation());
            values.put(OneDicDBOpenHelper.COLUMN_DESCRIPTION,model.getDescription());
            db.insert(OneDicDBOpenHelper.TABLE_SYNONYM,null,values);
        }

        List<Idiom> idioms = new ArrayList<>();
        idioms.add(new Idiom("A good mixer","آدم زود جوش و اجتماعی",""));
        idioms.add(new Idiom("Good company","آدم جالب و خوش صحبت – آدم خوش مشرب",""));
        idioms.add(new Idiom("Give somebody one’s word of honor","به کسی قول شرف دادن",""));
        for (Idiom model: idioms){
            ContentValues values = new ContentValues();
            values.put(OneDicDBOpenHelper.COLUMN_TITLE,model.getTitle());
            values.put(OneDicDBOpenHelper.COLUMN_TRANSLATION,model.getTranslation());
            values.put(OneDicDBOpenHelper.COLUMN_DESCRIPTION,model.getDescription());
            db.insert(OneDicDBOpenHelper.TABLE_IDIOM,null,values);
        }

        List<Slang> slangs = new ArrayList<>();
        slangs.add(new Slang("Awesome","خیلی خوب،عالی",""));
        slangs.add(new Slang("Cool","کنایه از خیلی باحال و معرکه بودن",""));
        slangs.add(new Slang("Bottom line","نکته اصلی و مهم",""));
        for (Slang model: slangs){
            ContentValues values = new ContentValues();
            values.put(OneDicDBOpenHelper.COLUMN_TITLE,model.getTitle());
            values.put(OneDicDBOpenHelper.COLUMN_TRANSLATION,model.getTranslation());
            values.put(OneDicDBOpenHelper.COLUMN_DESCRIPTION,model.getDescription());
            db.insert(OneDicDBOpenHelper.TABLE_SLANG,null,values);
        }


    }
}
