package doggieouthouse.com.doggieouthouse.UI.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.AlphaAnimation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import doggieouthouse.com.doggieouthouse.R;

public class PreMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_main);

        // SplashScreen
        ViewStub vb = (ViewStub)findViewById(R.id.viewStub);
        View inflated = vb.inflate();
        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 1.0f);
        fadeOut.setDuration(4000);
        inflated.setAnimation(fadeOut);
        inflated.setVisibility(View.INVISIBLE);

        Button nextButton = (Button)findViewById(R.id.nextButton_pre_main);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PreMainActivity.this,PreMainActivity2.class);
                startActivity(i);
            }
        });

    }
}
