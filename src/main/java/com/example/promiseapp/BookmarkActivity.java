package com.example.promiseapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.net.URL;

public class BookmarkActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmarkscreen);

        ListView listView;
        ListViewAdapter adapter;

        adapter = new ListViewAdapter();

        listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);



        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.medicine1),
                "석훈의 약", "Seokhun's medicine");

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.medicine2),
                "세파메칠정","Cephamethyl Tab");

        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.food1),
        "꼬꼬아찌 숯불치킨","I want to eat it");

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.medicine1),
                "석훈의 약", "Seokhun's medicine");

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.medicine2),
                "세파메칠정","Cephamethyl Tab");

        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.food1),
                "꼬꼬아찌 숯불치킨","I want to eat it");

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.medicine1),
                "석훈의 약", "Seokhun's medicine");

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.medicine2),
                "세파메칠정","Cephamethyl Tab");

        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.food1),
                "꼬꼬아찌 숯불치킨","I want to eat it");

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.medicine1),
                "석훈의 약", "Seokhun's medicine");

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.medicine2),
                "세파메칠정","Cephamethyl Tab");

        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.food1),
                "꼬꼬아찌 숯불치킨","I want to eat it");


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);

                String korname = item.getKorname();
                String engname = item.getEngname();
                Drawable poster = item.getPoster();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent,View view, int position, long id){
                Intent intent = new Intent(BookmarkActivity.this, explainScreen.class);
                startActivity(intent);
            }
        });
    }
}


