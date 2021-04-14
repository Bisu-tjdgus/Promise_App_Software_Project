package com.example.promiseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchscreen);
        Button search_camera = (Button) findViewById(R.id.search_camera);
        Button search_name = (Button) findViewById(R.id.search_name);
        Button search_shape = (Button) findViewById(R.id.search_shape);

        search_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        search_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, NameActivity.class);
                startActivity(intent);
            }
        });

        search_shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, ShapeActivity.class);
                startActivity(intent);
            }
        });
    }

}
