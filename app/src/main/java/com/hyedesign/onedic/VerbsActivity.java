package com.hyedesign.onedic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.hyedesign.onedic.db.WordDataSource;

/**
 * Created by Albaloo on 2/17/2017.
 */

public class VerbsActivity extends BaseMenuActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbs);
        initialize();
    }


    private void initialize() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Verbs");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

}
