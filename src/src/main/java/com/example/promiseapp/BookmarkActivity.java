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
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);
                String name=item.getKorname();
                System.out.println("-----출력값 : "+name);

                Intent intent = new Intent(getApplicationContext(), explainScreen.class);
                intent.putExtra("howtopage",2);
                intent.putExtra("inputName",name);
                startActivity(intent);
            }
        });
    }
}


