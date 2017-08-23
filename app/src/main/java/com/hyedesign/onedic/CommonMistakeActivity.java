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
import android.widget.ImageButton;
import android.widget.Toast;

import com.hyedesign.onedic.db.CommonMistakeDataSource;
import com.hyedesign.onedic.models.CommonMistake;
import com.hyedesign.onedic.utilities.FontResets;


/**
 * Created by Albaloo on 2/24/2017.
 */

public class CommonMistakeActivity extends AppCompatActivity {
    CommonMistakeDataSource dataSource;

    EditText etTitle,etCommonMistake,etDesc;

    ImageButton btnDelete;

    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commonmistake);

        initialize();

        id = getIntent().getLongExtra("id",0);
        if (id != 0) {
            CommonMistake model = dataSource.getById(id);
            etTitle.setText(model.getTitle());
            etCommonMistake.setText(model.getCommonMistake());
            etDesc.setText(model.getDescription());
        }else{
            //btnDelete.setEnabled(false);
        }
    }

    private void initialize() {
        dataSource = new CommonMistakeDataSource(this);
        etTitle = (EditText)findViewById(R.id.etCommonMistakeTitle);
        etCommonMistake = (EditText)findViewById(R.id.etCommonMistake);
        etDesc = (EditText)findViewById(R.id.etCommonMistakeDescription);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Common Mistake");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        FontResets.setFont(CommonMistakeActivity.this, (ViewGroup) getWindow().getDecorView());
    }

    private void goBack(){
        Intent intent = new Intent(this,CommonMistakesActivity.class);
        startActivity(intent);
        finish();
    }


    public void saveItem() {
        CommonMistake model = new CommonMistake();
        model.setId(id);
        model.setTitle(etTitle.getText().toString());
        model.setCommonMistake(etCommonMistake.getText().toString());
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
