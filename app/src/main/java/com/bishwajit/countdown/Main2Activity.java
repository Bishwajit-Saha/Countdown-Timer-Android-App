package com.bishwajit.countdown;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadImages();
    }

    public void loadImages()
    {
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        ImageButton fb = (ImageButton)findViewById(R.id.fb);
        ImageButton twitter = (ImageButton)findViewById(R.id.twitter);

        try {
            InputStream in = getAssets().open("Bishwajit.jpg");
            Drawable drawable = Drawable.createFromStream(in, null);
            imageView.setImageDrawable(drawable);

             /*in = getAssets().open("fb.jpg");
             drawable = Drawable.createFromStream(in, null);
             fb.setImageDrawable(drawable);

            in = getAssets().open("twitter.jpg");
            drawable = Drawable.createFromStream(in, null);
            twitter.setImageDrawable(drawable);*/

            fb.setOnClickListener(
                    new ImageButton.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent browseFb = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/bishwo.saha"));
                            startActivity(browseFb);
                        }
                    }
            );

            twitter.setOnClickListener(
                    new ImageButton.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) {
                            Intent browseTwitter = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/Bishwajit__Saha"));
                            startActivity(browseTwitter);
                        }
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
