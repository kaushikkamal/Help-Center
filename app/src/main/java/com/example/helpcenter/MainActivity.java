package com.example.helpcenter;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth = FirebaseAuth.getInstance();

        final ImageView image1 = findViewById(R.id.imageView);
        final ImageView image2 = findViewById(R.id.imageView7);
        final ImageView image3 = findViewById(R.id.image);
        final ImageView no_internet = findViewById(R.id.imageView11);
        final ImageView no_internet_text = findViewById(R.id.imageView12);

        no_internet.setVisibility(View.INVISIBLE);
        no_internet_text.setVisibility(View.INVISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!isConnected()){
                    image1.setVisibility(View.INVISIBLE);
                    image2.setVisibility(View.INVISIBLE);
                    image3.setVisibility(View.INVISIBLE);
                    no_internet.setVisibility(View.VISIBLE);
                    no_internet_text.setVisibility(View.VISIBLE);
                }
                else {
                    if (mAuth.getCurrentUser() != null){
                        Intent i = new Intent(MainActivity.this,Welcome.class);
                        startActivity(i);
                    }
                    else {
                        Intent i = new Intent(MainActivity.this,LandingPage.class);
                        startActivity(i);
                    }
                }
            }
        },2000);
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo != null  && netInfo.isConnected();
    }
}
