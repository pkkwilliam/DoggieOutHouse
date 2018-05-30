package doggieouthouse.com.doggieouthouse.UI.UI;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import doggieouthouse.com.doggieouthouse.R;
import doggieouthouse.com.doggieouthouse.UI.functions.Dog;
import doggieouthouse.com.doggieouthouse.UI.functions.HttpGet;
import doggieouthouse.com.doggieouthouse.UI.functions.HttpGetJSON;
import doggieouthouse.com.doggieouthouse.UI.functions.UploadImage;
import doggieouthouse.com.doggieouthouse.UI.functions.WifiConnectionCheck;

public class MainActivity extends AppCompatActivity {
    Boolean Is_Login = false;
    Button login_or_capture_button;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get JSON DOG
        Dog.run();

        // implementation of the UI from Layout
        TextView usernameTextView = (TextView)findViewById(R.id.usernameTextView);
        TextView doggieIDTextView = (TextView)findViewById(R.id.doggieIDTextView);
        TextView idCheckTextView = (TextView)findViewById(R.id.idCheckTextView);
        login_or_capture_button = (Button) findViewById(R.id.capture_or_login);

        // implement of Buttons
        login_or_capture_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        // Check login
        if (check_login()) {
            login_or_capture_button.setText("Capture");
            login_or_capture_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dispatchTakePictureIntent();
                }
            });
        }else {
            login_or_capture_button.setText("Capture");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView mImageView = (ImageView) findViewById(R.id.mImageView);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
            new UploadImage(this).execute(imageBitmap);
            HttpGetJSON test = new HttpGetJSON(this, SuccessClassify.class);
            test.execute("http://imagerecognitionalphatesting.appspot.com/demo?id=http://karmaincorporated.com/imageRecognitionUpload/TESING.png");
        }
    }

    private void dispatchTakePictureIntent() {
        final int number_of_image_to_take  = 1;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, number_of_image_to_take);
        }
    }

    private boolean check_login(){
        SharedPreferences sp1 = this.getSharedPreferences("Login", MODE_PRIVATE);

        String username = sp1.getString("username", null);
        String password = sp1.getString("password", null);
        if (username == null)
            return false;
        else
            // do something connect to db and login user
            return true;
    }

    private void login(String username, String password){
        SharedPreferences sp=getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor Ed=sp.edit();
        Ed.putString("username",username );
        Ed.putString("password",password);
        Ed.commit();
    }
}
