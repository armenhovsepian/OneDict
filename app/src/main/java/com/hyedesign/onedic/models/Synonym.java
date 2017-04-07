package com.hyedesign.onedic.models;

/**
 * Created by Albaloo on 2/21/2017.
 */

public class Synonym extends BaseModel {
    private String synonym;
    private String synonymTranslation;

    public String getSynonym(){ return this.synonym; }
    public void setSynonym(String synonym){ this.synonym = synonym; }

    public String getSynonymTranslation(){ return this.synonymTranslation; }
    public void setSynonymTranslation(String synonymTranslation){ this.synonymTranslation = synonymTranslation; }

}
