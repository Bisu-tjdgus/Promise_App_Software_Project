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

    private Button previousbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explainscreen3);


        explainkorname = (TextView)findViewById(R.id.expliankorname);
        explainprecaution = (TextView)findViewById(R.id.explainprecaution);

        previousbutton = (Button)findViewById(R.id.previousbutton);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    String precaution = ds.child("주의").getValue(String.class);
                    explainkorname.setText(key);
                    explainprecaution.setText(precaution);
                }

            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        previousbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), explainScreen2.class);
                startActivity(intent);
            }
        });






    }
}
