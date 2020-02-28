package com.wawan.dompetku.util;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


// ini adalah class untuk mendeklarasikan
// fungsi yg nantinya akna digunakan berkali kali
// oleh program
// dalam kasus ini adalah penyimpanan cache
public class SerializableSave {

    // deklarasi variabel
    private Context context;
    private String filename;


    // konstruktor class
    public SerializableSave(Context context, String filename) {
        this.context = context;
        this.filename = filename;
    }

    // fungsi turunan
    // untuk menyimpan object
    // bertipe seriazable
    public boolean save(Serializable s){
        boolean save = false;
        try {

            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(s);
            os.close();
            fos.close();

            save = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return save;
    }

    // fungsi turunan
    // untuk meload object
    // bertipe seriazable
    public Serializable load(){
        Serializable data = null;

        try {

            FileInputStream fis = context.openFileInput(filename);
            ObjectInputStream file = new ObjectInputStream(fis);
            data = (Serializable) file.readObject();
            file.close();
            fis.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return data;

    }

    // fungsi turunan
    // untuk menghapus object
    // bertipe seriazable
    public boolean delete(){
        File f = new File(context.getFilesDir(), filename);
        return f.delete();
    }
}
