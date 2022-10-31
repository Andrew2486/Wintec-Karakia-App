package com.group3.karakiaapp;

import android.app.Application;
import android.app.LoaderManager;
import android.content.pm.ApplicationInfo;
import android.content.res.*;
import android.provider.MediaStore;
import android.util.Log;

import java.util.*;
import java.util.logging.Logger;

import androidx.appcompat.app.AppCompatActivity;

public class ResourceManager {
    static ResourceManager _instance;
    public static ResourceManager Instance() { return _instance; }
    public Resources resources;

    public HashMap<String,Object> loaded = new HashMap<>();
    public HashMap<Integer,Karakia> karakias = new HashMap<>();

    public ResourceManager(AppCompatActivity app) {
        _instance = this;
        resources = app.getResources();
        Xml data = Xml.FromParser(resources.getXml(R.xml.karakias));
        for (Xml item : data.Children.get(0).Children) {
            Log.d("e","name: " + item.Name);
            if (item.Name.equals("karakia")) {
                Log.d("e","start");
                Karakia k = new Karakia();
                k.name = item.Attributes.get("name");
                k.id = Integer.parseInt(item.Attributes.get("id"));
                try {
                    k.video = (int) R.raw.class.getField(item.Attributes.get("video")).get(null);
                } catch (Exception e) {
                    Log.d("karakiaInitialization", "Could not find video called " + item.Attributes.get("video") + " for karakia " + k.name);
                }
                for (Xml child : item.Children) {
                    if (child.Name.equals("words"))
                        k.words = child.Children.get(0).Text;
                    else if (child.Name.equals("origins"))
                        k.origins = child.Children.get(0).Text;
                }
                Log.d("e", "id: " + k.id + " - name: " + k.name);
                karakias.put(k.id, k);
            }
        }
    }
}

