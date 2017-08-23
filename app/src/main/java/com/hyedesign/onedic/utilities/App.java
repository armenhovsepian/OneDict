package com.hyedesign.onedic.utilities;

import android.app.Application;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by Albaloo on 4/10/2017.
 */

public class App extends Application {

    private static Context context;
    private static TextToSpeech tts;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!= TextToSpeech.ERROR) {
                    //this.setLanguage(Locale.ENGLISH);
                    //tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                }

            }
        });
    }

    public static Context getAppContext(){
        return App.context;
    }

    public static void toast(CharSequence message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void toast(int resId){
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }




    public static void textToSpeech(final String toSpeak){

        tts.setLanguage(Locale.ENGLISH);
        tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        //tts.stop();
        //tts.shutdown();

    }

}
