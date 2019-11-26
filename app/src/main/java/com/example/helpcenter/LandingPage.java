package com.example.helpcenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import maes.tech.intentanim.CustomIntent;

public class LandingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageView imageNewUser = findViewById(R.id.imageNewUser);
        ImageView imageExistingUser = findViewById(R.id.imageExistingUser);

        imageNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LandingPage.this, SignIn.class);
                startActivity(i);
                CustomIntent.customType(LandingPage.this, "left-to-right");
            }
        });

        imageExistingUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LandingPage.this, LogIn.class);
                startActivity(i);
                CustomIntent.customType(LandingPage.this, "left-to-right");
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
    }
}