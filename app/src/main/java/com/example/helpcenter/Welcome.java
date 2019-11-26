package com.example.helpcenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.marcoscg.dialogsheet.DialogSheet;
import maes.tech.intentanim.CustomIntent;

public class Welcome extends AppCompatActivity {

    private ImageView settingsIcon,createAppointment,previousAppointment,sendFeedback,shareApp,signOut;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        setUi();

        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Welcome.this,About.class);
                startActivity(i);
                CustomIntent.customType(Welcome.this,"left-to-right");
            }
        });

        createAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Welcome.this,Form.class);
                startActivity(i);
                CustomIntent.customType(Welcome.this,"left-to-right");
            }
        });

        previousAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Welcome.this,Previous.class);
                startActivity(i);
                CustomIntent.customType(Welcome.this,"left-to-right");
            }
        });

        sendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Welcome.this,Feedback.class);
                startActivity(i);
                CustomIntent.customType(Welcome.this,"left-to-right");
            }
        });

        shareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String link = "This is the link for the app HELP CENTER " + " REPLACE THIS WITH YOUR LINK ";
                myIntent.putExtra(Intent.EXTRA_TEXT,link);
                startActivity(Intent.createChooser(myIntent, "Share link using"));
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogSheet signOut = new DialogSheet(Welcome.this)
                        .setTitle("Confirm!!!")
                        .setMessage("Do you want to SIGN OUT?")
                        .setColoredNavigationBar(true)
                        .setPositiveButton("Yes", new DialogSheet.OnPositiveClickListener() {
                            @Override
                            public void onClick(View view) {
                                FirebaseAuth.getInstance().signOut();
                                finish();
                                startActivity(new Intent(Welcome.this, LandingPage.class));
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null);
                signOut.show();
            }
        });
    }

    private void setUi(){
        settingsIcon = findViewById(R.id.settingsIcon);
        createAppointment = findViewById(R.id.createAppointment);
        previousAppointment = findViewById(R.id.previousAppointment);
        sendFeedback = findViewById(R.id.sendFeedback);
        shareApp = findViewById(R.id.shareApp);
        signOut = findViewById(R.id.signOut);
    }

    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }
        else {
            backToast = Toast.makeText(this,"Press back again to exit",Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}
