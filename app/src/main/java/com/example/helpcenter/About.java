package com.example.helpcenter;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import maes.tech.intentanim.CustomIntent;

public class About extends AppCompatActivity {

    private ImageView backAbout, sendEmail, privacy, fb, insta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setUi();

        backAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(About.this,Welcome.class);
                startActivity(i);
                CustomIntent.customType(About.this,"right-to-left");
            }
        });


        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("mailto:"));
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"kaushikborah777@gmail.com"});
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                }
                catch (android.content.ActivityNotFoundException ex) {
                }
            }
        });

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://kaushikborah777.wixsite.com/mysite"));
                    startActivity(intent);
                }
                catch (android.content.ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://kaushikborah777.wixsite.com/mysite")));
                }
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.facebook.com/profile.php?id=100008194395929"));
                    startActivity(intent);
                }
                catch (android.content.ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/profile.php?id=100008194395929")));
                }
            }
        });

        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://instagram.com/_u/kaushik_kamal_borah"));
                    intent.setPackage("com.instagram.android");
                    startActivity(intent);
                }
                catch (android.content.ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.instagram.com/kaushik_kamal_borah")));
                }
            }
        });
    }

    private void setUi(){
        backAbout = findViewById(R.id.imageView);
        sendEmail = findViewById(R.id.sendEmail);
        privacy = findViewById(R.id.privacy);
        fb = findViewById(R.id.fb);
        insta = findViewById(R.id.insta);

    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(About.this,"right-to-left");
    }
}
