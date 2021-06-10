package com.example.promiseapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class explainScreen extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private ImageView medicineImage;
    Bitmap bitmap;

    private TextView explainkorname, explaincolor, explainmedication, explainprecaution;
    private Button nextbutton;

    String name;

    String inputName;

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
    String picture;

    Thread uThread;

    int howtopage=0;



    int [] cost = new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explainscreen);


        explainkorname = (TextView)findViewById(R.id.expliankorname);
        explaincolor = (TextView)findViewById(R.id.explaincolor);
        explainmedication = (TextView)findViewById(R.id.explainmedication);
        explainprecaution = (TextView)findViewById(R.id.explainprecaution);
        nextbutton = (Button)findViewById(R.id.nextbutton);
        medicineImage = (ImageView)findViewById(R.id.medicineimage);


        Intent intent = getIntent();
        howtopage=intent.getExtras().getInt("howtopage");









        myRef.addValueEventListener(new ValueEventListener() {
            int index=-1;           //약 번호
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(howtopage==1){

                    front = intent.getExtras().getString("front");
                    back = intent.getExtras().getString("back");
                    formulation = intent.getExtras().getString("formulation");
                    shape = intent.getExtras().getString("shape");
                    color = intent.getExtras().getString("color");

                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        index++;
                        String fronttext = ds.child("식별앞").getValue(String.class);
                        String backtext = ds.child("식별뒤").getValue(String.class);
                        String formulationtext = ds.child("제형").getValue(String.class);
                        String shapetext = ds.child("모양").getValue(String.class);
                        String colortext = ds.child("색깔").getValue(String.class);

                        if(front.equals(fronttext)){cost[index]++;}
                        if(back.equals(backtext)){cost[index]++;}
                        if(formulation.equals(formulationtext)){cost[index]++;}
                        if(shape.equals(shapetext)){cost[index]++;}
                        if(color.equals(colortext)){cost[index]++;}

                    }


                    index=-1;

                    int tmp=0;
                    int max=0;
                    for(int i=0;i<15;i++){
                        if(max<cost[i]) {
                            max=cost[i];
                            tmp=i;
                        }
                    }

                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        index++;
                        name = ds.getKey();
                        color = ds.child("색깔").getValue(String.class);
                        medication = ds.child("복약").getValue(String.class);
                        usage = ds.child("용법").getValue(String.class);
                        front = ds.child("식별앞").getValue(String.class);
                        back = ds.child("식별뒤").getValue(String.class);
                        formulation = ds.child("제형").getValue(String.class);
                        shape = ds.child("모양").getValue(String.class);
                        ingredient = ds.child("성분정보").getValue(String.class);
                        efficacy = ds.child("효능").getValue(String.class);
                        precaution = ds.child("주의").getValue(String.class);
                        picture = ds.child("사진").getValue(String.class);

                        explainkorname.setText(name);
                        explaincolor.setText(color);
                        explainmedication.setText(medication);
                        explainprecaution.setText(precaution);
                        medicineImage.setImageBitmap(bitmap);



                        if(index==tmp) {
                            uThread.start();
                            try{

                                //메인 Thread는 별도의 작업을 완료할 때까지 대기한다!

                                //join() 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다림

                                //join() 메서드는 InterruptedException을 발생시킨다.

                                uThread.join();

                                //작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤

                                //UI 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지 지정

                                medicineImage.setImageBitmap(bitmap);

                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }

                            break;
                        }
                    }

                }
                else if(howtopage==2){

                    inputName = intent.getExtras().getString("inputName");

                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        name = ds.getKey();
                        color = ds.child("색깔").getValue(String.class);
                        medication = ds.child("복약").getValue(String.class);
                        usage = ds.child("용법").getValue(String.class);
                        front = ds.child("식별앞").getValue(String.class);
                        back = ds.child("식별뒤").getValue(String.class);
                        formulation = ds.child("제형").getValue(String.class);
                        shape = ds.child("모양").getValue(String.class);
                        ingredient = ds.child("성분정보").getValue(String.class);
                        efficacy = ds.child("효능").getValue(String.class);
                        precaution = ds.child("주의").getValue(String.class);
                        picture = ds.child("사진").getValue(String.class);

                        explainkorname.setText(name);
                        explaincolor.setText(color);
                        explainmedication.setText(medication);
                        explainprecaution.setText(precaution);
                        medicineImage.setImageBitmap(bitmap);


                        if(inputName.equals(name)) {
                            uThread.start();
                            try{

                                //메인 Thread는 별도의 작업을 완료할 때까지 대기한다!

                                //join() 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다림

                                //join() 메서드는 InterruptedException을 발생시킨다.

                                uThread.join();

                                //작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤

                                //UI 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지 지정

                                medicineImage.setImageBitmap(bitmap);

                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                }
                
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        uThread = new Thread() {

            @Override

            public void run(){

                try{

                    //서버에 올려둔 이미지 URL

                    URL url = new URL(picture);



                    //Web에서 이미지 가져온 후 ImageView에 지정할 Bitmap 만들기



                    /* URLConnection 생성자가 protected로 선언되어 있으므로

                     개발자가 직접 HttpURLConnection 객체 생성 불가 */

                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                    /* openConnection()메서드가 리턴하는 urlConnection 객체는

                    HttpURLConnection의 인스턴스가 될 수 있으므로 캐스팅해서 사용한다*/



                    conn.setDoInput(true); //Server 통신에서 입력 가능한 상태로 만듦

                    conn.connect(); //연결된 곳에 접속할 때 (connect() 호출해야 실제 통신 가능함)





                    InputStream is = conn.getInputStream(); //inputStream 값 가져오기

                    bitmap = BitmapFactory.decodeStream(is); // Bitmap으로 반환





                }catch (MalformedURLException e){

                    e.printStackTrace();

                }catch (IOException e){

                    e.printStackTrace();

                }

            }

        };

//        uThread.start(); // 작업 Thread 실행





        nextbutton.setOnClickListener(new View.OnClickListener() {

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
