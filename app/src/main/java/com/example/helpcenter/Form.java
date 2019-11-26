package com.example.helpcenter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.marcoscg.dialogsheet.DialogSheet;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import maes.tech.intentanim.CustomIntent;

public class Form extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextInputLayout text_patient_name,patientPhoneNumber;
    private TextView tvAge,tvDate,tvDepartment, tvDoctor;
    private ImageView imageBtnForm, btnDepartment, btnDoctor, btnAge, btnDate;
    public int age;
    public String date, timeSlot, doctorName, deptName,phoneNumber,msg,patientName;
    private RadioGroup radioGenderGroup;
    private RadioButton radioGenderButton;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    String[] departmentNames;
    private String userID;

    public static String SHARED_PREFS = "sharedPrefs";
    public static String TIME = "time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setUi();

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        userID = user.getUid();

        final String[] finalDepartment = new String[1];

        Spinner spinnerTime = findViewById(R.id.spinnerTime);
        List<String> listTime = new ArrayList<>();
        listTime.add("10:00AM - 10:30AM");
        listTime.add("10:30AM - 11:00AM");
        listTime.add("11:00AM - 11:30AM");
        listTime.add("11:30AM - 12:00PM");
        listTime.add("12:00PM - 12:30PM");
        listTime.add("12:30PM - 1:00PM");
        listTime.add("2:00PM - 2:30PM");
        listTime.add("2:30PM - 3:00PM");

        ArrayAdapter<String> adapterTime = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listTime);
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(adapterTime);
        spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //default
                timeSlot = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(Form.this);
                d.setContentView(R.layout.dialog);
                Button button1Set = d.findViewById(R.id.button1Set);
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
                d.show();
            }
        });

        btnDepartment .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                departmentNames = new String[]{"Ears,Nose and Throat(ENT)", "Bone", "Nerve", "Heart", "Skin"};
                AlertDialog.Builder builderDepart = new AlertDialog.Builder(Form.this);
                builderDepart.setTitle("Choose the department");
                builderDepart.setSingleChoiceItems(departmentNames, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finalDepartment[0] = departmentNames[which];
                        tvDepartment.setText(departmentNames[which]);
                        dialog.dismiss();
                    }
                });

                builderDepart.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertDialogDepart = builderDepart.create();
                alertDialogDepart.show();
            }
        });

        btnDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tvDepartment.getText().equals("")){
                    View contextView = findViewById(R.id.contextView);
                    Snackbar.make(contextView, "Select any Department", Snackbar.LENGTH_LONG).show();
                }
                else {
                    if (finalDepartment[0].equals("Ears,Nose and Throat(ENT)")) {
                        Intent i = new Intent(Form.this, DoctorViewPage.class);
                        String fromDepartment = "ent";
                        i.putExtra("from", fromDepartment);
                        startActivity(i);
                    }
                    else if (finalDepartment[0].equals("Bone")) {
                        Intent i = new Intent(Form.this, DoctorViewPage.class);
                        String fromDepartment = "bone";
                        i.putExtra("from", fromDepartment);
                        startActivity(i);
                    }
                    else if (finalDepartment[0].equals("Nerve")) {
                        Intent i = new Intent(Form.this, DoctorViewPage.class);
                        String fromDepartment = "nerve";
                        i.putExtra("from", fromDepartment);
                        startActivity(i);
                    }
                    else if (finalDepartment[0].equals("Heart")) {
                        Intent i = new Intent(Form.this, DoctorViewPage.class);
                        String fromDepartment = "heart";
                        i.putExtra("from", fromDepartment);
                        startActivity(i);
                    }
                    else if (finalDepartment[0].equals("Skin")) {
                        Intent i = new Intent(Form.this, DoctorViewPage.class);
                        String fromDepartment = "skin";
                        i.putExtra("from", fromDepartment);
                        startActivity(i);
                    }
                }
            }
        });

        Intent i = getIntent();
        deptName = i.getStringExtra("deptN");
        tvDepartment.setText(deptName);
        doctorName = i.getStringExtra("docName");
        tvDoctor.setText(doctorName);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });

        imageBtnForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateDepartment() && validateDoctor() && validatePatientName() && validatePhoneNumber() && validateGender() && validateAge() && validateDate()){

                    String patientName = text_patient_name.getEditText().getText().toString();
                    phoneNumber = patientPhoneNumber.getEditText().getText().toString();
                    String ageString = Integer.toString(age);
                    String gender =  radioGenderButton.getText().toString();

                    DialogSheet dialogSheet = new DialogSheet(Form.this)
                            .setTitle("Confirm your details...")
                            .setMessage("\nPatient Name:  " + patientName + "\n\n"
                                    + "Phone Number:  " + phoneNumber + "\n\n"
                                    + "Gender:  " + gender + "\n\n"
                                    + "Age:  " + ageString + "\n\n"
                                    + "Department:  " + deptName + "\n\n"
                                    + "Doctor Name:  " + doctorName + "\n\n"
                                    + "Date:  " + date + "\n\n"
                                    + "Time Slot:  " + timeSlot)
                            .setColoredNavigationBar(true)
                            .setPositiveButton("Book", new DialogSheet.OnPositiveClickListener() {
                                @Override
                                public void onClick(View view) {
                                    sendData();
                                }
                            })
                            .setNegativeButton(android.R.string.cancel, null);

                    dialogSheet.show();
                }
                else {
                    return;
                }
            }
        });
    }

    private void sendData() {
        patientName = text_patient_name.getEditText().getText().toString();
        phoneNumber = patientPhoneNumber.getEditText().getText().toString();
        String ageString = Integer.toString(age);
        String gender =  radioGenderButton.getText().toString();

        final UserInformation addInfo = new UserInformation();
        addInfo.setPatient_name(patientName);
        addInfo.setPhone_number(phoneNumber);
        addInfo.setGender(gender);
        addInfo.setAge(ageString);
        addInfo.setDepartment_name(deptName);
        addInfo.setDoctor_name(doctorName);
        addInfo.setDate(date);
        addInfo.setTime(timeSlot);

        final UserInformation addDate = new UserInformation();
        addDate.setTime(timeSlot);

        progressDialog.setMessage("Please wait !");
        progressDialog.show();

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference myRefAppointment = firebaseDatabase.getReferenceFromUrl("https://help-center-e8a5f.firebaseio.com/").child("appointments");
        final DatabaseReference myRefOnlyDoctor = firebaseDatabase.getReferenceFromUrl("https://help-center-e8a5f.firebaseio.com/");

/*
        myRef.child(userID).child(date).setValue(addInfo);
        myRefDoctor.child(doctorName).child(userID).child(date).child(timeSlot).setValue(addDate);
        myRefOnlyDoctor.child(doctorName).child(date).child(timeSlot).setValue(addDate);

        progressDialog.dismiss();
        Intent i = new Intent(Form.this, Confirmed.class);
        startActivity(i);
*/

        myRefAppointment.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        String checkDateAppoint = ds.getKey().toString();
                        if (checkDateAppoint.equals(date)){
                            progressDialog.dismiss();

                            DialogSheet errorAppointment = new DialogSheet(Form.this)
                                    .setTitle("Change the date!!!")
                                    .setMessage("\nYou can book only one appointment in a day...")
                                    .setColoredNavigationBar(true)
                                    .setPositiveButton("Ok", new DialogSheet.OnPositiveClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setNegativeButton(android.R.string.cancel, null);

                            errorAppointment.show();
                            break;
                        }
                        else {
                            myRefOnlyDoctor.child(doctorName).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()){
                                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                                            final String checkDate = ds.getKey().toString();
                                            if (checkDate.equals(date)){
                                                myRefOnlyDoctor.child(doctorName).child(checkDate).addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        for (DataSnapshot ds4 : dataSnapshot.getChildren()){
                                                            String checkTime = ds4.getKey().toString();
                                                            if (checkTime.equals(timeSlot)){
                                                                progressDialog.dismiss();

                                                                DialogSheet errorDoctor = new DialogSheet(Form.this)
                                                                        .setTitle("Error!!!")
                                                                        .setMessage("\nThis doctor has already appointed by an other patient...")
                                                                        .setColoredNavigationBar(true)
                                                                        .setPositiveButton("Ok", new DialogSheet.OnPositiveClickListener() {
                                                                            @Override
                                                                            public void onClick(View view) {
                                                                                 startActivity(new Intent(Form.this,Welcome.class));
                                                                            }
                                                                        })
                                                                        .setNegativeButton(android.R.string.cancel, null);

                                                                errorDoctor.show();
                                                                break;
                                                            }
                                                            else {
                                                                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                                editor.putLong(TIME, System.currentTimeMillis());
                                                                editor.apply();

                                                                myRefAppointment.child(userID).child(date).setValue(addInfo);
                                                                myRefOnlyDoctor.child(doctorName).child(date).child(timeSlot).setValue(addDate);

                                                                progressDialog.dismiss();
                                                                Intent i = new Intent(Form.this, Confirmed.class);
                                                                startActivity(i);
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                            }
                                            else{
                                                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putLong(TIME, System.currentTimeMillis());
                                                editor.apply();

                                                myRefAppointment.child(userID).child(date).setValue(addInfo);
                                                myRefOnlyDoctor.child(doctorName).child(date).child(timeSlot).setValue(addDate);

                                                progressDialog.dismiss();
                                                Intent i = new Intent(Form.this, Confirmed.class);
                                                startActivity(i);
                                                break;
                                            }
                                        }
                                    }
                                    else if(!dataSnapshot.exists()) {
                                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putLong(TIME, System.currentTimeMillis());
                                        editor.apply();

                                        myRefAppointment.child(userID).child(date).setValue(addInfo);
                                        myRefOnlyDoctor.child(doctorName).child(date).child(timeSlot).setValue(addDate);

                                        progressDialog.dismiss();
                                        Intent i = new Intent(Form.this, Confirmed.class);
                                        startActivity(i);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }
                else {

                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong(TIME, System.currentTimeMillis());
                    editor.apply();

                    myRefAppointment.child(userID).child(date).setValue(addInfo);
                    myRefOnlyDoctor.child(doctorName).child(date).child(timeSlot).setValue(addDate);

                    progressDialog.dismiss();
                    Intent i = new Intent(Form.this, Confirmed.class);
                    startActivity(i);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setUi(){
        text_patient_name = findViewById(R.id.text_patient_name);
        patientPhoneNumber = findViewById(R.id.patientPhoneNumber);
        btnAge = findViewById(R.id.btnAge);
        tvAge = findViewById(R.id.tvAge);
        tvDate = findViewById(R.id.tvDate);
        btnDate = findViewById(R.id.btnDate);
        imageBtnForm = findViewById(R.id.imageBtnForm);
        radioGenderGroup = findViewById(R.id.radioGenderGroup);
        tvDepartment = findViewById(R.id.tvDepartment);
        btnDepartment = findViewById(R.id.btnDepartment);
        tvDoctor = findViewById(R.id.tvDoctor);
        btnDoctor = findViewById(R.id.btnDoctor);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        view.setMinDate(System.currentTimeMillis() - 10000);
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        date = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        tvDate.setText(String.format("Select Date : %s", date));
    }

    public void clickedButton(View view) {
        int radioId = radioGenderGroup.getCheckedRadioButtonId();
        // find the radiobutton by the previously returned id
        radioGenderButton =  findViewById(radioId);
    }

    private boolean validateDepartment() {
        if (tvDepartment.getText().equals("")){
            View contextView = findViewById(R.id.contextView);
            Snackbar.make(contextView, "Select a department", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    private boolean validateDoctor() {
        if (tvDoctor.getText().equals("")){
            View contextView = findViewById(R.id.contextView);
            Snackbar.make(contextView, "Select a doctor", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    private boolean validatePatientName(){
        String patient = text_patient_name.getEditText().getText().toString().trim();
        if (patient.isEmpty()){
            text_patient_name.setError("Field can't be empty");
            Snackbar.make(findViewById(R.id.contextView),"Enter the patient name",Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else {
            text_patient_name.setError(null);
            return true;
        }
    }

    private boolean validatePhoneNumber(){
        String phone = patientPhoneNumber.getEditText().getText().toString().trim();
        if (phone.isEmpty()){
            patientPhoneNumber.setError("Field can't be empty");
            Snackbar.make(findViewById(R.id.contextView),"Enter the phone number",Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else if (phone.length()==10){
            patientPhoneNumber.setError(null);
            return true;
        }
        else {
            patientPhoneNumber.setError("Phone number should contain 10 digits");
            Snackbar.make(findViewById(R.id.contextView),"Phone number should contain 10 digits",Snackbar.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean validateGender(){
        if (radioGenderGroup.getCheckedRadioButtonId() == -1){
            // no radio buttons are checked
            View contextView = findViewById(R.id.contextView);
            Snackbar.make(contextView, "Select a gender", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }
    private boolean validateDate() {
        if (tvDate.getText().equals("Select Date :")){
            View contextView = findViewById(R.id.contextView);
            Snackbar.make(contextView, "Select a date", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }
    private boolean validateAge() {
        if (String.valueOf(age).matches("0")){
            View contextView = findViewById(R.id.contextView);
            Snackbar.make(contextView, "Select an age", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(Form.this,"right-to-left");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this,Welcome.class);
        startActivity(i);
        CustomIntent.customType(Form.this,"right-to-left");
    }
}
