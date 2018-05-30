package doggieouthouse.com.doggieouthouse.UI.functions;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by pkkwilliam on 4/27/18.
 */

public class Dog implements Serializable{
    int id;
    private String name;
    private String size;
    private String weight;
    private  String height;
    private  String life_span;
    private  String origin;
    private  String group;
    private String history;
    private String role;
    private String temperament;
    private  String exercise;
    private String grooming;
    private String health_issue;
    private String description;
    private String image_url;
    public Dog(int id, String name, String size, String weight, String height, String life_span, String origin, String group, String history, String role, String temperament, String exercise, String grooming, String health_issue, String description, String image_url){
        this.id = id;
        this.name = name;
        this.size = size;
        this.weight = weight;
        this.height = height;
        this.life_span = life_span;
        this.origin = origin;
        this.group = group;
        this.history = history;
        this.role = role;
        this.temperament = temperament;
        this.exercise = exercise;
        this.grooming = grooming;
        this.health_issue = health_issue;
        this.description = description;
        this.image_url = image_url;
    }

    static Dog[] dogs;
    public Dog(String dogJson){
        Log.d("Create Dog Object","Got JSON");
        dogs = new Gson().fromJson(dogJson, Dog[].class);
    }
    public static Dog[] getDogs(){
        return dogs;
    }
    public static void run(){
        new getDog().execute();
    }
    public String getName(){
        return this.name;
    }
    public String getSize(){
        return this.size;
    }
    public String getWeight(){
        return this.weight;
    }
    public String getHeight(){
        return this.height;
    }
    public String getLifeSpan(){
        return this.life_span;
    }
    public String getOrigin(){
        return this.origin;
    }
    public String getGroup(){
        return this.group;
    }
    public String getHistory(){
        return this.history;
    }
    public String getRole(){
        return this.role;
    }
    public String getTemperament(){
        return this.temperament;
    }
    public String getExercise(){
        return this.exercise;
    }
    public String getGrooming(){
        return this.grooming;
    }
    public String getHealth_issue(){
        return this.health_issue;
    }
    public String getDescription(){
        return this.description;
    }
    public String getImage_url(){
        return this.image_url;
    }
}
class getDog extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... params){
        List<String> list = new ArrayList();
        String content = null;
        try {
            content = getHtmlContent("http://karmaincorporated.com/imageRecognitionUpload/dog_details.json");
            new Dog(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String getHtmlContent(String url) throws IOException{
        String address = url;
        // Build and set timeout values for the request.
        URLConnection connection = (new URL(address)).openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.connect();

        // Read and store the result line by line then return the entire string.
        InputStream in = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder html = new StringBuilder();
        for (String line; (line = reader.readLine()) != null; ) {
            html.append(line);
        }
        in.close();
        return html.toString();
    }
}