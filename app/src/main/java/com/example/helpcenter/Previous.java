package com.example.helpcenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import maes.tech.intentanim.CustomIntent;

public class Previous extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous);

        mRecyclerView = findViewById(R.id.recyclerView);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        new FirebaseDatabaseHelper().readData(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<UserInformation> userInformations, List<String> keys) {
                progressDialog.dismiss();
                new RecyclerView_Config().setConfig(mRecyclerView, Previous.this, userInformations, keys);
            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

    }
    @Override
    public void finish() {
        super.finish();
        startActivity(new Intent(this,Welcome.class));
        CustomIntent.customType(Previous.this,"right-to-left");
    }
}
