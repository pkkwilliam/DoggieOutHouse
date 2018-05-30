package doggieouthouse.com.doggieouthouse.UI.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.Serializable;

import doggieouthouse.com.doggieouthouse.R;
import doggieouthouse.com.doggieouthouse.UI.functions.Dog;
import doggieouthouse.com.doggieouthouse.UI.functions.ResultClass;

public class SuccessClassify extends AppCompatActivity {
    Dog resultDog = null;
    static Dog[] dogs = Dog.getDogs();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_classify);
        Intent intent = getIntent();
        String raw_json = intent.getExtras().getString("recognizeResult");
        // String raw_dog_json = intent.getExtras().getString("preDefineDict");

        Gson gson = new Gson();


        TextView breed = (TextView)findViewById(R.id.breed);
        TextView certainty = (TextView)findViewById(R.id.certainty);
        Button breed_info_button = (Button)findViewById(R.id.breedInfoButton);

        JsonParser parser = new JsonParser();
        ResultClass[] rc = new ResultClass[3];

        float highest = Integer.MIN_VALUE;
        float lowest =  Integer.MAX_VALUE;
        float sum = 0;

        for(int i = 0; i < 3; i++){
            JsonObject element = (JsonObject)parser.parse(raw_json)
                    .getAsJsonObject().getAsJsonArray("responses").get(0)
                    .getAsJsonObject().get("webDetection")
                    .getAsJsonObject().getAsJsonArray("webEntities").get(i);
            rc[i] = gson.fromJson(element, ResultClass.class);
            float currentScore = rc[i].getScore();
            sum += currentScore;
            highest = (currentScore > highest) ? currentScore : highest;
            lowest = (currentScore < lowest) ? currentScore : lowest;
        }
        Log.d("TESTING","RRUNUSDF");
        for(int i = 0; i < rc.length; i++){
            for(int j = 0; j < dogs.length; j++){
                if(rc[i].getDescription().equals(dogs[j].getName())){
                    resultDog = dogs[j];
                    break;
                }
            }
        }

        if(resultDog != null){
            new DownloadImageTask((ImageView) findViewById(R.id.success_classify_image))
                    .execute(resultDog.getImage_url());

            breed.setText(resultDog.getName());
            float score = 100 - (highest - lowest);
            String result = String.format("%.2f",score)+"%";
            certainty.setText(result);
            breed_info_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SuccessClassify.this, BreedInfo.class);
                    intent.putExtra("dog",resultDog);
                    startActivity(intent);
                }
            });
        }else {
            breed.setText("Not able to recoginze...");
            certainty.setText("0%");
            breed_info_button.setText("No Result");
        }
    }
}
class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}