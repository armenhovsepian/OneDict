package com.hyedesign.onedic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.hyedesign.onedic.adapter.DividerItemDecoration;
import com.hyedesign.onedic.adapter.IdiomAdapter;
import com.hyedesign.onedic.db.IdiomDataSource;
import com.hyedesign.onedic.models.Idiom;

import java.util.List;

/**
 * Created by Albaloo on 2/17/2017.
 */

public class IdiomsActivity extends BaseMenuActivity {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;

    TextView idiomTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idioms);

        initialize();

        models = dataSource.getAll();
        if(models.size() == 0){
            dataSource.seed();
            models = dataSource.getAll();
        }

        refreshDisplay();

    }

    @Override
    public void refreshDisplay() {
        idiomTotal.setText(String.valueOf(models.size()));
        //  cast a List of supertypes to a List of subtypes
        List<Idiom> idioms = (List<Idiom>)(List<?>) models;
        mAdapter = new IdiomAdapter(idioms);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // DIVIDER
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void goToIntent() {
        Intent intent = new Intent(this,IdiomActivity.class);
        startActivity(intent);
    }

    private void initialize() {
        dataSource = new IdiomDataSource(this);
        idiomTotal = (TextView)findViewById(R.id.tvIdiomTotal);
        mRecyclerView = (RecyclerView) findViewById(R.id.idiom_recycler_view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Idioms");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_action_all:
                models = dataSource.getAll();
                refreshDisplay();
                return true;

            case R.id.menu_action_fav:
                models = dataSource.getFiltered("IsFavorite = '1'","Id ASC");
                refreshDisplay();
                return true;

            case R.id.menu_action_alpha:
                models = dataSource.getFiltered(null,"Title ASC");
                refreshDisplay();
                return true;

            case R.id.menu_action_add:
                goToIntent();
                return true;

            case R.id.menu_action_sync:
                Toast.makeText(this, "Action Sync selected", Toast.LENGTH_SHORT).show();
                return true;

            default:
                //finish();
                onBackPressed();
                return super.onOptionsItemSelected(item);
        }
    }

}
