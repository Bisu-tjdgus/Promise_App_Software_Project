//package com.example.promiseapp;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.FileProvider;
//
//
//import android.Manifest;
//import android.app.AlarmManager;
//import android.app.AlertDialog;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.graphics.ImageDecoder;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.aneggs.FirebasePost;
//import com.example.aneggs.list_window;
//import com.example.aneggs.R;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//import com.google.mlkit.vision.common.InputImage;
//import com.google.mlkit.vision.text.Text;
//import com.google.mlkit.vision.text.TextRecognition;
//import com.google.mlkit.vision.text.TextRecognizer;
//
//import java.io.File;
//import java.io.IOException;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.GregorianCalendar;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.lang.String;
//
//public class list_window extends AppCompatActivity implements View.OnClickListener{
//
//    private AlarmManager alarmManager;
//    private NotificationManager notificationManager;
//    private DatabaseReference mPostReference;
//
//    final private static String TAG = "cam";
//    final static int TAKE_PICTURE = 1;
//
//    String mCurrentPhotoPath;
//    final static int REQUEST_TAKE_PHOTO = 1;
//
//
//    Button btn_cam;
//    Button btn_Update;
//    Button btn_Insert;
//    Button btn_Select;
//    EditText edit_ID;
//    EditText edit_Name;
//    EditText edit_Date;
//    TextView text_ID;
//    TextView text_Name;
//    TextView text_Date;
//    TextView text_Type;
//    CheckBox type1;
//    CheckBox type2;
//    CheckBox check_ID;
//    CheckBox check_Name;
//    CheckBox check_Date;
//    Spinner type_spinner;
//
//    String ID;
//    String name;
//    String date;
//    String consum_date;
//    String type = "";
//    String sort = "id";
//
//    Bitmap bitmap;
//    String str;
//
//    ArrayAdapter<String> arrayAdapter;
//
//    static ArrayList<String> arrayIndex =  new ArrayList<String>();
//    static ArrayList<String> arrayData = new ArrayList<String>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.search_camera);
//
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {                                                        //카메라 권한 설정
//            if(checkSelfPermission(Manifest.permission.CAMERA) ==
//                    PackageManager.PERMISSION_GRANTED &&
//                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
//                            PackageManager.PERMISSION_GRANTED) {
//                Log.d(TAG, "권한 설정 완료");
//            } else {
//                Log.d(TAG, "권한 설정 요청");
//                ActivityCompat.requestPermissions(list_window.this, new String[]
//                        {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//            }
//        }
//
//        btn_cam = (Button)findViewById(R.id.btn_cam);
//        btn_cam.setOnClickListener(this);
//        btn_Insert = (Button) findViewById(R.id.btn_insert);
//        btn_Insert.setOnClickListener(this);
//        btn_Update = (Button) findViewById(R.id.btn_update);
//        btn_Update.setOnClickListener(this);
//        btn_Select = (Button) findViewById(R.id.btn_select);
//        btn_Select.setOnClickListener(this);
//        edit_ID = (EditText) findViewById(R.id.edit_id);
//        edit_Name = (EditText) findViewById(R.id.edit_name);
//        edit_Date = (EditText) findViewById(R.id.edit_age);
//        text_ID = (TextView) findViewById(R.id.text_id);
//        text_Name = (TextView) findViewById(R.id.text_name);
//        text_Date = (TextView) findViewById(R.id.text_age);
//        text_Type= (TextView) findViewById(R.id.text_gender);
//        //type1 = (CheckBox) findViewById(R.id.check_man);
//        //type1.setOnClickListener(this);
//        //type2 = (CheckBox) findViewById(R.id.check_woman);
//        //type2.setOnClickListener(this);
//        check_ID = (CheckBox) findViewById(R.id.check_userid);
//        check_ID.setOnClickListener(this);
//        check_Name = (CheckBox) findViewById(R.id.check_name);
//        check_Name.setOnClickListener(this);
//        check_Date = (CheckBox) findViewById(R.id.check_age);
//        check_Date.setOnClickListener(this);
//
//        type_spinner = (Spinner)findViewById(R.id.type_spin);
//
//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
//        ListView listView = (ListView) findViewById(R.id.db_list_view);
//        listView.setAdapter(arrayAdapter);
//        listView.setOnItemClickListener(onClickListener);
//        listView.setOnItemLongClickListener(longClickListener);
//        type_spinner.setOnItemSelectedListener(onTypeItemClickListner);
//
//        check_ID.setChecked(true);
//        getFirebaseDatabase();
//
//        btn_Insert.setEnabled(true);
//        btn_Update.setEnabled(false);
//
//        Calendar cal = new GregorianCalendar();
//        btn_Insert.setEnabled(true);
//        btn_Update.setEnabled(false);
//        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//        alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        Log.v("HelloAlarmActivity", cal.getTime().toString());
//
//
//
//    }
//
//    public void setInsertMode(){
//        edit_ID.setText("");
//        edit_Name.setText("");
//        edit_Date.setText("");
//        //type1.setChecked(false);
//        //type2.setChecked(false);
//        btn_Insert.setEnabled(true);
//        btn_Update.setEnabled(false);
//    }
//    //type spinner
//    private AdapterView.OnItemSelectedListener onTypeItemClickListner = new AdapterView.OnItemSelectedListener() {
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            type = parent.getItemAtPosition(position).toString();
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> adapterView) {
//
//        }
//    };
//    //sort spinner
//    private AdapterView.OnItemSelectedListener onSortItemClickListner = new AdapterView.OnItemSelectedListener() {
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            sort = parent.getItemAtPosition(position).toString();
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> adapterView) {
//
//        }
//    };
//
//    // list눌러서 보여주는 것
//    private AdapterView.OnItemClickListener onClickListener = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Log.e("On Click", "position = " + position);
//            Log.e("On Click", "Data: " + arrayData.get(position));
//            String[] tempData = arrayData.get(position).split("\\s+");
//            Log.e("On Click", "Split Result = " + tempData);
//            edit_ID.setText(tempData[0].trim());
//            edit_Name.setText(tempData[1].trim());
//            //edit_Date.setText(tempData[2].trim());
//            String fdate = tempData[2].trim();
//            tempData[2] = calDayback(fdate);
//            edit_Date.setText(tempData[2]);
//            type = tempData[3].trim().toString();
//            /*if(tempData[3].trim().equals("곡류")){
//                type1.setChecked(true);
//                type = "곡류";
//            }else{
//                type2.setChecked(true);
//                type = "유제품류";
//            }*/
//            edit_ID.setEnabled(false);
//            btn_Insert.setEnabled(false);
//            btn_Update.setEnabled(true);
//        }
//    };
//
//    private AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
//        @Override
//        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//            Log.d("Long Click", "position = " + position);
//            final String[] nowData = arrayData.get(position).split("\\s+");
//            ID = nowData[0];
//            String viewData = nowData[0] + ", " + nowData[1] + ", " + nowData[2] + ", " + nowData[3];
//            AlertDialog.Builder dialog = new AlertDialog.Builder(list_window.this);
//            dialog.setTitle("데이터 삭제")
//                    .setMessage("해당 데이터를 삭제 하시겠습니까?" + "\n" + viewData)
//                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            postFirebaseDatabase(false);
//                            getFirebaseDatabase();
//                            setInsertMode();
//                            edit_ID.setEnabled(true);
//                            Toast.makeText(list_window.this, "데이터를 삭제했습니다.", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Toast.makeText(list_window.this, "삭제를 취소했습니다.", Toast.LENGTH_SHORT).show();
//                            setInsertMode();
//                            edit_ID.setEnabled(true);
//                        }
//                    })
//                    .create()
//                    .show();
//            return false;
//        }
//    };
//
//    public boolean IsExistID(){
//        boolean IsExist = arrayIndex.contains(ID);
//        return IsExist;
//    }
//
//    public void postFirebaseDatabase(boolean add){
//        mPostReference = FirebaseDatabase.getInstance().getReference();
//        Map<String, Object> childUpdates = new HashMap<>();
//        Map<String, Object> postValues = null;
//        if(add){
//            FirebasePost post = new FirebasePost(ID, name, date, type, consum_date);
//            postValues = post.toMap();
//        }
//        childUpdates.put("/id_list/" + ID, postValues);
//        mPostReference.updateChildren(childUpdates);
//    }
//
//    public void getFirebaseDatabase(){
//        ValueEventListener postListener = new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.e("getFirebaseDatabase", "key: " + dataSnapshot.getChildrenCount());
//                arrayData.clear();
//                arrayIndex.clear();
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    String key = postSnapshot.getKey();
//                    FirebasePost get = postSnapshot.getValue(FirebasePost.class);
//
//                    if(get.name.equals("milk")) {
//                        String ddd;
//                        int mored =  Integer.parseInt(calDay(get.date)) - 50;
//                        ddd = Integer.toString(mored);
//                        if (mored > 0) {
//                            String plus = "+";
//                            ddd = plus.concat(ddd);
//                        }
//                        consum_date = ddd;
//                    }
//                    else if(get.name.equals("우유")) {
//                        String moredday;
//                        int more =  Integer.parseInt(calDay(date)) - 50;
//                        moredday = Integer.toString(more);
//                        if (more > 0) {
//                            String plus = "+";
//                            moredday = plus.concat(moredday);
//                        }
//                        consum_date = moredday;
//                    }
//                    else if(get.name.equals("슬라이스치즈")) {
//                        String moredday;
//                        int more =  Integer.parseInt(calDay(date)) - 70;
//                        moredday = Integer.toString(more);
//                        if (more > 0) {
//                            String plus = "+";
//                            moredday = plus.concat(moredday);
//                        }
//                        consum_date = moredday;
//                    }
//                    else if(get.name.equals("액상커피")) {
//                        String moredday;
//                        int more =  Integer.parseInt(calDay(date)) - 30;
//                        moredday = Integer.toString(more);
//                        if (more > 0) {
//                            String plus = "+";
//                            moredday = plus.concat(moredday);
//                        }
//                        consum_date = moredday;
//                    }
//                    else if(get.name.equals("달걀") ||get.name.equals("계란")|| get.name.equals("egg")) {
//                        String moredday;
//                        int more =  Integer.parseInt(calDay(date)) - 25;
//                        moredday = Integer.toString(more);
//                        if (more > 0) {
//                            String plus = "+";
//                            moredday = plus.concat(moredday);
//                        }
//                        consum_date = moredday;
//                    }
//                    else if(get.name.equals("두부")) {
//                        String moredday;
//                        int more =  Integer.parseInt(calDay(date)) - 90;
//                        moredday = Integer.toString(more);
//                        if (more > 0) {
//                            String plus = "+";
//                            moredday = plus.concat(moredday);
//                        }
//                        consum_date = moredday;
//                    }
//                    else if(get.name.equals("식빵")) {
//                        String moredday;
//                        int more =  Integer.parseInt(calDay(date)) - 20;
//                        moredday = Integer.toString(more);
//                        if (more > 0) {
//                            String plus = "+";
//                            moredday = plus.concat(moredday);
//                        }
//                        consum_date = moredday;
//                    }
//                    else if(get.name.equals("생면")) {
//                        String moredday;
//                        int more =  Integer.parseInt(calDay(date)) - 50;
//                        moredday = Integer.toString(more);
//                        if (more > 0) {
//                            String plus = "+";
//                            moredday = plus.concat(moredday);
//                        }
//                        consum_date = moredday;
//                    }
//                    else if(get.name.equals("냉동만두")) {
//                        String moredday;
//                        int more =  Integer.parseInt(calDay(date)) - 25;
//                        moredday = Integer.toString(more);
//                        if (more > 0) {
//                            String plus = "+";
//                            moredday = plus.concat(moredday);
//                        }
//                        consum_date = moredday;
//                    }
//                    else {
//                        consum_date = calDay(get.date);
//                    }
//
//                    String[] info = {get.id, get.name, calDay(get.date), get.type, get.consum_date};
//
//                        /*String consum_date;
//                        int more =  Integer.parseInt(calDay(get.date)) - 30;
//                        consum_date = Integer.toString(more);*/
//                    String Result = setTextLength(info[0], 10 - info[0].length()) +
//                            setTextLength(info[1], 20 - info[1].length()) +
//                            setTextLength(info[2], 20 - info[2].length()) +
//                            setTextLength(info[3], 20 - info[3].length()) +
//                            setTextLength(info[4], 20 - info[4].length());
//                    //setTextLength(consum_date, 15 - consum_date.length());
//                    arrayData.add(Result);
//                    arrayIndex.add(key);
//
//                    Log.d("getFirebaseDatabase", "key: " + key);
//                    Log.d("getFirebaseDatabase", "info: " + info[0] + info[1] + get.date + info[3]);
//                    if(calDay(get.date).equals("-3")){
//                        setAlarm("3");
//                    }
//                    else if(calDay(get.date).equals("-1")){
//                        setAlarm("1");
//                    }
//                }
//                arrayAdapter.clear();
//                arrayAdapter.addAll(arrayData);
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w("getFirebaseDatabase","loadPost:onCancelled", databaseError.toException());
//            }
//        };
//        Query sortbyAge = FirebaseDatabase.getInstance().getReference().child("id_list").orderByChild(sort);
//        sortbyAge.addListenerForSingleValueEvent(postListener);
//    }
//
//    public String setTextLength(String text, int length){
//        if(text.length()<length){
//            int gap = length - text.length();
//            for (int i=0; i<gap; i++){
//                text = text + " ";
//            }
//        }
//        return text;
//    }
//
//    final int ONE_DAY = 24 * 60 * 60 * 1000;
//    int year,month, day;
//    private void setAlarm(String a){
//        int DDay = day - Integer.parseInt(a);
//        String s_day, s_year, s_month;
//        s_year = Integer.toString(year);
//        s_month = Integer.toString(month);
//        s_day = Integer.toString(DDay);
//
//        Intent receiverIntent = new Intent(list_window.this, AlarmReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(list_window.this, 0, receiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        String from = s_year + "-" + s_month + "-" + s_day + " 15:25:50";
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date datetime = null;
//        try {
//            datetime = dateFormat.parse(from);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(datetime);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//    }
//
//    String calDay(String enddate){
//
//        long r;
//
//        year = Integer.parseInt(enddate.substring(0,4));
//        month = Integer.parseInt(enddate.substring(5,7)) - 1;
//        day = Integer.parseInt(enddate.substring(8,10));
//
//
//        final Calendar ddayCalendar = Calendar.getInstance();
//        ddayCalendar.set(year,month,day);
//
//        final long dday = ddayCalendar.getTimeInMillis() / ONE_DAY;
//        final long today = Calendar.getInstance().getTimeInMillis()/ONE_DAY;
//        long result = dday - today;
//
//        final String strFormat = new String();
//        if (result > 0) {
//            result = -result;
//            //strFormat = "유통기한이 %d일 남았습니다";
//        } else if (result == 0) {
//            //strFormat = "유통기한이 오늘까지 입니다!!!!";
//        } else {
//            result *= -1;
//            String ans = Long.toString(result);
//            String plus = "+";
//            ans = plus.concat(ans);
//            return ans;
//            //strFormat = "유통기한이 %d일 지났습니다....";
//        }
//
//        return Long.toString(result);
//    }
//
//    String calDayback(String dday){
//        long r;
//
//        int num = -Integer.parseInt(dday);
//
//        Calendar cal = Calendar.getInstance();
//
//        cal.setTime(new Date());
//        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
//
//
//        cal.add(cal.DATE, num);
//        //long result = dday - today;
//
//
//
//        return df.format(cal.getTime());
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()) {
//            case R.id.btn_insert:
//                ID = edit_ID.getText().toString();
//                name = edit_Name.getText().toString();
//                //String enddate = edit_Date.getText().toString();
//
//                //date = calDay(enddate);
//                date = edit_Date.getText().toString();
//
//                if(name.equals("milk")) {
//                    String ddd;
//                    int mored =  Integer.parseInt(calDay(date)) - 50;
//                    ddd = Integer.toString(mored);
//                    if (mored > 0) {
//                        String plus = "+";
//                        ddd = plus.concat(ddd);
//                    }
//                    consum_date = ddd;
//                }
//                else if(name.equals("우유")) {
//                    String moredday;
//                    int more =  Integer.parseInt(calDay(date)) - 50;
//                    moredday = Integer.toString(more);
//                    if (more > 0) {
//                        String plus = "+";
//                        moredday = plus.concat(moredday);
//                    }
//                    consum_date = moredday;
//                }
//                else if(name.equals("치즈")) {
//                    String moredday;
//                    int more =  Integer.parseInt(calDay(date)) - 70;
//                    moredday = Integer.toString(more);
//                    if (more > 0) {
//                        String plus = "+";
//                        moredday = plus.concat(moredday);
//                    }
//                    consum_date = moredday;
//                }
//                else if(name.equals("커피")) {
//                    String moredday;
//                    int more =  Integer.parseInt(calDay(date)) - 30;
//                    moredday = Integer.toString(more);
//                    if (more > 0) {
//                        String plus = "+";
//                        moredday = plus.concat(moredday);
//                    }
//                    consum_date = moredday;
//                }
//                else if(name.equals("달걀") ||name.equals("계란")||name.equals("egg")) {
//                    String moredday;
//                    int more =  Integer.parseInt(calDay(date)) - 25;
//                    moredday = Integer.toString(more);
//                    if (more > 0) {
//                        String plus = "+";
//                        moredday = plus.concat(moredday);
//                    }
//                    consum_date = moredday;
//                }
//                else if(name.equals("두부")) {
//                    String moredday;
//                    int more =  Integer.parseInt(calDay(date)) - 90;
//                    moredday = Integer.toString(more);
//                    if (more > 0) {
//                        String plus = "+";
//                        moredday = plus.concat(moredday);
//                    }
//                    consum_date = moredday;
//                }
//                else if(name.equals("식빵")) {
//                    String moredday;
//                    int more =  Integer.parseInt(calDay(date)) - 20;
//                    moredday = Integer.toString(more);
//                    if (more > 0) {
//                        String plus = "+";
//                        moredday = plus.concat(moredday);
//                    }
//                    consum_date = moredday;
//                }
//                else if(name.equals("생면")) {
//                    String moredday;
//                    int more =  Integer.parseInt(calDay(date)) - 50;
//                    moredday = Integer.toString(more);
//                    if (more > 0) {
//                        String plus = "+";
//                        moredday = plus.concat(moredday);
//                    }
//                    consum_date = moredday;
//                }
//                else if(name.equals("만두")) {
//                    String moredday;
//                    int more =  Integer.parseInt(calDay(date)) - 25;
//                    moredday = Integer.toString(more);
//                    if (more > 0) {
//                        String plus = "+";
//                        moredday = plus.concat(moredday);
//                    }
//                    consum_date = moredday;
//                }
//                else {
//                    consum_date = calDay(date);
//                }
//
//
//                if(!IsExistID()){
//                    postFirebaseDatabase(true);
//                    getFirebaseDatabase();
//                    setInsertMode();
//                }else{
//                    Toast.makeText(list_window.this, "이미 존재하는 ID 입니다. 다른 ID로 설정해주세요.", Toast.LENGTH_LONG).show();
//                }
//                edit_ID.requestFocus();
//                edit_ID.setCursorVisible(true);
//                break;
//
//            case R.id.btn_update:
//                ID = edit_ID.getText().toString();
//                name = edit_Name.getText().toString();
//                date = edit_Date.getText().toString();
//                String endd = edit_Date.getText().toString();
//
//
//                if(name.equals("milk")) {
//                    String ddd;
//                    int mored =  Integer.parseInt(calDay(date)) - 50;
//                    ddd = Integer.toString(mored);
//                    if (mored > 0) {
//                        String plus = "+";
//                        ddd = plus.concat(ddd);
//                    }
//                    consum_date = ddd;
//                }
//                else if(name.equals("우유")) {
//                    String moredday;
//                    int more =  Integer.parseInt(calDay(date)) - 50;
//                    moredday = Integer.toString(more);
//                    if (more > 0) {
//                        String plus = "+";
//                        moredday = plus.concat(moredday);
//                    }
//                    consum_date = moredday;
//                }
//                else if(name.equals("슬라이스치즈")) {
//                    String moredday;
//                    int more =  Integer.parseInt(calDay(date)) - 70;
//                    moredday = Integer.toString(more);
//                    if (more > 0) {
//                        String plus = "+";
//                        moredday = plus.concat(moredday);
//                    }
//                    consum_date = moredday;
//                }
//                else if(name.equals("액상커피")) {
//                    String moredday;
//                    int more =  Integer.parseInt(calDay(date)) - 30;
//                    moredday = Integer.toString(more);
//                    if (more > 0) {
//                        String plus = "+";
//                        moredday = plus.concat(moredday);
//                    }
//                    consum_date = moredday;
//                }
//                else if(name.equals("달걀") ||name.equals("계란")||name.equals("egg")) {
//                    String moredday;
//                    int more =  Integer.parseInt(calDay(date)) - 25;
//                    moredday = Integer.toString(more);
//                    if (more > 0) {
//                        String plus = "+";
//                        moredday = plus.concat(moredday);
//                    }
//                    consum_date = moredday;
//                }
//                else if(name.equals("두부")) {
//                    String moredday;
//                    int more =  Integer.parseInt(calDay(date)) - 90;
//                    moredday = Integer.toString(more);
//                    if (more > 0) {
//                        String plus = "+";
//                        moredday = plus.concat(moredday);
//                    }
//                    consum_date = moredday;
//                }
//                else if(name.equals("식빵")) {
//                    String moredday;
//                    int more =  Integer.parseInt(calDay(date)) - 20;
//                    moredday = Integer.toString(more);
//                    if (more > 0) {
//                        String plus = "+";
//                        moredday = plus.concat(moredday);
//                    }
//                    consum_date = moredday;
//                }
//                else if(name.equals("생면")) {
//                    String moredday;
//                    int more =  Integer.parseInt(calDay(date)) - 50;
//                    moredday = Integer.toString(more);
//                    if (more > 0) {
//                        String plus = "+";
//                        moredday = plus.concat(moredday);
//                    }
//                    consum_date = moredday;
//                }
//                else if(name.equals("냉동만두")) {
//                    String moredday;
//                    int more =  Integer.parseInt(calDay(date)) - 25;
//                    moredday = Integer.toString(more);
//                    if (more > 0) {
//                        String plus = "+";
//                        moredday = plus.concat(moredday);
//                    }
//                    consum_date = moredday;
//                }
//                else {
//                    consum_date = calDay(date);
//                }
//
//
//                //date = calDay(endd);
//
//                postFirebaseDatabase(true);
//                getFirebaseDatabase();
//                setInsertMode();
//                edit_ID.setEnabled(true);
//                edit_ID.requestFocus();
//                edit_ID.setCursorVisible(true);
//                break;
//
//            case R.id.btn_select:
//                getFirebaseDatabase();
//                break;
//
//            case R.id.btn_cam:
//                dispatchTakePictureIntent();
//                break;
//
//            case R.id.check_userid:
//                check_Name.setChecked(false);
//                check_Date.setChecked(false);
//                sort = "id";
//                break;
//
//            case R.id.check_name:
//                check_ID.setChecked(false);
//                check_Date.setChecked(false);
//                sort = "type";
//                break;
//
//            case R.id.check_age:
//                check_ID.setChecked(false);
//                check_Name.setChecked(false);
//                sort = "date";
//                break;
//        }
//    }
//    //카메라 메소드
//    //버튼 -> dispatch(createimagefile) -> onActivity -> text recog
//    //onRequestPermissionsResult 퍼미션 체크
//
//    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        Log.d(TAG, "onRequestPermissionsResult");
//        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
//            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
//        }
//    }
//
//    @Override public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
//        switch (requestCode) {
//            case REQUEST_TAKE_PHOTO: {
//                if (resultCode == RESULT_OK) {
//                    File file = new File(mCurrentPhotoPath);
//                    if (Build.VERSION.SDK_INT >= 29) {
//                        ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), Uri.fromFile(file));
//                        try {
//                            bitmap = ImageDecoder.decodeBitmap(source);
//                            if (bitmap != null) {
//                                runTextRecognition();
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        try {
//                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
//                            if (bitmap != null) {
//                                runTextRecognition();
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace(); } } } break;
//            }
//        }
//
//    }
//    public File createImageFile() throws IOException {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile( imageFileName, ".jpg", storageDir );
//        mCurrentPhotoPath = image.getAbsolutePath(); return image;
//    }
//    public void dispatchTakePictureIntent() {
//        System.out.println("디스패치 시작");
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        System.out.println("인텐트 작동");
//        if(takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            System.out.println("리졸브 액티브");
//            File photoFile = null;
//            System.out.println("파일 변수 생성");
//            try {
//                System.out.println("파일 설정 시작");
//                photoFile = createImageFile();
//                System.out.println("파일 설정 성공");
//            } catch (IOException ex) {
//                System.out.println("오류 오류"); }
//            if(photoFile != null) {
//                System.out.println("uri 생성 시작");
//                Uri photoURI = FileProvider.getUriForFile(this, "com.example.aneggs.fileprovider", photoFile);
//                System.out.println("uri 생성 성공");
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                System.out.println("캐치 진행중 액티비티 시작");
//                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//                System.out.println("캐치 완료 디스패치 끝");
//            }
//            System.out.println("캐치 안함");
//        }
//    }
//
//
//
//    //text recog 메소드
//
//    public void runTextRecognition() {
//        InputImage image = InputImage.fromBitmap(bitmap, 0);
//        TextRecognizer recognizer = TextRecognition.getClient();
//        recognizer.process(image)
//                .addOnSuccessListener(
//                        new OnSuccessListener<Text>() {
//                            @Override
//                            public void onSuccess(Text texts) {
//                                processTextRecognitionResult(texts);
//                            }
//                        })
//                .addOnFailureListener(
//                        new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                // Task failed with an exception
//                                e.printStackTrace();
//                            }
//                        });
//    }
//
//    public void processTextRecognitionResult(Text texts) {
//        List<Text.TextBlock> blocks = texts.getTextBlocks();
//        if (blocks.size() == 0) {
//            return;
//        }
//        for (int i = 0; i < blocks.size(); i++) {
//            System.out.println("텍스트 인식 :"+ i + "번째" +blocks.get(i).getText());
//        }
//        str = blocks.get(0).getText();
//        System.out.println("~문자열: "+str);
//        String[] array = str.split("\\.");
//        System.out.println("  문자열 가르기 성공?");
//        System.out.println(array.length);
//        System.out.println("  문자열 가르기 성공!");
//        for(int i=0;i<array.length;i++)
//            System.out.println("  문자열" + i +" : "+ array[i]);
//        //mTxtDate.setText(String.format("%d/%d/%d", Integer.valueOf(array[0]), Integer.valueOf(array[1]) + 1, Integer.valueOf(array[2])));
//
//        for(int i=0;i<array.length;i++){
//            String[] array2 = array[i].split("\\s");
//            for(int j=0;j<array2.length;j++){
//                if(array2[j]!=" ")
//                    array[i] = array2[j];
//            }
//        }
//        edit_Date.setText(array[0]+"."+array[1]+"."+array[2]);
//
//        System.out.println(array[0]);
//        System.out.println(array[1]);
//        System.out.println(array[2]);
//
//    }
//}