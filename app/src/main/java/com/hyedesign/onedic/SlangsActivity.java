package com.hyedesign.onedic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.hyedesign.onedic.adapter.SlangAdapter;
import com.hyedesign.onedic.db.SlangDataSource;
import com.hyedesign.onedic.models.Slang;

import java.util.List;

/**
 * Created by Albaloo on 2/17/2017.
 */

public class SlangsActivity extends BaseMenuActivity {
    private static final String LOGTAG = "SLANGS_ACTIVITY";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slangs);

        initialize();

        models = dataSource.getAll();
/*        if (models.size() == 0){
            dataSource.seed();
            models = dataSource.getAll();
        }*/

        refreshDisplay();
    }

    private void initialize() {
        dataSource = new SlangDataSource(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.slang_recycler_view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Slangs");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SlangActivity.class));
            }
        });
    }

    @Override
    public void refreshDisplay() {
        List<Slang> slangs = (List<Slang>)(List<?>)models;
        mAdapter = new SlangAdapter(slangs);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // DIVIDER
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }


}
