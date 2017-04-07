package com.hyedesign.onedic;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.hyedesign.onedic.db.AntonymDataSource;
import com.hyedesign.onedic.models.Antonym;
import com.hyedesign.onedic.utilities.FontResets;


/**
 * Created by Albaloo on 2/23/2017.
 */

public class AntonymActivity extends AppCompatActivity {
    AntonymDataSource dataSource;

    EditText etTitle,etTranslation,etAntonym,etAntonymTranslation,etDesc;

    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antonym);

        initialize();

        id = getIntent().getLongExtra("id",0);
        if (id != 0) {
            Antonym model = dataSource.getById(id);
            etTitle.setText(model.getTitle());
            etTranslation.setText(model.getTranslation());
            etAntonym.setText(model.getAntonym());
            etAntonymTranslation.setText(model.getAntonymTranslation());
            etDesc.setText(model.getDescription());
        }
    }

    private void initialize() {
        dataSource = new AntonymDataSource(this);
        etTitle = (EditText)findViewById(R.id.etAntonymTitle);
        etTranslation = (EditText)findViewById(R.id.etAntonymTitleTranslate);
        etAntonym = (EditText)findViewById(R.id.etAntonym);
        etAntonymTranslation = (EditText)findViewById(R.id.etAntonymTranslate);
        etDesc = (EditText)findViewById(R.id.etAntonymDescription);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setTitle("Antonym");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        FontResets.setFont(AntonymActivity.this, (ViewGroup) getWindow().getDecorView());
    }

    private void goBack(){
        Intent intent = new Intent(this,AntonymsActivity.class);
        startActivity(intent);
        finish();
    }

    public void saveItem() {
        Antonym model = new Antonym();
        model.setId(id);
        model.setTitle(etTitle.getText().toString());
        model.setTranslation(etTranslation.getText().toString());
        model.setAntonym(etAntonym.getText().toString());
        model.setAntonymTranslation(etAntonymTranslation.getText().toString());
        model.setDescription(etDesc.getText().toString());

        if (id == 0) {
            dataSource.create(model);
        } else {
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

        finish();
        return super.onOptionsItemSelected(item);
    }

}
