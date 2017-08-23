package com.hyedesign.onedic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hyedesign.onedic.adapter.DividerItemDecoration;
import com.hyedesign.onedic.adapter.IdiomAdapter;
import com.hyedesign.onedic.db.IdiomDataSource;
import com.hyedesign.onedic.models.Idiom;

import java.util.List;

public class IdiomsActivity extends BaseMenuActivity {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idioms);

        initialize();

        models = dataSource.getAll();
/*        if(models.size() == 0){
            dataSource.seed();
            models = dataSource.getAll();
        }*/

        refreshDisplay();

    }

    @Override
    public void refreshDisplay() {

        //  cast a List of supertypes to a List of subtypes
        List<Idiom> idioms = (List<Idiom>)(List<?>) models;
        mAdapter = new IdiomAdapter(idioms);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // DIVIDER
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
    }


    private void initialize() {
        dataSource = new IdiomDataSource(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.idiom_recycler_view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Idioms");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),IdiomActivity.class));
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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

            default:
                //finish();
                onBackPressed();
                return super.onOptionsItemSelected(item);
        }
    }

}
