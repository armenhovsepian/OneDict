package com.hyedesign.onedic;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.hyedesign.onedic.db.SynonymDataSource;
import com.hyedesign.onedic.models.CommonMistake;
import com.hyedesign.onedic.models.Synonym;
import com.hyedesign.onedic.utilities.FontResets;


/**
 * Created by Albaloo on 2/23/2017.
 */

public class SynonymActivity extends AppCompatActivity {
    SynonymDataSource dataSource;

    EditText etTitle,etTranslation,etSynonym,etSynonymTranslation,etDesc;

    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synonym);

        initialize();

        id = getIntent().getLongExtra("id",0);
        if (id != 0) {
            Synonym model = dataSource.getById(id);
            etTitle.setText(model.getTitle());
            etTranslation.setText(model.getTranslation());
            etSynonym.setText(model.getSynonym());
            etSynonymTranslation.setText(model.getSynonymTranslation());
            etDesc.setText(model.getDescription());
        }
    }

    private void initialize() {
        dataSource = new SynonymDataSource(this);

        etTitle = (EditText)findViewById(R.id.etSynonymTitle);
        etTranslation = (EditText) findViewById(R.id.etSynonymTitleTranslate);
        etSynonym = (EditText)findViewById(R.id.etSynonym);
        etSynonymTranslation = (EditText)findViewById(R.id.etSynonymTranslate);
        etDesc = (EditText)findViewById(R.id.etSynonymDescription);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setTitle("Synonym");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        FontResets.setFont(SynonymActivity.this, (ViewGroup) getWindow().getDecorView());
    }

    private void goBack(){
        Intent intent = new Intent(this, SynonymsActivity.class);
        startActivity(intent);
        finish();
    }

    public void saveItem() {
        Synonym model = new Synonym();
        model.setId(id);
        model.setTitle(etTitle.getText().toString());
        model.setTranslation(etTranslation.getText().toString());
        model.setSynonym(etSynonym.getText().toString());
        model.setSynonymTranslation(etSynonymTranslation.getText().toString());
        model.setDescription(etDesc.getText().toString());

        if (id == 0) {
            dataSource.create(model);
        }else{
            dataSource.update(model);
        }

        Toast.makeText(getApplicationContext(), R.string.saveChanges, Toast.LENGTH_SHORT).show();
        goBack();
    }

    public void deleteItem() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.delete)
                .setMessage(R.string.really_delete)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataSource.delete(id);
                        Toast.makeText(getApplicationContext(), R.string.removedFromCollection, Toast.LENGTH_SHORT).show();
                        goBack();
                    }

                })
                .setNegativeButton(R.string.no, null)
                .show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                Toast.makeText(this,"home",Toast.LENGTH_SHORT);
                break;

            case R.id.menu_action_save:
                saveItem();
                break;

            case R.id.menu_action_delete:
                deleteItem();
                break;

            default:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
