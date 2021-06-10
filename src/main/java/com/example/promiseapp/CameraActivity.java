package com.example.promiseapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CameraActivity extends AppCompatActivity {
    final private static String TAG = "GILBOMI";
    Button btn_photo, enterbutton;
    TextView sample;
    ImageView imagephoto;

    String name = "";

    Bitmap bitmap;

    List<Text.TextBlock> blocks;

    final static int TAKE_PICTURE = 1;

    String mCurrentPhotoPath;
    final static int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_camera);

        sample = (TextView)findViewById(R.id.sample);
        btn_photo = (Button)findViewById(R.id.btn_photo);
        enterbutton = (Button)findViewById(R.id.enterbutton);
        imagephoto = (ImageView)findViewById(R.id.image);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(CameraActivity.this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }



        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_photo:
                        dispatchTakePictureIntent();
                        break;
                }
            }
        });

        enterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sample.setText(name);
            }
        });
    }

    // 권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }

    //카메라 메소드
    //버튼 -> dispatch(createimagefile) -> onActivity -> text recog
    //onRequestPermissionsResult 퍼미션 체크

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO: {
                if (resultCode == RESULT_OK) {
                    File file = new File(mCurrentPhotoPath);
                    if (Build.VERSION.SDK_INT >= 29) {
                        ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), Uri.fromFile(file));
                        try {
                            bitmap = ImageDecoder.decodeBitmap(source);
                            if (bitmap != null) {
                                runTextRecognition();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                            if (bitmap != null) {
                                runTextRecognition();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            }
        }

    }

    public File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

//    public void dispatchTakePictureIntent() {
//        System.out.println("디스패치 시작");
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        System.out.println("인텐트 작동");
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            System.out.println("리졸브 액티브");
//            File photoFile = null;
//            System.out.println("파일 변수 생성");
//            try {
//                System.out.println("파일 설정 시작");
//                photoFile = createImageFile();
//                System.out.println("파일 설정 성공");
//            } catch (IOException ex) {
//                System.out.println("오류 오류");
//            }
//            if (photoFile != null) {
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


    //text recog 메소드

    public void runTextRecognition() {
        InputImage image = InputImage.fromBitmap(bitmap, 0);
        imagephoto.setImageBitmap(bitmap);
        TextRecognizer recognizer = TextRecognition.getClient();
        recognizer.process(image)
                .addOnSuccessListener(
                        new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text texts) {
                                if(texts.getTextBlocks().size()==0) name= "없음";
                                else{name = blocks.get(0).getText();}
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                e.printStackTrace();
                            }
                        });
    }

//
//    // 카메라로 촬영한 영상을 가져오는 부분
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
//        try {
//            switch (requestCode) {
//                case REQUEST_TAKE_PHOTO: {
//                    if (resultCode == RESULT_OK) {
//                        File file = new File(mCurrentPhotoPath);
//                        Bitmap bitmap;
//                        if (Build.VERSION.SDK_INT >= 29) {
//                            ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), Uri.fromFile(file));
//                            try {
//                                bitmap = ImageDecoder.decodeBitmap(source);
//                                if (bitmap != null) {
//                                    iv_photo.setImageBitmap(bitmap);
//                                }
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        else {
//                            try {
//                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
//                                if (bitmap != null) {
//                                    iv_photo.setImageBitmap(bitmap);
//                                }
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                    break;
//                }
//            }
//        } catch (Exception error) {
//            error.printStackTrace();
//        }
//    }
//
//    // 사진 촬영 후 썸네일만 띄워줌. 이미지를 파일로 저장해야 함
//    private File createImageFile() throws IOException {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile( imageFileName, ".jpg", storageDir );
//        mCurrentPhotoPath = image.getAbsolutePath();
//        return image;
//    }
//
//    // 카메라 인텐트 실행하는 부분
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.promiseapp.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
}

