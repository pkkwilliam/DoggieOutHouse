package doggieouthouse.com.doggieouthouse.UI.functions;

import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import doggieouthouse.com.doggieouthouse.UI.UI.SuccessClassify;

/**
 * Created by pkkwilliam on 4/11/18.
 */

public class HttpGet extends AsyncTask<String,Void,String>{
    @Override
    protected String doInBackground(String... params) {
        try {
            String result = retrieveInformationFromURL(params[0]);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // Not method from abstract class

    private String retrieveInformationFromURL(String address) throws Exception{
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

