package com.example.johndoe.demoqr;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.RawRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.VideoView;

import java.lang.reflect.Field;
import java.util.Hashtable;

public class VideoRoot extends AppCompatActivity {

    private WebView gifHolder;
    private TextView receivedInfo;
    VideoView videoLibras;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_root);

        receivedInfo  = (TextView)findViewById(R.id.receivedInfo);
        //videoLibras = (VideoView)findViewById(R.id.videoLibras);
        gifHolder = (WebView)findViewById(R.id.gifHolder);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            String value = extras.getString("nomeVideo");
            receivedInfo.setText(value.substring(0,value.length()-4));

            gifHolder.loadUrl("file:///android_res/raw/" + value);
            gifHolder.setPadding(0, 0, 0, 0);
            gifHolder.getSettings().setLoadWithOverviewMode(true);
            gifHolder.getSettings().setUseWideViewPort(true);

           // Uri uri = Uri.parse(nome); //Declare your url here.

           // videoLibras.setVideoURI(uri);
           // videoLibras.requestFocus();
           // videoLibras.start();
        }
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
