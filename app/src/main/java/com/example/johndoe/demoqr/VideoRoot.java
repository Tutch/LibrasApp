package com.example.johndoe.demoqr;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.annotation.RawRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.VideoView;

import java.lang.reflect.Field;
import java.util.Hashtable;

public class VideoRoot extends AppCompatActivity {

    private TextView receivedInfo;
    private TextView dicionario;
    VideoView videoLibras;
    private ProgressDialog progressDialog;
    Hashtable<String, String> dicionarioVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_root);

        populateTable();

        receivedInfo  = (TextView)findViewById(R.id.receivedInfo);
        dicionario = (TextView)findViewById(R.id.dicionario);
        videoLibras = (VideoView)findViewById(R.id.videoLibras);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            String value = extras.getString("qrLido");
            receivedInfo.setText(value);
            dicionario.setText(dicionarioVideos.get(value));

            String nome = "android.resource://" + getPackageName() + "/raw/" + dicionarioVideos.get(value) ;


            Uri uri = Uri.parse(nome); //Declare your url here.

            videoLibras.setVideoURI(uri);
            videoLibras.requestFocus();
            videoLibras.start();
        }
    }

    private void populateTable(){
        dicionarioVideos = new Hashtable<String,String>();
        dicionarioVideos.put("oi","doggo");
    }

    // http://stackoverflow.com/questions/4427608/android-getting-resource-id-from-string
    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
