package com.example.promiseapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
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
import com.google.firebase.ml.modeldownloader.CustomModel;
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions;
import com.google.firebase.ml.modeldownloader.DownloadType;
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;

import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CameraActivity extends AppCompatActivity {
    final private static String TAG = "GILBOMI";
    Button btn_photo, enterbutton;
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

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        btn_photo = (Button) findViewById(R.id.btn_photo);
        enterbutton = (Button) findViewById(R.id.enterbutton);
        imagephoto = (ImageView) findViewById(R.id.image);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "?????? ?????? ??????");
            } else {
                Log.d(TAG, "?????? ?????? ??????");
                ActivityCompat.requestPermissions(CameraActivity.this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        System.out.println("---------------------------?????? ???????????? ??????!");


        CustomModelDownloadConditions conditions = new CustomModelDownloadConditions.Builder()
                .requireWifi()  // Also possible: .requireCharging() and .requireDeviceIdle()
                .build();
        FirebaseModelDownloader.getInstance()
                .getModel("your_model", DownloadType.LOCAL_MODEL, conditions)
                .addOnSuccessListener(new OnSuccessListener<CustomModel>() {
                    @Override
                    public void onSuccess(CustomModel model) {
                        // Download complete. Depending on your app, you could enable the ML
                        // feature, or switch from the local model to the remote model, etc.

                        // The CustomModel object contains the local path of the model file,
                        // which you can use to instantiate a TensorFlow Lite interpreter.
                        File modelFile = model.getFile();

                        if (modelFile != null) {
                            Interpreter interpreter = new Interpreter(modelFile);
                            Bitmap tbitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);
                            ByteBuffer input = ByteBuffer.allocateDirect(224 * 224 * 3 * 4).order(ByteOrder.nativeOrder());
                            for (int y = 0; y < 224; y++) {
                                for (int x = 0; x < 224; x++) {
                                    int px = tbitmap.getPixel(x, y);

                                    // Get channel values from the pixel value.
                                    int r = Color.red(px);
                                    int g = Color.green(px);
                                    int b = Color.blue(px);

                                    // Normalize channel values to [-1.0, 1.0]. This requirement depends
                                    // on the model. For example, some models might require values to be
                                    // normalized to the range [0.0, 1.0] instead.
                                    float rf = (r - 127) / 255.0f;
                                    float gf = (g - 127) / 255.0f;
                                    float bf = (b - 127) / 255.0f;

                                    input.putFloat(rf);
                                    input.putFloat(gf);
                                    input.putFloat(bf);

                                    int bufferSize = 1000 * java.lang.Float.SIZE / java.lang.Byte.SIZE;
                                    ByteBuffer modelOutput = ByteBuffer.allocateDirect(bufferSize).order(ByteOrder.nativeOrder());

                                    interpreter.run(input, modelOutput);
                                    modelOutput.rewind();

                                    FloatBuffer probabilities = modelOutput.asFloatBuffer();
                                    try {
                                        BufferedReader reader = new BufferedReader(
                                                new InputStreamReader(getAssets().open("custom_labels.txt")));
                                        for (int i = 0; i < probabilities.capacity(); i++) {
                                            String label = reader.readLine();
                                            float probability = probabilities.get(i);
                                            Log.i(TAG, String.format("%s: %1.4f", label, probability));
                                        }
                                    } catch (IOException e) {
                                        // File not found?
                                    }
                                }
                            }
                        }
                    }

                });

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
                runTextRecognition();

                Intent intent = new Intent(getApplicationContext(), explainScreen.class);
                intent.putExtra("howtopage",2);
                intent.putExtra("inputName",name);
                startActivity(intent);
            }
        });
    }

    // ?????? ??????
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }

    //????????? ?????????
    //?????? -> dispatch(createimagefile) -> onActivity -> text recog
    //onRequestPermissionsResult ????????? ??????

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
//    // ?????? ?????? ??? ???????????? ?????????. ???????????? ????????? ???????????? ???
//    private File createImageFile() throws IOException {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile( imageFileName, ".jpg", storageDir );
//        mCurrentPhotoPath = image.getAbsolutePath();
//        return image;
//    }

    public void dispatchTakePictureIntent() {
        System.out.println("???????????? ??????");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        System.out.println("????????? ??????");
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            System.out.println("????????? ?????????");
            File photoFile = null;
            System.out.println("?????? ?????? ??????");
            try {
                System.out.println("?????? ?????? ??????");
                photoFile = createImageFile();
                System.out.println("?????? ?????? ??????");
            } catch (IOException ex) {
                System.out.println("?????? ??????");
            }
            if (photoFile != null) {
                System.out.println("uri ?????? ??????");
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.promiseapp.fileprovider", photoFile);
                System.out.println("uri ?????? ??????");
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                System.out.println("?????? ????????? ???????????? ??????");
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                System.out.println("?????? ?????? ???????????? ???");
            }
            System.out.println("?????? ??????");
        }
    }


    private void runTextRecognition() {
        InputImage image = InputImage.fromBitmap(bitmap, 0);
        imagephoto.setImageBitmap(bitmap);
        System.out.println("????????? ?????? ??????");
        TextRecognizer recognizer = TextRecognition.getClient();
        recognizer.process(image)
                .addOnSuccessListener(
                        new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text texts) {
                                processTextRecognitionResult(texts);
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

    public void processTextRecognitionResult(Text texts) {
        List<Text.TextBlock> blocks = texts.getTextBlocks();
        if (blocks.size() == 0) {
            name="???????????? ???";
            return;
        }
        for (int i = 0; i < blocks.size(); i++) {
            System.out.println("????????? ?????? :"+ i + "??????" +blocks.get(i).getText());
            name=blocks.get(i).getText();

        }
        name = blocks.get(0).getText();
        System.out.println("~?????????: "+name);
    }
}


//
//    // ???????????? ????????? ????????? ???????????? ??????
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
//                                    runTextRecognition();
//                                }
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        else {
//                            try {
//                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
//                                if (bitmap != null) {
//                                    runTextRecognition();
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
//    // ?????? ?????? ??? ???????????? ?????????. ???????????? ????????? ???????????? ???
//    private File createImageFile() throws IOException {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile( imageFileName, ".jpg", storageDir );
//        mCurrentPhotoPath = image.getAbsolutePath();
//        return image;
//    }
//
//    // ????????? ????????? ???????????? ??????
//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//
//            }
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this, "com.example.promiseapp.fileprovider", photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//            }
//        }
//    }
//}

