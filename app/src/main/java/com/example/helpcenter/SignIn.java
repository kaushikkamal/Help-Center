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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import maes.tech.intentanim.CustomIntent;

public class SignIn extends AppCompatActivity {

    private ImageView imageBackArrowSignIn, imageBtnNextSignIn;
    private TextInputLayout usernameSignIn, emailSignIn, passwordSignIn, rePasswordSignIn ;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setUi();

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        imageBackArrowSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignIn.this,LandingPage.class);
                startActivity(i);
                CustomIntent.customType(SignIn.this,"right-to-left");
            }
        });

        imageBtnNextSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateUserName() && validateEmail() && validatePassword() && validateRePassword()){

                    if (passwordSignIn.getEditText().getText().toString().trim().equals(rePasswordSignIn.getEditText().getText().toString().trim())) {

                        String userEmail = emailSignIn.getEditText().getText().toString().trim();
                        String userPassword = passwordSignIn.getEditText().getText().toString().trim();

                        progressDialog.setMessage("Please wait until the registration completed!");
                        progressDialog.show();

                        firebaseAuth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Intent i = new Intent(SignIn.this,Welcome.class);
//                                    Intent i = new Intent(SignIn.this,PhoneNumber.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                    CustomIntent.customType(SignIn.this,"left-to-right");
                                }
                                else {
                                    progressDialog.dismiss();
                                    Snackbar.make(findViewById(R.id.contextView), "Registration failed", Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else {
                        Snackbar.make(findViewById(R.id.contextView), "Password doesnot match", Snackbar.LENGTH_SHORT).show();
                        passwordSignIn.getEditText().setText(null);
                        rePasswordSignIn.getEditText().setText(null);
                    }
                }
                else {
                    return;
                }
            }
        });
    }

    private void setUi(){
        imageBackArrowSignIn = findViewById(R.id.imageBackArrowSignIn);
        usernameSignIn = findViewById(R.id.usernameSignIn);
        emailSignIn = findViewById(R.id.emailSignIn);
        passwordSignIn = findViewById(R.id.passwordSignIn);
        rePasswordSignIn = findViewById(R.id.rePasswordSignIn);
        imageBtnNextSignIn = findViewById(R.id.imageBtnNextSignIn);
    }

    private boolean validateUserName(){
        String userNameInput = usernameSignIn.getEditText().getText().toString().trim();
        if (userNameInput.isEmpty()){
            usernameSignIn.setError("Field can't be empty");
            return false;
        }
        else if (userNameInput.length() > 15){
            usernameSignIn.setError("Username too long");
            return false;
        }
        else {
            usernameSignIn.setError(null);
            return true;
        }
    }

    private boolean validateEmail(){
        String emailInput = emailSignIn.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()){
            emailSignIn.setError("Field can't be empty");
            return false;
        }
        else {
            emailSignIn.setError(null);
            return true;
        }
    }
    private boolean validatePassword(){
        String passwordInput = passwordSignIn.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()){
            passwordSignIn.setError("Field can't be empty");
            return false;
        }
        else if (passwordInput.length() <= 6){
            passwordSignIn.setError("Password should be atleast of 7 characters");
            return false;
        }
        else {
            passwordSignIn.setError(null);
            return true;
        }
    }
    private boolean validateRePassword(){
        String rePasswordInput = rePasswordSignIn.getEditText().getText().toString().trim();
        if (rePasswordInput.isEmpty()){
            rePasswordSignIn.setError("Field can't be empty");
            return false;
        }
        else if (rePasswordInput.length() <= 6){
            rePasswordSignIn.setError("Password should be atleast of 7 characters");
            return false;
        }
        else {
            rePasswordSignIn.setError(null);
            return true;
        }
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(SignIn.this,"right-to-left");
    }
}
