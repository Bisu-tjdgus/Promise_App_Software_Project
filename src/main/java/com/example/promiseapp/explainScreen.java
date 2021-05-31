package com.example.promiseapp;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class explainScreen extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private TextView explainkorname, explainprecaution, explainshape, explainway, explainingredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explainscreen);


        explainkorname = (TextView)findViewById(R.id.expliankorname);
        explainprecaution = (TextView)findViewById(R.id.explainprecaution);
        explainshape = (TextView)findViewById(R.id.explainshape);
        explainingredient = (TextView)findViewById(R.id.explainingredient);
        explainway = (TextView)findViewById(R.id.explainway);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    String shape = ds.child("모양").getValue(String.class);
                    String precaution = ds.child("주의").getValue(String.class);
                    String ingredient = ds.child("성분정보").getValue(String.class);
                    String way = ds.child("용법").getValue(String.class);
                    explainkorname.setText("이름: \n"+key);
                    explainshape.setText("모양: \n"+shape);
                    explainprecaution.setText("주의: \n"+precaution);
                    explainingredient.setText("성분정보: \n"+ingredient);
                    explainway.setText("용법: \n"+way);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
}
