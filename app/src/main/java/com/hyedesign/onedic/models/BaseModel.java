package com.hyedesign.onedic.models;

import android.support.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Albaloo on 2/21/2017.
 */

public class BaseModel {

    protected long id;
    protected String title;
    protected String translation;
    protected String description;
    protected boolean isFavorite;
    //protected Date created;
    //protected Date modified;
    //protected boolean isDeleted;
    //protected boolean isSynced;

    public void setId(long id){this.id = id;}
    public long getId(){ return id;}

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return title;
    }

    public void setTranslation(String trans){
        translation = trans;
    }
    public String getTranslation(){
        return translation;
    }

    public void setDescription(String desc){
        this.description = desc;
    }
    public String getDescription(){
        return description;
    }

    public void setFavorite(int favorite){
        this.isFavorite = favorite == 1;
    }
    public boolean getFavorite(){ return isFavorite; }

    //public void setCreated(String datestr){ this.created = dateConvert(datestr); }
    //public Date getCreated(){return created;}

    //public void setModified(String datestr){this.modified = dateConvert(datestr);}
    //public Date getModified(){return modified;}

    //public void setDeleted(int deleted){this.isDeleted = deleted == 1;}
    //public boolean getDeleted(){return isDeleted;}

    //public void setSynced(int synced){ this.isSynced = synced == 1;}
    //public boolean getSynced(){return isSynced;}


/*    @Nullable
    private Date dateConvert(String datestr) {
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            return (Date)formatter.parse(datestr);
        }catch (Exception ex){
            return null;
        }
    }*/
}
