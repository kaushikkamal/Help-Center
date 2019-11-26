package com.example.helpcenter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import maes.tech.intentanim.CustomIntent;

public class LogIn extends AppCompatActivity {

    private ImageView imageBackArrow, imageBtnNextLogIn;
    private TextInputLayout text_input_email, text_input_password ;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setUi();

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        imageBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogIn.this,LandingPage.class);
                startActivity(i);
                CustomIntent.customType(LogIn.this,"right-to-left");
            }
        });

        imageBtnNextLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEmail() && validatePassword()){

                    String emailLogIn = text_input_email.getEditText().getText().toString().trim();
                    String passwordLogIn = text_input_password.getEditText().getText().toString().trim();

                    progressDialog.setMessage("Wait for the confirmation!");
                    progressDialog.show();

                    firebaseAuth.signInWithEmailAndPassword(emailLogIn,passwordLogIn).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                text_input_email.getEditText().setText(null);
                                text_input_password.getEditText().setText(null);
                                Intent i = new Intent(LogIn.this,Welcome.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                CustomIntent.customType(LogIn.this,"left-to-right");
                            }
                            else {
                                progressDialog.dismiss();
                                Snackbar.make(findViewById(R.id.contextView), "Wrong Information", Snackbar.LENGTH_SHORT).show();
                                text_input_email.getEditText().setText(null);
                                text_input_password.getEditText().setText(null);
                            }
                        }
                    });
                }
                else {
                    return;
                }
            }
        });
    }
    private void setUi(){
        imageBackArrow = findViewById(R.id.imageBackArrow);
        text_input_email = findViewById(R.id.text_input_email);
        text_input_password = findViewById(R.id.text_input_password);
        imageBtnNextLogIn = findViewById(R.id.imageBtnNextLogIn);
    }
    private boolean validateEmail(){
        String emailInput = text_input_email.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()){
            text_input_email.setError("Field can't be empty");
            return false;
        }
        else {
            text_input_email.setError(null);
            return true;
        }
    }
    private boolean validatePassword(){
        String passwordInput = text_input_password.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()){
            text_input_password.setError("Field can't be empty");
            return false;
        }
        else {
            text_input_password.setError(null);
            return true;
        }
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(LogIn.this,"right-to-left");
    }
}
