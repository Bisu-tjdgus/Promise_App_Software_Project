package com.example.promiseapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ShapeActivity extends AppCompatActivity {

    private RadioGroup radioformulation,radioshape;

    private CheckBox colorbutton1,colorbutton2,colorbutton3,colorbutton4,colorbutton5
            ,colorbutton6,colorbutton7, colorbutton8, colorbutton9, colorbutton10
            ,colorbutton11, colorbutton12, colorbutton13, colorbutton14, colorbutton15, colorbutton16;

    private Button enterButton;

    private EditText frontText, backText;


    String front;
    String back;
    String formulation;
    String shape;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_shape);

        radioformulation = (RadioGroup)findViewById(R.id.radioformulation);
        radioshape = (RadioGroup)findViewById(R.id.radioshape);

        radioformulation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButton1){
                    formulation="정제";
                }
                else if(checkedId== R.id.radioButton2){
                    formulation="경질캡슐";
                }
                else if(checkedId==R.id.radioButton3){
                    formulation="연질캡슐";
                }
            }
        });

        radioshape.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.shapeButton1){
                    shape="원형";
                }
                else if(checkedId == R.id.shapeButton2){
                    shape="타원형";
                }
                else if(checkedId == R.id.shapeButton3){
                    shape="장방형";
                }
            }
        });

        colorbutton1=(CheckBox)findViewById(R.id.colorButton1);
        colorbutton2=(CheckBox)findViewById(R.id.colorButton2);
        colorbutton3=(CheckBox)findViewById(R.id.colorButton3);
        colorbutton4=(CheckBox)findViewById(R.id.colorButton4);
        colorbutton5=(CheckBox)findViewById(R.id.colorButton5);
        colorbutton6=(CheckBox)findViewById(R.id.colorButton6);
        colorbutton7=(CheckBox)findViewById(R.id.colorButton7);
        colorbutton8=(CheckBox)findViewById(R.id.colorButton8);
        colorbutton9=(CheckBox)findViewById(R.id.colorButton9);
        colorbutton10=(CheckBox)findViewById(R.id.colorButton10);
        colorbutton11=(CheckBox)findViewById(R.id.colorButton11);
        colorbutton12=(CheckBox)findViewById(R.id.colorButton12);
        colorbutton13=(CheckBox)findViewById(R.id.colorButton13);
        colorbutton14=(CheckBox)findViewById(R.id.colorButton14);
        colorbutton15=(CheckBox)findViewById(R.id.colorButton15);
        colorbutton16=(CheckBox)findViewById(R.id.colorButton16);


        enterButton = (Button)findViewById(R.id.enterbutton);
        

//        View.OnClickListener buttonClick = new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                Button button=(Button)findViewById(v.getId());
//                button.setSelected(!button.isSelected());
//            }
//        };

        enterButton.setEnabled(false);

        frontText = (EditText)findViewById(R.id.fronttext);
        backText = (EditText)findViewById(R.id.backtext);




        frontText.addTextChangedListener(new TextWatcher() {                                        //front 미 입력시 버튼 비활성화
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                front = frontText.getText().toString();
                if(front.length()==0){
                    enterButton.setEnabled(false);

                }
                else{
                    enterButton.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        enterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                back= backText.getText().toString();
                ArrayList<String> result = new ArrayList();  // 결과를 출력할 문자열  ,
                if(colorbutton1.isChecked() == true) result.add(colorbutton1.getText().toString());
                if(colorbutton2.isChecked() == true) result.add(colorbutton2.getText().toString());
                if(colorbutton3.isChecked() == true) result.add(colorbutton3.getText().toString());
                if(colorbutton4.isChecked() == true) result.add(colorbutton4.getText().toString());
                if(colorbutton5.isChecked() == true) result.add(colorbutton5.getText().toString());
                if(colorbutton6.isChecked() == true) result.add(colorbutton6.getText().toString());
                if(colorbutton7.isChecked() == true) result.add(colorbutton7.getText().toString());
                if(colorbutton8.isChecked() == true) result.add(colorbutton8.getText().toString());
                if(colorbutton9.isChecked() == true) result.add(colorbutton9.getText().toString());
                if(colorbutton10.isChecked() == true) result.add(colorbutton10.getText().toString());
                if(colorbutton11.isChecked() == true) result.add(colorbutton11.getText().toString());
                if(colorbutton12.isChecked() == true) result.add(colorbutton12.getText().toString());
                if(colorbutton13.isChecked() == true) result.add(colorbutton13.getText().toString());
                if(colorbutton14.isChecked() == true) result.add(colorbutton14.getText().toString());
                if(colorbutton15.isChecked() == true) result.add(colorbutton15.getText().toString());
                if(colorbutton16.isChecked() == true) result.add(colorbutton16.getText().toString());

                Intent intent = new Intent(getApplicationContext(), explainScreen.class);
                intent.putExtra("howtopage",1);
                intent.putExtra("formulation",formulation);
                intent.putExtra("back", back);
                intent.putExtra("shape",shape);
                intent.putExtra("color", result.get(0));
                intent.putExtra("front",front);
                startActivity(intent);
            }
        });

    }
}
