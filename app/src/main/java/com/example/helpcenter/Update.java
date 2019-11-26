package com.example.helpcenter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.marcoscg.dialogsheet.DialogSheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Update extends AppCompatActivity {

    public String gender,userID,patient_name,patient_phone_number,docNameDelete;
    private TextInputLayout update_patient_name,update_patient_phone_number;
    private TextView tvAge,deleteText;
    public int age;
    private ImageView imageUpdate, imageBack, btnAge, deleteImage;
    private RadioGroup radioGenderGroup;
    private RadioButton radioGenderButton;
    private FirebaseAuth firebaseAuth;

    public static String SHARED_PREFS = "sharedPrefs";
    public static String TIME = "time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        long timePass = sharedPreferences.getLong(TIME,120000);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userID = user.getUid();

        Intent i = getIntent();
        final String key = i.getStringExtra("key");
        docNameDelete = i.getStringExtra("sendDocDate");

        final DatabaseReference dr = FirebaseDatabase.getInstance().getReferenceFromUrl("https://help-center-e8a5f.firebaseio.com/").child("appointments").child(userID).child(key);
        final DatabaseReference deleteAppoint = FirebaseDatabase.getInstance().getReferenceFromUrl("https://help-center-e8a5f.firebaseio.com/").child("appointments").child(userID);
        final DatabaseReference deleteDoctor = FirebaseDatabase.getInstance().getReferenceFromUrl("https://help-center-e8a5f.firebaseio.com/");

        imageBack = findViewById(R.id.imageBack);
        update_patient_name = findViewById(R.id.update_patient_name);
        update_patient_phone_number = findViewById(R.id.update_patient_phone_number);
        radioGenderGroup = findViewById(R.id.radioGenderGroup);

        tvAge = findViewById(R.id.tvAge);
        btnAge = findViewById(R.id.btnAge);
        imageUpdate = findViewById(R.id.imageUpdate);
        deleteImage = findViewById(R.id.deleteImage);
        deleteText = findViewById(R.id.deleteText);

        btnAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(Update.this);
                d.setContentView(R.layout.dialog);
                Button button1Set = d.findViewById(R.id.button1Set);
                Button button2Cancel = d.findViewById(R.id.button2Cancel);
                final NumberPicker np = d.findViewById(R.id.numberPicker1);
                np.setMaxValue(100);
                np.setValue(25);
                np.setMinValue(5);
                np.setWrapSelectorWheel(false);
                button1Set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                        age = np.getValue();
                        tvAge.setText("Age : " + np.getValue());
                    }
                });
                button2Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
                d.show();
            }
        });

        imageUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validatePatientName() && validatePhoneNumber() && validateGender() && validateAge()){
                    patient_name = update_patient_name.getEditText().getText().toString().trim();
                    patient_phone_number = update_patient_phone_number.getEditText().getText().toString().trim();
                    String ageString = Integer.toString(age);
                    gender =  radioGenderButton.getText().toString();

                    Map<String, Object> updates = new HashMap<>();
                    updates.put("patient_name", patient_name);
                    updates.put("phone_number", patient_phone_number);
                    updates.put("gender", gender);
                    updates.put("age", ageString);
                    dr.updateChildren(updates);
                    Toast.makeText(Update.this,"Data is updated",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Update.this,Previous.class));
                }
                else {
                    return;
                }
            }
        });

        if (System.currentTimeMillis() - timePass > 240000){
            deleteImage.setEnabled(true);
            deleteImage.setAlpha(1f);
            deleteText.setVisibility(View.INVISIBLE);
            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogSheet deleteAppointment = new DialogSheet(Update.this)
                            .setTitle("Confirm!!!")
                            .setMessage("Do you want to delete the appointment?")
                            .setColoredNavigationBar(true)
                            .setPositiveButton("Ok", new DialogSheet.OnPositiveClickListener() {
                                @Override
                                public void onClick(View view) {
                                    deleteAppoint.child(key).removeValue();
                                    deleteDoctor.child(docNameDelete).child(key).removeValue();
                                    finish();
                                    return;
                                }
                            })
                            .setNegativeButton(android.R.string.cancel, null);
                    deleteAppointment.show();
                }
            });
        }
        else {
            deleteImage.setEnabled(false);
            deleteImage.setAlpha(0.5f);
            deleteText.setVisibility(View.VISIBLE);
        }

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Update.this,Previous.class));
            }
        });
    }

    public void clickedButton(View view) {
        int radioId = radioGenderGroup.getCheckedRadioButtonId();
        // find the radiobutton by the previously returned id
        radioGenderButton =  findViewById(radioId);
    }
    private boolean validatePatientName(){
        String patient = update_patient_name.getEditText().getText().toString().trim();
        if (patient.isEmpty()){
            update_patient_name.setError("Field can't be empty");
            return false;
        }
        else {
            update_patient_name.setError(null);
            return true;
        }
    }

    private boolean validatePhoneNumber(){
        String phone = update_patient_phone_number.getEditText().getText().toString().trim();
        if (phone.isEmpty()){
            update_patient_phone_number.setError("Field can't be empty");
            return false;
        }
        else if (phone.length()==10){
            update_patient_phone_number.setError(null);
            return true;
        }
        else {
            update_patient_phone_number.setError("Phone number should contain 10 digits");
            return false;
        }
    }

    private boolean validateGender(){
        if (radioGenderGroup.getCheckedRadioButtonId() == -1){
            // no radio buttons are checked
            View contextView = findViewById(R.id.contextView);
            Snackbar.make(contextView, "Select a gender", Snackbar.LENGTH_LONG).show();
            return false;
        }
        else {
            return true;
        }
    }

    private boolean validateAge() {
        if (String.valueOf(age).matches("0")){
            View contextView = findViewById(R.id.contextView);
            Snackbar.make(contextView, "Select an age", Snackbar.LENGTH_LONG).show();
            return false;
        }
        else {
            return true;
        }
    }
}
