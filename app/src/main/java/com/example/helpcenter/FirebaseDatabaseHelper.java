package com.example.helpcenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference, mDoctor;
    private List<UserInformation> uInformation = new ArrayList<>();

    FirebaseAuth firebaseAuthHelper = FirebaseAuth.getInstance();

    private FirebaseUser userHelper = firebaseAuthHelper.getCurrentUser();
    private String userID = userHelper.getUid();

    public interface DataStatus{
        void DataIsLoaded(List<UserInformation> uInformation, List<String> keys);
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReferenceFromUrl("https://help-center-e8a5f.firebaseio.com/").child("appointments").child(userID);
        mDoctor = mDatabase.getReferenceFromUrl("https://help-center-e8a5f.firebaseio.com/").child("doctors");
    }

    public void readData(final DataStatus dataStatus){
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uInformation.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    UserInformation uInfo = keyNode.getValue(UserInformation.class);
                    uInformation.add(uInfo);
                }
                dataStatus.DataIsLoaded(uInformation,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateData(String key, UserInformation userInformation, final  DataStatus dataStatus){
        mReference.child(key).setValue(userInformation).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdated();
            }
        });
    }

}
