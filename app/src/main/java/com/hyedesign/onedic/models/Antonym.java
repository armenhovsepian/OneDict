package com.hyedesign.onedic.models;

/**
 * Created by Albaloo on 2/21/2017.
 */

public class Antonym extends BaseModel {
    private String antonym;
    private String antonymTranslation;

    public Antonym(){}

    public Antonym(String title,String translation,String antonym,String antonymTranslation,String description){
        this.title = title;
        this.translation = translation;
        this.antonym = antonym;
        this.antonymTranslation = antonymTranslation;
        this.description = description;
    }

    public void setAntonym(String ant){
        this.antonym = ant;
    }
    public String getAntonym(){
        return antonym;
    }

    public String getAntonymTranslation(){ return this.antonymTranslation; }
    public void setAntonymTranslation(String antonymTranslation){ this.antonymTranslation = antonymTranslation; }
}
