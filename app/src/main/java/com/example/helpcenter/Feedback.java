package com.example.helpcenter;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hsalf.smilerating.SmileRating;

import maes.tech.intentanim.CustomIntent;

public class Feedback extends AppCompatActivity {

    private TextInputLayout text_feedback ;
    private ImageView btnSubmitFeedback;
    private FirebaseAuth firebaseAuth;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        text_feedback = findViewById(R.id.text_feedback);
        btnSubmitFeedback = findViewById(R.id.btnSubmitFeedback);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userID = user.getUid();

        SmileRating smileRating = (SmileRating) findViewById(R.id.smile_rating);

        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley, boolean reselected) {
                switch (smiley) {
                    case SmileRating.BAD:
                        Toast.makeText(Feedback.this,"BAD",Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.GOOD:
                        Toast.makeText(Feedback.this,"GOOD",Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.GREAT:
                        Toast.makeText(Feedback.this,"GREAT",Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.OKAY:
                        Toast.makeText(Feedback.this,"OKAY",Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.TERRIBLE:
                        Toast.makeText(Feedback.this,"TERRIBLE",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
            @Override
            public void onRatingSelected(int level, boolean reselected) {

            }
        });

        btnSubmitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFeedback()){
                    final String feedback = text_feedback.getEditText().getText().toString();

                    DatabaseReference dr = FirebaseDatabase.getInstance().getReferenceFromUrl("https://help-center-e8a5f.firebaseio.com/").child("feedback").child(userID);

                    UserInformation infoFeedback = new UserInformation();
                    infoFeedback.setFeedback(feedback);
                    dr.setValue(infoFeedback);

                    Toast.makeText(Feedback.this, "Thanks for the feedback", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Feedback.this,Welcome.class);
                    startActivity(i);
                    CustomIntent.customType(Feedback.this,"right-to-left");
                }
            }
        });
    }

    private boolean validateFeedback(){
        String feedback = text_feedback.getEditText().getText().toString().trim();
        if (feedback.isEmpty()){
            text_feedback.setError("Field can't be empty");
            return false;
        }
        else {
            text_feedback.setError(null);
            return true;
        }
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(Feedback.this,"right-to-left");
    }
}
