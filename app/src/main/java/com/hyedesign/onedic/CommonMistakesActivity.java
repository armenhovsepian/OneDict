package com.hyedesign.onedic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.hyedesign.onedic.adapter.CommonMistakeAdapter;
import com.hyedesign.onedic.db.CommonMistakeDataSource;
import com.hyedesign.onedic.models.CommonMistake;

import java.util.List;

/**
 * Created by Albaloo on 2/24/2017.
 */

public class CommonMistakesActivity extends BaseMenuActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    TextView commonMiskateTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commonmistakes);
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
        commonMiskateTotal.setText(String.valueOf(models.size()));

        List<CommonMistake> commonMistakes = (List<CommonMistake>)(List<?>) models;
        mAdapter = new CommonMistakeAdapter(commonMistakes);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // DIVIDER
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        mRecyclerView.setAdapter(mAdapter);
    }


    private void initialize() {
        dataSource = new CommonMistakeDataSource(this);
        commonMiskateTotal = (TextView)findViewById(R.id.tvCommonMistakeTotal);
        mRecyclerView = (RecyclerView) findViewById(R.id.commonMistake_recycler_view);

        //setTitle("Idioms");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Common Mistakes");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void goToIntent() {
        Intent intent = new Intent(this,CommonMistakeActivity.class);
        startActivity(intent);
    }
}
