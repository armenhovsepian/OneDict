package com.hyedesign.onedic.utilities;

import android.content.Context;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Albaloo on 4/11/2017.
 */

public class InternalFileStorage {

    private final String FILE_NAME = "myfile.txt";

    public String getPath(){
        File file = App.getAppContext().getFilesDir();
        String path = file.getAbsolutePath();
        return path;
    }

    public void createFile(String text) throws IOException {
        FileOutputStream fos = App.getAppContext().openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
        fos.write(text.getBytes());
        fos.close();

        App.toast("File written to disk");
    }

    public void readFile() throws IOException {

        FileInputStream fis = App.getAppContext().openFileInput(FILE_NAME);
        BufferedInputStream bis = new BufferedInputStream(fis);
        StringBuffer buffer = new StringBuffer();

        while (bis.available() != 0){
            char c = (char)bis.read();
            buffer.append(c);
        }

        bis.close();
        fis.close();

        App.toast(buffer.toString());
    }



    public void createJsonData() throws IOException, JSONException {

        JSONArray data = getNewJSONData();

        String text = data.toString();

        FileOutputStream fos = App.getAppContext().openFileOutput("tours", Context.MODE_PRIVATE);
        fos.write(text.getBytes());
        fos.close();

        App.toast("File written to disk\n" + data.toString());
    }

    @NonNull
    protected JSONArray getNewJSONData() throws JSONException {

        JSONArray data = new JSONArray();
        JSONObject tour;

        tour = new JSONObject();
        tour.put("tour", "Salton Sea");
        tour.put("price", 500);
        data.put(data);

        tour = new JSONObject();
        tour.put("tour", "Death Vally");
        tour.put("price", 700);
        data.put(data);

        tour = new JSONObject();
        tour.put("tour", "San Franscisco");
        tour.put("price", 1200);
        data.put(data);
        return data;
    }


    public void readJsonData() throws IOException, JSONException {

        FileInputStream fis = App.getAppContext().openFileInput("tours");
        BufferedInputStream bis = new BufferedInputStream(fis);
        StringBuffer buffer = new StringBuffer();

        while (bis.available() != 0){
            char c = (char)bis.read();
            buffer.append(c);
        }

        bis.close();
        fis.close();

        StringBuffer toursBuffer = new StringBuffer();
        JSONArray data = new JSONArray(buffer.toString());
        for (int i = 0; i<data.length();i++){
            String tour = data.getJSONObject(i).getString("tour");
            //double price = data.getJSONObject(i).getDouble("price");
            toursBuffer.append(tour + "\n");
        }

        App.toast(toursBuffer.toString());
    }



}
