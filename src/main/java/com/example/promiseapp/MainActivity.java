package com.example.promiseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startLoading();
    }
    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent= new Intent(getApplicationContext(), MainScreen.class);
                startActivity(intent);  //Loading화면을 띄운다.
                finish();   //현재 액티비티 종료
            }
        }, 4000); // 화면에 Logo 2초간 보이기
    }// startLoading Method..
}