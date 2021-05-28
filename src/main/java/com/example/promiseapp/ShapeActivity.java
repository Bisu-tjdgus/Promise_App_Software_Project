package com.example.promiseapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShapeActivity extends AppCompatActivity {

    private RadioGroup radioGroup;

    private Button shapeButton1, shapeButton2, shapeButton3, shapeButton4, shapeButton5,
                   shapeButton6, shapeButton7, shapeButton8, shapeButton9;

    private Button colorButton1, colorButton2, colorButton3, colorButton4,
                   colorButton5, colorButton6, colorButton7, colorButton8,
                   colorButton9, colorButton10, colorButton11, colorButton12,
                   colorButton13, colorButton14, colorButton15, colorButton16;

    private Button enterButton, resetButton;

    private EditText frontText, backText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_shape);

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup3);

        shapeButton1 = (Button)findViewById(R.id.shapeButton1);
        shapeButton2 = (Button)findViewById(R.id.shapeButton2);
        shapeButton3 = (Button)findViewById(R.id.shapeButton3);
        shapeButton4 = (Button)findViewById(R.id.shapeButton4);
        shapeButton5 = (Button)findViewById(R.id.shapeButton5);
        shapeButton6 = (Button)findViewById(R.id.shapeButton6);
        shapeButton7 = (Button)findViewById(R.id.shapeButton7);
        shapeButton8 = (Button)findViewById(R.id.shapeButton8);
        shapeButton9 = (Button)findViewById(R.id.shapeButton9);

        colorButton1 = (Button)findViewById(R.id.colorButton1);
        colorButton2 = (Button)findViewById(R.id.colorButton2);
        colorButton3 = (Button)findViewById(R.id.colorButton3);
        colorButton4 = (Button)findViewById(R.id.colorButton4);
        colorButton5 = (Button)findViewById(R.id.colorButton5);
        colorButton6 = (Button)findViewById(R.id.colorButton6);
        colorButton7 = (Button)findViewById(R.id.colorButton7);
        colorButton8 = (Button)findViewById(R.id.colorButton8);
        colorButton9 = (Button)findViewById(R.id.colorButton9);
        colorButton10 = (Button)findViewById(R.id.colorButton10);
        colorButton11 = (Button)findViewById(R.id.colorButton11);
        colorButton12 = (Button)findViewById(R.id.colorButton12);
        colorButton13 = (Button)findViewById(R.id.colorButton13);
        colorButton14 = (Button)findViewById(R.id.colorButton14);
        colorButton15 = (Button)findViewById(R.id.colorButton15);
        colorButton16 = (Button)findViewById(R.id.colorButton16);

        enterButton = (Button)findViewById(R.id.enterbutton);
        resetButton = (Button)findViewById(R.id.resetbutton);
        

//        View.OnClickListener buttonClick = new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                Button button=(Button)findViewById(v.getId());
//                button.setSelected(!button.isSelected());
//            }
//        };

        enterButton.setEnabled(false);
        resetButton.setEnabled(false);

        frontText = (EditText)findViewById(R.id.fronttext);
        backText = (EditText)findViewById(R.id.backtext);

        frontText.addTextChangedListener(new TextWatcher() {                                        //front 미 입력시 버튼 비활성화
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String message1 = frontText.getText().toString();
                if(message1.length()==0){
                    enterButton.setEnabled(false);
                    resetButton.setEnabled(false);
                }
                else{
                    enterButton.setEnabled(true);
                    resetButton.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        enterButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                int id = radioGroup.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(id);
                //rb.getText().toString(); 값으로 전송
            }
        });

    }








}
