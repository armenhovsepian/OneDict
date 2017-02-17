package com.hyedesign.onedic;

import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hyedesign.onedic.utilities.MyTextUtil;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyTextUtil util = new MyTextUtil();

        TextView tv = (TextView) findViewById(R.id.tvTest);
        tv.setText(util.getValue());
    }


    public void redirect_onclick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.btnIdioms:
                intent = new Intent(this,IdiomsActivity.class);
                break;

            case R.id.btnAntithetical:
                intent = new Intent(this,AntitheticalActivity.class);
                break;

            case R.id.btnCommon:
                intent = new Intent(this,CommonActivity.class);
                break;

            case R.id.btnSynonymous:
                intent = new Intent(this,SynonymousActivity.class);
                break;

            case R.id.btnVerbs:
                intent = new Intent(this,VerbsActivity.class);
                break;

            case R.id.btnWords:
                intent = new Intent(this, WordsActivity.class);
                break;

            default:
                intent = new Intent(this,MainActivity.class);
        }

        this.startActivity(intent);
    }

}
