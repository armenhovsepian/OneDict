package com.hyedesign.onedic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hyedesign.onedic.utilities.FontResets;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button words,idioms,synonyms,antonyms,commonMistakes,slangs,verbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        FontResets.setFont(MainActivity.this, (ViewGroup) getWindow().getDecorView());
    }

    private void initialize() {
        words = (Button)findViewById(R.id.btnWords);
        words.setOnClickListener(this);

        idioms = (Button)findViewById(R.id.btnIdioms);
        idioms.setOnClickListener(this);

        synonyms = (Button)findViewById(R.id.btnSynonyms);
        synonyms.setOnClickListener(this);

        antonyms = (Button)findViewById(R.id.btnAntonyms);
        antonyms.setOnClickListener(this);

        commonMistakes = (Button)findViewById(R.id.btnCommonMistakes);
        commonMistakes.setOnClickListener(this);

        slangs = (Button)findViewById(R.id.btnSlangs);
        slangs.setOnClickListener(this);

        verbs = (Button)findViewById(R.id.btnVerbs);
        verbs.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btnIdioms:
                intent = new Intent(this, IdiomsActivity.class);
                break;

            case R.id.btnAntonyms:
                intent = new Intent(this, AntonymsActivity.class);
                break;

            case R.id.btnSynonyms:
                intent = new Intent(this, SynonymsActivity.class);
                break;

            case R.id.btnVerbs:
                intent = new Intent(this,VerbsActivity.class);
                break;

            case R.id.btnWords:
                intent = new Intent(this, WordsActivity.class);
                break;

            case R.id.btnCommonMistakes:
                intent = new Intent(this, CommonMistakesActivity.class);
                break;

            case R.id.btnSlangs:
                intent = new Intent(this, SlangsActivity.class);
                break;

            default:
                intent = new Intent(this, MainActivity.class);
        }

        this.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
