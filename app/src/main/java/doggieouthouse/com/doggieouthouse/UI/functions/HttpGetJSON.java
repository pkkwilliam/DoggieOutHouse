package doggieouthouse.com.doggieouthouse.UI.functions;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import doggieouthouse.com.doggieouthouse.R;
import doggieouthouse.com.doggieouthouse.UI.UI.MainActivity;
import doggieouthouse.com.doggieouthouse.UI.UI.SuccessClassify;

/**
 * Created by pkkwilliam on 3/30/18.
 * This is for MainActivity
 */

public class HttpGetJSON extends AsyncTask<String, Boolean, List<String>>{
    private Activity currentActivity;
    private Class nextActivity;
    TextView textView;
    Button button;

    public HttpGetJSON(Activity currentActivity, Class nextActivity){
        this.currentActivity = currentActivity;
        this.nextActivity = nextActivity;
        textView = (TextView)currentActivity.findViewById(R.id.idCheckTextView);
    }
    @Override
    protected List<String> doInBackground(String... params){
        List<String> list = new ArrayList();
        String recognizeResult = null;
        try {
            recognizeResult = getHtmlContent(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean is_visible = false;
        for(int i = 0; i < 20; i++){
            is_visible = (is_visible) ? false : true;
            publishProgress(is_visible);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(recognizeResult);
        return list;
    }
    @Override
    protected void onPreExecute(){
        button = (Button)currentActivity.findViewById(R.id.capture_or_login);
        button.setText("Processing...");
    }
    @Override
    protected void onProgressUpdate(Boolean... values){
        super.onProgressUpdate(values);
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f , 1.0f ) ;
        Boolean is_visible = values[0];
        String rand_id = "ID:Check";
        if(is_visible){
            textView.setVisibility(View.VISIBLE);
            textView.startAnimation(fadeIn);
            fadeIn.setDuration(200);
            textView.setText(rand_id);
            textView.setBackgroundColor(Color.parseColor("#FFF000"));
        }else {
            AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
            textView.startAnimation(fadeOut);
            fadeOut.setDuration(200);
        }

    }
    @Override
    protected void onPostExecute(List<String> list){
        Intent intent = new Intent(currentActivity, nextActivity);
        intent.putExtra("recognizeResult",list.get(0));
        // intent.putExtra("preDefineDict",list.get(1));
        button.setText("Capture");
        currentActivity.startActivity(intent);
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
