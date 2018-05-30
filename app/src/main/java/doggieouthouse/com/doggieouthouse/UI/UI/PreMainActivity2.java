package doggieouthouse.com.doggieouthouse.UI.UI;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import doggieouthouse.com.doggieouthouse.R;

public class PreMainActivity2 extends AppCompatActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_main2);
        videoView = (VideoView)findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.doh_video);
        videoView.setVideoURI(uri);
        //videoView.setVideoPath();
        videoView.start();

        Button nextButton = (Button)findViewById(R.id.nextButton_pre_main2);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PreMainActivity2.this,MainActivity.class);
                startActivity(i);
            }
        });
        Button backButton = (Button)findViewById(R.id.backButton_pre_main2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PreMainActivity2.this,PreMainActivity.class);
                startActivity(i);
            }
        });

    }
    @Override
    protected  void onResume(){
        super.onResume();
        videoView.start();
    }
}
