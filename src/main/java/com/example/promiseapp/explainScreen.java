package com.example.promiseapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    private TextView explainkorname, explaincolor, explainmedication, explainprecaution;
    private Button nextbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explainscreen);


        explainkorname = (TextView)findViewById(R.id.expliankorname);
        explaincolor = (TextView)findViewById(R.id.explaincolor);
        explainmedication = (TextView)findViewById(R.id.explainmedication);
        explainprecaution = (TextView)findViewById(R.id.explainprecaution);
        nextbutton = (Button)findViewById(R.id.nextbutton);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    String color = ds.child("색깔").getValue(String.class);
                    String medication = ds.child("복약").getValue(String.class);
                    String precaution = ds.child("용법").getValue(String.class);

                    explainkorname.setText(key);
                    explaincolor.setText(color);
                    explainmedication.setText(medication);
                    explainprecaution.setText(precaution);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        nextbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), explainScreen2.class);
                startActivity(intent);
            }
        });








    }
}
