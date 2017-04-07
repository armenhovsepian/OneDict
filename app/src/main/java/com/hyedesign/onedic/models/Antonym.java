package com.hyedesign.onedic.models;

/**
 * Created by Albaloo on 2/21/2017.
 */

public class Antonym extends BaseModel {
    private String antonym;
    private String antonymTranslation;

    public void setAntonym(String ant){
        this.antonym = ant;
    }
    public String getAntonym(){
        return antonym;
    }

    public String getAntonymTranslation(){ return this.antonymTranslation; }
    public void setAntonymTranslation(String antonymTranslation){ this.antonymTranslation = antonymTranslation; }
}
