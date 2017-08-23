package com.hyedesign.onedic.models;

/**
 * Created by Albaloo on 2/18/2017.
 */

public class Word extends BaseModel{

    public Word(){}

    public Word(String title,String pronunciation,String translation,String description){
        this.title = title;
        this.pronunciation = pronunciation;
        this.translation = translation;
        this.description = description;
    }

    private String pronunciation;

    public void setPronunciation(String pro){
        pronunciation = pro;
    }
    public String getPronunciation(){
        return pronunciation;
    }


}
