package com.hyedesign.onedic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hyedesign.onedic.adapter.SynonymAdapter;
import com.hyedesign.onedic.db.SynonymDataSource;
import com.hyedesign.onedic.models.Synonym;

import java.util.List;

/**
 * Created by Albaloo on 2/17/2017.
 */

public class SynonymsActivity extends BaseMenuActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synonyms);

        initialize();

        models = dataSource.getAll();
/*        if (models.size() == 0){
            dataSource.seed();
            models = dataSource.getAll();
        }*/

        refreshDisplay();
    }

    private void initialize() {
        dataSource = new SynonymDataSource(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.synonym_recycler_view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Synonyms");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SynonymActivity.class));
            }
        });
    }

    @Override
    public void refreshDisplay() {
        List<Synonym> synonyms = (List<Synonym>)(List<?>)models;
        mAdapter = new SynonymAdapter(synonyms);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // DIVIDER
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void goToIntent() {
        Intent intent = new Intent(this,SynonymActivity.class);
        startActivity(intent);
    }
}
