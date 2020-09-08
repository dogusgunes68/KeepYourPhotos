package com.example.keepyourphotos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddPhotoActivity extends AppCompatActivity {

    Bitmap selectedImage;
    SQLiteDatabase database;

    EditText explainText;
    ImageView imageView;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        imageView = findViewById(R.id.galleryImage);
        explainText = findViewById(R.id.explainText);
        addButton = findViewById(R.id.button);


    }

    public void addClicked(View view){

        String explain = explainText.getText().toString();

        Bitmap smallImage = makeSmallerImage(selectedImage,300);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        smallImage.compress(Bitmap.CompressFormat.PNG,50,outputStream);
        byte[] byteArray = outputStream.toByteArray();

        Intent intent = getIntent();
        int folderId = intent.getIntExtra("id",1);

        try {

            database = this.openOrCreateDatabase("Photos",MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS photos(folderid INTEGER, image BLOB, comment VARCHAR)");

            String sqlString = "INSERT INTO photos(folderid,image,comment) VALUES (?,?,?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
            sqLiteStatement.bindLong(1,folderId);
            sqLiteStatement.bindBlob(2,byteArray);
            sqLiteStatement.bindString(3,explain);
            sqLiteStatement.execute();


        }catch (Exception e){

            e.printStackTrace();

        }

        Intent intentToPhotoActivity = new Intent(AddPhotoActivity.this,PhotoActivity.class);
        intentToPhotoActivity.putExtra("info",String.valueOf(folderId));
        startActivity(intentToPhotoActivity);

    }
    public Bitmap makeSmallerImage(Bitmap image, int maximumSize){

        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;

        if (bitmapRatio > 1){

            width = maximumSize;
            height = (int)(width / bitmapRatio);

        }else {

            height = maximumSize;
            width = (int)(height * bitmapRatio);

        }

        return Bitmap.createScaledBitmap(image,width,height,true);

    }
    public void selectImage(View view){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else {

            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery,2);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                Intent intentToGallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery,2);

            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 2 && resultCode == RESULT_OK && data != null){

            Uri imageData = data.getData();

            try {

                if (Build.VERSION.SDK_INT >= 28) {

                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(),imageData);
                    selectedImage = ImageDecoder.decodeBitmap(source);
                    imageView.setImageBitmap(selectedImage);

                } else {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageData);
                    imageView.setImageBitmap(selectedImage);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);

    }

}