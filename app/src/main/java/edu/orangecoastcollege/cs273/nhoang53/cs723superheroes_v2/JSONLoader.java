package edu.orangecoastcollege.cs273.nhoang53.cs723superheroes_v2;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Nguyen Hoang C02288487
 * Project 2: CS273 Superheroes
 */

public class JSONLoader {

    public static ArrayList<Superheroes> loadJSONFromAsset(Context context) throws IOException{
        ArrayList<Superheroes> allSuperheroes = new ArrayList<Superheroes>();

        // read data from json file
        InputStream is = context.getAssets().open("cs273superheroes.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        String json = new String(buffer, "UTF-8"); // put data in to string

        try{
            // JSONObject large have big JSONArray
            // JSONArray have some small JSONObject small
            JSONObject jsonRootObject = new JSONObject(json);
            JSONArray allSuperheroesJSON =
                    jsonRootObject.getJSONArray("CS273Superheroes"); // name of group on json file
            int numberOfSuperheroes = allSuperheroesJSON.length();

            for(int i = 0; i < numberOfSuperheroes; i++){
                JSONObject superheroesJSON = allSuperheroesJSON.getJSONObject(i);

                Superheroes hero = new Superheroes();

                hero.setUserName(superheroesJSON.getString("Username"));
                hero.setName(superheroesJSON.getString("Name"));
                hero.setSuperpower(superheroesJSON.getString("Superpower"));
                hero.setOneThing(superheroesJSON.getString("OneThing"));
                hero.setImageName(superheroesJSON.getString("ImageName"));

                allSuperheroes.add(hero);
            }

        }catch (JSONException e){
            Log.e("Superheroes", e.getMessage());
        }

        return allSuperheroes;
    }
}

