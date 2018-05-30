package doggieouthouse.com.doggieouthouse.UI.functions;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import doggieouthouse.com.doggieouthouse.R;

/**
 * Created by pkkwilliam on 3/28/18.
 */

public class UploadImage extends AsyncTask<Bitmap, Void, Void> {

    final static String FTP_SERVER = "ftp.karmaincorporated.com";
    final static String FTP_USERNAME = "imageRecognitionUpload@karmaincorporated.com";
    final static String FTP_PASSWORD = "!x{tfnV4iH,$";
    Context context;

    public UploadImage(Context context){
        this.context = context;
    }

    @Override
    protected Void doInBackground(Bitmap... params) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(FTP_SERVER);
            ftpClient.setSoTimeout(10000);
            ftpClient.enterLocalPassiveMode();
            if (ftpClient.login(FTP_USERNAME, FTP_PASSWORD)) {
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    params[0].compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                    byte[] bitmapdata = bos.toByteArray();
                    ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
                    ftpClient.storeFile("TESING.png", bs);
                    bs.close();
                    Log.i("Upload", "sent");
                } catch (Exception e) {
                    Log.i("Upload", "error uploading");
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void result){

    }
}
