package com.hyedesign.onedic;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.hyedesign.onedic.db.WordDataSource;
import com.hyedesign.onedic.models.Word;
import com.hyedesign.onedic.utilities.FontResets;

/**
 * Created by Albaloo on 2/19/2017.
 */

public class WordActivity extends AppCompatActivity {

    WordDataSource dataSource;

    EditText etWord,etDesc,etPro,etTrn;

    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        initialize();
        dataSource = new WordDataSource(this);

        id = getIntent().getLongExtra("id",0);
        if (id != 0) {
            Word word = dataSource.getById(id);
            etWord.setText(word.getTitle());
            etPro.setText(word.getPronunciation());
            etTrn.setText(word.getTranslation());
            etDesc.setText(word.getDescription());
        }

    }

    public void initialize(){
        etWord = (EditText)findViewById(R.id.etWrdTitle);
        etPro = (EditText)findViewById(R.id.etWrdPronunciation);
        etTrn = (EditText)findViewById(R.id.etWrdTranslate);
        etDesc = (EditText)findViewById(R.id.etWrdDescription);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Word");
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        FontResets.setFont(WordActivity.this, (ViewGroup) getWindow().getDecorView());
    }

    private void goBack(){
        //Intent intent = new Intent(this, WordsActivity.class);
        //startActivity(intent);
        finish();
    }

    public void saveItem() {
        Word model = new Word();
        model.setId(id);
        model.setTitle(etWord.getText().toString());
        model.setPronunciation(etPro.getText().toString());
        model.setTranslation(etTrn.getText().toString());
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
