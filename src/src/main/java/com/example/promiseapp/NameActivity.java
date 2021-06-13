package com.example.promiseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NameActivity extends AppCompatActivity {

    private EditText nameText;

    String name;

    private Button enterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_name);


        nameText = (EditText)findViewById(R.id.nametext);
        enterButton = (Button)findViewById(R.id.enterbutton);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name=nameText.getText().toString();


                Intent intent = new Intent(getApplicationContext(), explainScreen.class);
                intent.putExtra("howtopage",2);
                intent.putExtra("inputName",name);
                startActivity(intent);
            }
        });
    }
}
