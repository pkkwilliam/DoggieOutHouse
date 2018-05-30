package doggieouthouse.com.doggieouthouse.UI.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;

import doggieouthouse.com.doggieouthouse.R;
import doggieouthouse.com.doggieouthouse.UI.functions.Dog;

public class BreedInfo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breed_info);

        Dog resultDog = (Dog)getIntent().getSerializableExtra("dog");

        TextView name = (TextView)findViewById(R.id.breed_info_breed_name_textView);
        TextView size = (TextView)findViewById(R.id.size_textView);
        TextView weight = (TextView)findViewById(R.id.weight_textView);
        TextView height = (TextView)findViewById(R.id.height_textView);
        TextView life_span = (TextView)findViewById(R.id.life_span_textView);
        TextView origin = (TextView)findViewById(R.id.origin_textView);
        TextView group = (TextView)findViewById(R.id.group_textView);
        TextView history = (TextView)findViewById(R.id.history_textView);
        TextView role = (TextView)findViewById(R.id.role_textView);
        TextView temperament = (TextView)findViewById(R.id.temperament_textView);
        TextView excersice = (TextView)findViewById(R.id.exercise_textView);
        TextView grooming = (TextView)findViewById(R.id.grooming_textView);
        TextView health_issue = (TextView)findViewById(R.id.health_issue_textView);
        TextView description = (TextView)findViewById(R.id.description_textView);

        name.setText(resultDog.getName());
        size.setText(resultDog.getSize());
        weight.setText(resultDog.getWeight());
        height.setText(resultDog.getHeight());
        life_span.setText(resultDog.getLifeSpan());
        origin.setText(resultDog.getOrigin());
        group.setText(resultDog.getGroup());
        history.setText(resultDog.getHistory());
        role.setText(resultDog.getRole());
        temperament.setText(resultDog.getTemperament());
        excersice.setText(resultDog.getExercise());
        grooming.setText(resultDog.getGrooming());
        health_issue.setText(resultDog.getHealth_issue());
        description.setText(resultDog.getDescription());

        new DownloadImageTaskResult((ImageView) findViewById(R.id.dog_image))
                .execute(resultDog.getImage_url());
    }
}
class DownloadImageTaskResult extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTaskResult(ImageView bmImage) {
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