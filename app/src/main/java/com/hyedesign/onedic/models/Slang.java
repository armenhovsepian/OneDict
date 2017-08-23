package com.hyedesign.onedic.models;

/**
 * Created by Albaloo on 2/24/2017.
 */

public class Slang extends BaseModel {

    public Slang(){}

    public Slang(String title, String translation,String description){
        this.title = title;
        this.translation = translation;
        this.description = description;
    }

}
