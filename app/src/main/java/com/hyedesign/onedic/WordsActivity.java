package com.hyedesign.onedic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyedesign.onedic.adapter.WordAdapter;
import com.hyedesign.onedic.db.WordDataSource;
import com.hyedesign.onedic.models.Word;
import com.hyedesign.onedic.utilities.App;
import com.hyedesign.onedic.utilities.FontResets;

import java.util.List;

/**
 * Created by Albaloo on 2/17/2017.
 */

public class WordsActivity extends BaseMenuActivity {

    private static final String LOGTAG = WordsActivity.class.getSimpleName();

    TextView wordTotal;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);

        initialize();

        models = dataSource.getAll();
/*        if (models.size() == 0){
            dataSource.seed();
            models = dataSource.getAll();
        }*/

        refreshDisplay();

        /*
        for (Word item:words){
            dataSource.favorite(item.getId(),"0");
        }

        words = dataSource.getAll();

        */

        //createData();
/*
        ArrayList<String> myDataset = new ArrayList<>();
        myDataset.add("www");
        myDataset.add("eee");
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset);


        mRecyclerView.setAdapter(mAdapter);
*/






    }

    private void initialize() {
        wordTotal = (TextView)findViewById(R.id.tvWordTotal);
        dataSource = new WordDataSource(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Words");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),WordActivity.class));
            }
        });

        FontResets.setFont(WordsActivity.this, (ViewGroup) getWindow().getDecorView());
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }

    @Override
    public void refreshDisplay() {
        wordTotal.setText(String.valueOf(models.size()));
        List<Word> words = (List<Word>)(List<?>)models;
        mAdapter = new WordAdapter(words);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // DIVIDER
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void goToIntent() {
        Intent intent = new Intent(this,WordActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshDisplay();
    }
}
