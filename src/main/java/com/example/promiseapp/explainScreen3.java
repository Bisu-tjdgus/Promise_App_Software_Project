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

public class explainScreen3 extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private TextView explainkorname, explainprecaution;

    String name;

    String front;               //식별앞
    String back;                //식별뒤
    String formulation;         //제형
    String shape;               //모양
    String color;               //색깔
    String medication;          //복약
    String usage;               //용법
    String ingredient;          //성분정보
    String efficacy;            //효능
    String precaution;          //주의

    private Button previousbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explainscreen3);

        Intent intent = getIntent();


        front = intent.getExtras().getString("front");
        back = intent.getExtras().getString("back");
        formulation = intent.getExtras().getString("formulation");
        shape = intent.getExtras().getString("shape");
        color = intent.getExtras().getString("color");
        medication = intent.getExtras().getString("medication");
        usage = intent.getExtras().getString("usage");
        ingredient = intent.getExtras().getString("ingredient");
        efficacy = intent.getExtras().getString("efficacy");
        precaution = intent.getExtras().getString("precaution");
        name = intent.getExtras().getString("name");


        explainkorname = (TextView)findViewById(R.id.expliankorname);
        explainprecaution = (TextView)findViewById(R.id.explainprecaution);

        explainkorname.setText(name);
        explainprecaution.setText(precaution);

        previousbutton = (Button)findViewById(R.id.previousbutton);


        previousbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), explainScreen2.class);
                intent.putExtra("name",name);
                intent.putExtra("color",color);
                intent.putExtra(" medication", medication);
                intent.putExtra("usage",usage);
                intent.putExtra("front",front);
                intent.putExtra("back",back);
                intent.putExtra("formulation",formulation);
                intent.putExtra("shape",shape);
                intent.putExtra("ingredient",ingredient);
                intent.putExtra("efficacy",efficacy);
                intent.putExtra("precaution",precaution);
                startActivity(intent);
            }
        });



    }
}
