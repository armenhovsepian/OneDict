package com.hyedesign.onedic.utilities;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by Albaloo on 3/24/2017.
 */

public class Utils {

    static TextToSpeech tts;

    public static void Toast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void TextToSpeech(Context context, final String toSpeak){

        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!= TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.ENGLISH);
                    tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                }

            }
        });

        tts.setLanguage(Locale.ENGLISH);
        tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        //tts.stop();
        //tts.shutdown();
    }


    private void speak(String text){

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}
