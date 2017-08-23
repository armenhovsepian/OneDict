package com.hyedesign.onedic.models;

/**
 * Created by Albaloo on 2/21/2017.
 */

public class Idiom extends BaseModel {

    public Idiom(){}

    public Idiom(String title, String translation,String description){
        this.title = title;
        this.translation = translation;
        this.description = description;
    }
}
