package com.hyedesign.onedic.utilities;

import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Albaloo on 4/11/2017.
 */

public class ExternalFileStorage {

    private File file;
    private static final String FILENAME = "jsondata.txt";

    public ExternalFileStorage(){
        File extDir = App.getAppContext().getExternalFilesDir(null);
        file = new File(extDir,FILENAME);
    }

    public String getPath(){
        File extDir = App.getAppContext().getExternalFilesDir(null);
        String path = extDir.getAbsolutePath();
        return path;
    }


    public boolean checkExternalStorage(){
        String state = Environment.getExternalStorageState();

        File pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File filePicture = new File(pictureFolder,"filename");

        if (state.equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
            App.toast("External storage is read-only");
        }else{
            App.toast("External storage is unavailable.");
        }

        return false;
    }


    public void createFile(String text) throws IOException {

        if (!checkExternalStorage()){
            return;
        }

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(text.getBytes());
        fos.close();

        App.toast("File written to disk");
    }


    public String readFile() throws IOException {

        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        StringBuffer buffer = new StringBuffer();

        while (bis.available() != 0){
            char c = (char)bis.read();
            buffer.append(c);
        }

        bis.close();
        fis.close();

        //App.toast(buffer.toString());
        return buffer.toString();
    }
}
