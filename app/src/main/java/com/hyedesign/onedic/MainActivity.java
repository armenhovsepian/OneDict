package com.hyedesign.onedic;

import android.content.Intent;
import android.content.IntentSender;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyedesign.onedic.db.AntonymDataSource;
import com.hyedesign.onedic.db.CommonMistakeDataSource;
import com.hyedesign.onedic.db.IdiomDataSource;
import com.hyedesign.onedic.db.OneDicDBOpenHelper;
import com.hyedesign.onedic.db.SlangDataSource;
import com.hyedesign.onedic.db.SynonymDataSource;
import com.hyedesign.onedic.db.WordDataSource;
import com.hyedesign.onedic.interfaces.IDataSource;
import com.hyedesign.onedic.models.Antonym;
import com.hyedesign.onedic.models.BackupModel;
import com.hyedesign.onedic.models.BaseModel;
import com.hyedesign.onedic.models.CommonMistake;
import com.hyedesign.onedic.models.Idiom;
import com.hyedesign.onedic.models.Slang;
import com.hyedesign.onedic.models.Synonym;
import com.hyedesign.onedic.models.Word;
import com.hyedesign.onedic.utilities.App;
import com.hyedesign.onedic.utilities.BackupHelper;
import com.hyedesign.onedic.utilities.ExternalFileStorage;
import com.hyedesign.onedic.utilities.FontResets;
import com.hyedesign.onedic.utilities.InternalFileStorage;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener{

    Button words,idioms,synonyms,antonyms,commonMistakes,slangs,verbs;
    IDataSource dataSource;
    TextView tvPath,tvExtPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        initializeDataSources();
        initializeStoragePath();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




    }

    private void initializeStoragePath() {
        InternalFileStorage intFileStorage = new InternalFileStorage();
        tvPath.setText(intFileStorage.getPath());

        ExternalFileStorage extFileStorage = new ExternalFileStorage();
        tvExtPath.setText(extFileStorage.getPath());
    }

    private void initialize() {
        words = (Button)findViewById(R.id.btnWords);
        words.setOnClickListener(this);

        idioms = (Button)findViewById(R.id.btnIdioms);
        idioms.setOnClickListener(this);

        synonyms = (Button)findViewById(R.id.btnSynonyms);
        synonyms.setOnClickListener(this);

        antonyms = (Button)findViewById(R.id.btnAntonyms);
        antonyms.setOnClickListener(this);

        commonMistakes = (Button)findViewById(R.id.btnCommonMistakes);
        commonMistakes.setOnClickListener(this);

        slangs = (Button)findViewById(R.id.btnSlangs);
        slangs.setOnClickListener(this);

        verbs = (Button)findViewById(R.id.btnVerbs);
        verbs.setOnClickListener(this);

        tvPath = (TextView)findViewById(R.id.tvPath);
        tvExtPath = (TextView)findViewById(R.id.tvExtPath);

        FontResets.setFont(MainActivity.this, (ViewGroup) getWindow().getDecorView());
    }

    private void initializeDataSources(){
        IDataSource dataSource;

        dataSource = new WordDataSource(this);
        words.setText(getString(R.string.words)+ "\n(" + dataSource.getCount() + ")");
        //words.setText(getString(R.string.words)+ "\n(" + BackupHelper.getCount(OneDicDBOpenHelper.TABLE_WORD) + ")");

        dataSource = new SynonymDataSource(this);
        synonyms.setText(getString(R.string.synonyms)+ "\n(" + dataSource.getCount() + ")");

        dataSource = new SlangDataSource(this);
        slangs.setText(getString(R.string.slangs)+ "\n(" + dataSource.getCount() + ")");

        dataSource = new IdiomDataSource(this);
        idioms.setText(getString(R.string.idioms)+ "\n(" + dataSource.getCount() + ")");

        dataSource = new CommonMistakeDataSource(this);
        commonMistakes.setText(getString(R.string.commonMistakes) + "\n(" + dataSource.getCount() + ")");

        dataSource = new AntonymDataSource(this);
        antonyms.setText(getString(R.string.antonyms)+ "\n(" + dataSource.getCount() + ")");

    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btnIdioms:
                intent = new Intent(this, IdiomsActivity.class);
                break;

            case R.id.btnAntonyms:
                intent = new Intent(this, AntonymsActivity.class);
                break;

            case R.id.btnSynonyms:
                intent = new Intent(this, SynonymsActivity.class);
                break;

            case R.id.btnVerbs:
                intent = new Intent(this,VerbsActivity.class);
                break;

            case R.id.btnWords:
                intent = new Intent(this, WordsActivity.class);
                break;

            case R.id.btnCommonMistakes:
                intent = new Intent(this, CommonMistakesActivity.class);
                break;

            case R.id.btnSlangs:
                intent = new Intent(this, SlangsActivity.class);
                break;

            default:
                intent = new Intent(this, MainActivity.class);
        }

        this.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String text = "empty";
        if (id == R.id.nav_help) {
            // Handle the camera action
            text = "help";
        } else if (id == R.id.nav_info) {
            text = "info";
        } else if (id == R.id.nav_settings) {
            text = "settings";
        } else if (id == R.id.nav_backup) {
            text = "backup";
            backup();
        } else if (id == R.id.nav_restore) {
                text = "restore";
            restore();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void restore() {
        ExternalFileStorage extFileStorage = new ExternalFileStorage();
        try {

            // Section 1 _ read file
            String data = extFileStorage.readFile();
            Gson gson = new Gson();
            BackupModel backupModel = gson.fromJson(data,BackupModel.class);


            // Section 2 _ restore tables
            IDataSource dataSource;
            dataSource = new WordDataSource(this);
            dataSource.delete();
            for (Word item : backupModel.words){
                dataSource.create(item);
            }


            dataSource = new CommonMistakeDataSource(this);
            dataSource.delete();
            for (CommonMistake item : backupModel.commonMistakes){
                dataSource.create(item);
            }

            dataSource = new AntonymDataSource(this);
            dataSource.delete();
            for (Antonym item : backupModel.antonyms){
                dataSource.create(item);
            }

            dataSource = new SynonymDataSource(this);
            dataSource.delete();
            for (Synonym item : backupModel.synonyms){
                dataSource.create(item);
            }

            dataSource = new IdiomDataSource(this);
            dataSource.delete();
            for (Idiom item : backupModel.idioms){
                dataSource.create(item);
            }

            dataSource = new SlangDataSource(this);
            dataSource.delete();
            for (Slang item : backupModel.slangs){
                dataSource.create(item);
            }


            App.toast("Restored");

        } catch (IOException e) {
            e.printStackTrace();
        }

        initializeDataSources();
    }

    private void backup() {
        BackupModel backupModel;
        backupModel = new BackupModel();
        IDataSource dataSource;

        dataSource= new WordDataSource(this);
        backupModel.words = dataSource.getAll();

        dataSource = new CommonMistakeDataSource(this);
        backupModel.commonMistakes = dataSource.getAll();

        dataSource = new AntonymDataSource(this);
        backupModel.antonyms = dataSource.getAll();

        dataSource = new SynonymDataSource(this);
        backupModel.synonyms = dataSource.getAll();

        dataSource = new IdiomDataSource(this);
        backupModel.idioms = dataSource.getAll();

        dataSource = new SlangDataSource(this);
        backupModel.slangs = dataSource.getAll();

        Gson gson = new Gson();
        String strBackup = gson.toJson(backupModel);

        ExternalFileStorage extFileStorage = new ExternalFileStorage();

        try {
            extFileStorage.createFile(strBackup);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    protected void onResume() {
        super.onResume();
        initializeDataSources();
    }


    private void saveInInternalFolder(){

    }
    private void saveInExternalFolder(){

    }
    private void saveInGoogleDrive(){

    }



}
