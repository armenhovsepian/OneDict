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

import com.hyedesign.onedic.db.IdiomDataSource;
import com.hyedesign.onedic.models.Idiom;
import com.hyedesign.onedic.utilities.FontResets;

/**
 * Created by Albaloo on 2/24/2017.
 */

public class IdiomActivity extends AppCompatActivity {
    IdiomDataSource dataSource;

    EditText etTitle,etTranslate,etDesc;

    long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idiom);

        initialize();

        id = getIntent().getLongExtra("id",0);
        if (id != 0) {
            Idiom model = dataSource.getById(id);
            etTitle.setText(model.getTitle());
            etTranslate.setText(model.getTranslation());
            etDesc.setText(model.getDescription());
        }
    }

    private void initialize() {
        dataSource = new IdiomDataSource(this);
        etTitle = (EditText)findViewById(R.id.etIdiomTitle);
        etTranslate = (EditText)findViewById(R.id.etIdiomTranslation);
        etDesc = (EditText)findViewById(R.id.etIdiomDescription);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Idiom");
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        //actionBar.hide();
        //actionBar.show();
        FontResets.setFont(IdiomActivity.this, (ViewGroup) getWindow().getDecorView());
    }

    public void saveItem() {
        Idiom model = new Idiom();
        model.setId(id);
        model.setTitle(etTitle.getText().toString());
        model.setTranslation(etTranslate.getText().toString());
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

    private void goBack(){
        Intent intent = new Intent(this,IdiomsActivity.class);
        startActivity(intent);
        finish();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
