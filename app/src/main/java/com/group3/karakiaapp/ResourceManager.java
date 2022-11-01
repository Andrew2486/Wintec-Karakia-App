package com.group3.karakiaapp;

import android.app.Application;
import android.app.LoaderManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.*;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import org.json.JSONObject;
import org.xml.sax.ContentHandler;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderAdapter;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class ResourceManager {
    static ResourceManager _instance;
    public static ResourceManager Instance() { return _instance; }
    public Resources resources;
    public Context context;

    public HashMap<String,Object> loaded = new HashMap<>();
    public HashMap<Integer,Karakia> karakias = new HashMap<>();
    public HashMap<String,HelpVideo> helpVideos = new HashMap<>();

    public ResourceManager(AppCompatActivity app) {
        _instance = this;
        resources = app.getResources();
        context = app.getApplicationContext();
        for (Xml item : Xml.FromParser(resources.getXml(R.xml.karakias)).Children.get(0).Children) {
            if (item.Name.equals("karakia")) {
                Karakia k = new Karakia();
                k.name = item.Attributes.get("name");
                k.id = Integer.parseInt(item.Attributes.get("id"));
                k.video = GetRawResourceId(item.Attributes.get("video"));
                if (k.video == -1)
                    Log.d("karakiaInitialization", "Could not find resource called " + item.Attributes.get("video") + " for karakia video " + k.name);
                k.audio = GetRawResourceId(item.Attributes.get("audio"));
                if (k.audio == -1)
                    Log.d("karakiaInitialization", "Could not find resource called " + item.Attributes.get("audio") + " for karakia audio " + k.name);
                k.image = GetRawResourceId(item.Attributes.get("image"));
                if (k.image == -1)
                    Log.d("karakiaInitialization", "Could not find resource called " + item.Attributes.get("image") + " for karakia image " + k.name);

                for (Xml child : item.Children) {
                    if (child.Name.equals("words"))
                    {
                        if (child.Attributes.get("lang").equals("english"))
                            k.wordsEnglish = child.Children.get(0).Text;
                        if (child.Attributes.get("lang").equals("maori"))
                            k.wordsMaori = child.Children.get(0).Text;
                    }
                    else if (child.Name.equals("origins"))
                        k.origins = child.Children.get(0).Text;
                }
                karakias.put(k.id, k);
            }
        }
        for (Xml item : Xml.FromParser(resources.getXml(R.xml.help_video)).Children.get(0).Children) {
            if (item.Name.equals("video")) {
                HelpVideo v = new HelpVideo();
                v.name = item.Attributes.get("name");
                v.id = item.Attributes.get("id");
                v.video = GetRawResourceId(item.Attributes.get("video"));
                if (v.video == -1)
                    Log.d("helpInitialization", "Could not find resource called " + item.Attributes.get("video") + " for karakia video " + v.name);
                helpVideos.put(v.id, v);
            }
        }
        ReadSettings();
    }

    static int GetRawResourceId(String name) {
        try {
            return (int) R.raw.class.getField(name).get(null);
        } catch (Exception e)
        {
            return -1;
        }
    }

    public void ReadSettings() {
        JSONObject values = ReadFile("help.settings");
        if (values != null)
            for (HelpVideo help: helpVideos.values())
                TryGetValue(values,help.name,(Boolean x) -> help.autoplay = x);
        values = ReadFile("general.settings");
        if (values != null)
            TryGetValue(values,"ToS",(Boolean x) -> MainActivity.Settings.agreedToToS = x);
    }

    public void WriteSettings() {
        JSONObject values = new JSONObject();
        for (HelpVideo help: helpVideos.values())
            TryPutValue(values,help.name, help.autoplay);
        WriteFile("help.settings",values);
        values = new JSONObject();
        TryPutValue(values,"ToS",MainActivity.Settings.agreedToToS);
        WriteFile("general.settings",values);
    }


    static <T> boolean TryGetValue(JSONObject json, String name, Action<T> OnSuccess) {
        if (json.has(name)) {
            T result;
            try {
                result = (T) json.opt(name);
            } catch (Exception e) {
                Log.e("TryGetValue",name + " - " + e);
                return false;
            }
            OnSuccess.invoke(result);
            return true;
        }
        return false;
    }
    static boolean TryPutValue(JSONObject json, String name, Object value) {
        try {
            json.putOpt(name,value);
            return true;
        } catch (Exception e) {
            Log.e("TryPutValue",name + " - " + e);
            return false;
        }
    }

    JSONObject ReadFile(String filename) {
        FileInputStream fileStream = null;
        try {
            fileStream = context.openFileInput(filename);
            List<Byte> bytes = new ArrayList<>();
            int read = fileStream.read();
            while (read != -1) {
                bytes.add((byte)read);
                read = fileStream.read();
            }
            fileStream.close();
            byte[] bytes2 = new byte[bytes.size()];
            for (int i = 0; i < bytes.size();i++)
                bytes2[i] = bytes.get(i);
            return new JSONObject(new String(bytes2));
        } catch (Exception e) {
            Log.e("ReadFile",filename + " - " + e);
            if (fileStream != null)
                try { fileStream.close(); } catch (Exception e2) {}
            return null;
        }
    }
    void WriteFile(String filename, JSONObject json) {
        FileOutputStream fileStream = null;
        try {
            fileStream = context.openFileOutput(filename,Context.MODE_PRIVATE);
            String contents = json.toString(4);
            fileStream.write(contents.getBytes());
            fileStream.close();
        } catch (Exception e) {
            Log.e("WriteFile",filename + " - " + e);
            if (fileStream != null)
                try { fileStream.close(); } catch (Exception e2) {}
        }
    }
}


