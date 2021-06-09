package com.example.promiseapp;

import android.content.Intent;
import android.os.Bundle;
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

public class explainScreen2 extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private TextView explainkorname, explainshape, explainingredient, explainformulation,
    explainfront, explainback, explainefficacy;

    private Button previousbutton;
    private Button nextbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explainscreen2);


        explainkorname = (TextView)findViewById(R.id.expliankorname);
        explainshape = (TextView)findViewById(R.id.explainshape);
        explainingredient = (TextView)findViewById(R.id.explainingredient);
        explainformulation = (TextView)findViewById(R.id.explainformulation);
        explainfront = (TextView)findViewById(R.id.explainfront);
        explainback = (TextView)findViewById(R.id.explainback);
        explainefficacy = (TextView)findViewById(R.id.explainefficacy);
        previousbutton = (Button)findViewById(R.id.previousbutton);
        nextbutton = (Button)findViewById(R.id.nextbutton);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    String shape = ds.child("모양").getValue(String.class);
                    String ingredient = ds.child("성분정보").getValue(String.class);
                    String formulation = ds.child("제형").getValue(String.class);
                    String front = ds.child("식별앞").getValue(String.class);
                    String back = ds.child("식별뒤").getValue(String.class);
                    String efficacy = ds.child("효능").getValue(String.class);
                    explainkorname.setText(key);
                    explainshape.setText(shape);
                    explainingredient.setText(ingredient);
                    explainformulation.setText(formulation);
                    explainfront.setText(front);
                    explainback.setText(back);
                    explainefficacy.setText(efficacy);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        previousbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), explainScreen.class);
                startActivity(intent);
            }
        });

        nextbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), explainScreen3.class);
                startActivity(intent);
            }
        });






    }
}
