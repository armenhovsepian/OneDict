package com.hyedesign.onedic;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hyedesign.onedic.interfaces.IDataSource;
import com.hyedesign.onedic.models.BaseModel;

import java.util.List;

/**
 * Created by Albaloo on 2/27/2017.
 */

public class BaseMenuActivity extends AppCompatActivity {
    ActionBar actionBar;
    IDataSource dataSource;
    List<BaseModel> models;
    String title;
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setIcon(R.drawable.btn_star_big_on);
        LayoutInflater inflator = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.main_menu, null);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(v);
    }*/


    public void refreshDisplay() {
    }

    public void goToIntent(){
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_action_all:
                models = dataSource.getAll();
                refreshDisplay();
                break;

            case R.id.menu_action_fav:
                models = dataSource.getFiltered("IsFavorite = '1'","Id ASC");
                refreshDisplay();
                break;

            case R.id.menu_action_alpha:
                models = dataSource.getFiltered(null,"Title ASC");
                refreshDisplay();
                break;

/*            case R.id.menu_action_add:
                goToIntent();
                break;

            case R.id.menu_action_sync:
                Toast.makeText(this, "Action Sync selected", Toast.LENGTH_SHORT).show();
                break;*/

            default:
                onBackPressed();
                //return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }
}
