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
    private Button nextbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explainscreen2);

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
        explainshape = (TextView)findViewById(R.id.explainshape);
        explainingredient = (TextView)findViewById(R.id.explainingredient);
        explainformulation = (TextView)findViewById(R.id.explainformulation);
        explainfront = (TextView)findViewById(R.id.explainfront);
        explainback = (TextView)findViewById(R.id.explainback);
        explainefficacy = (TextView)findViewById(R.id.explainefficacy);
        previousbutton = (Button)findViewById(R.id.previousbutton);
        nextbutton = (Button)findViewById(R.id.nextbutton);

        explainkorname.setText(name);
        explainshape.setText(shape);
        explainingredient.setText(ingredient);
        explainformulation.setText(formulation);
        explainfront.setText(front);
        explainback.setText(back);
        explainefficacy.setText(efficacy);



        previousbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), explainScreen.class);
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

        nextbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), explainScreen3.class);
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
