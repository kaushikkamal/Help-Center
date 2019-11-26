package com.example.helpcenter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.marcoscg.dialogsheet.DialogSheet;

import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private DataAdapter mDataAdapter;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference, mDoctor, mOnlyDoctor;
    FirebaseAuth firebaseAuthRecycle = FirebaseAuth.getInstance();

    private FirebaseUser userRecycle = firebaseAuthRecycle.getCurrentUser();
    private String userID = userRecycle.getUid();

    public RecyclerView_Config() {
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReferenceFromUrl("https://help-center-e8a5f.firebaseio.com/").child("appointments").child(userID);
        //mDoctor = mDatabase.getReferenceFromUrl("https://help-center-f4570.firebaseio.com/").child("doctors");
        mOnlyDoctor = mDatabase.getReferenceFromUrl("https://help-center-e8a5f.firebaseio.com/");
    }

    public void setConfig(RecyclerView recyclerView, Context context, List<UserInformation> uInfos, final List<String> keys){
        mContext = context;
        mDataAdapter = new DataAdapter(uInfos, keys);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mDataAdapter);

        if (linearLayoutManager.getItemCount()==0){

            AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
            alert.setTitle("No Previous Appointments!!!");
            alert.setMessage("You haven't book any appointment yet...");
            alert.setCancelable(false);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mContext.startActivity(new Intent(mContext,Welcome.class));
                }
            });
            AlertDialog dialog = alert.create();
            dialog.show();
        }
    }

    class DataItemView extends RecyclerView.ViewHolder{
        private TextView prev_patient_name,prev_patient_phone_number,prev_department,prev_doctor,prev_age,prev_sex,prev_date,prev_time;
        private String key,sendDoc;
        private ImageView image_edit;
        UserInformation userData = new UserInformation();

        public DataItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.cardview, parent, false));
            prev_patient_name = itemView.findViewById(R.id.prev_patient_name);
            prev_patient_phone_number = itemView.findViewById(R.id.prev_patient_phone_number);
            prev_department = itemView.findViewById(R.id.prev_department);
            prev_doctor = itemView.findViewById(R.id.prev_doctor);
            prev_age = itemView.findViewById(R.id.prev_age);
            prev_sex = itemView.findViewById(R.id.prev_sex);
            prev_date = itemView.findViewById(R.id.prev_date);
            prev_time = itemView.findViewById(R.id.prev_time);
            image_edit = itemView.findViewById(R.id.image_edit);

            image_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext,Update.class);
                    i.putExtra("key",key);
                    i.putExtra("sendDocDate",sendDoc);
                    mContext.startActivity(i);
                }
            });
        }
        public void bind(UserInformation userInformation,String key){
            prev_patient_name.setText(userInformation.getPatient_name());
            prev_patient_phone_number.setText(userInformation.getPhone_number());
            prev_department.setText(userInformation.getDepartment_name());
            prev_doctor.setText(userInformation.getDoctor_name());
            prev_age.setText(userInformation.getAge());
            prev_sex.setText(userInformation.getGender());
            prev_date.setText(userInformation.getDate());
            prev_time.setText(userInformation.getTime());
            sendDoc = userInformation.getDoctor_name();
            this.key = key;
        }
    }

    class DataAdapter extends RecyclerView.Adapter<DataItemView>{
        private List<UserInformation> mDataList;
        private List<String> mKeys;

        public DataAdapter(List<UserInformation> mDataList, List<String> mKeys) {
            this.mDataList = mDataList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public DataItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DataItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull DataItemView holder, int position) {
            holder.bind(mDataList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }
    }
}