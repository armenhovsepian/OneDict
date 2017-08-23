package com.hyedesign.onedic.models;

/**
 * Created by Albaloo on 2/21/2017.
 */

public class CommonMistake extends BaseModel {

    private String commonMistake;

    public CommonMistake(){}

    public CommonMistake(String title,String commonMistake,String translation,String description){
        this.title = title;
        this.commonMistake = commonMistake;
        this.translation = translation;
        this.description = description;
    }

    public String getCommonMistake(){ return this.commonMistake; }
    public void setCommonMistake(String commonMistake) {this.commonMistake = commonMistake; }
}
