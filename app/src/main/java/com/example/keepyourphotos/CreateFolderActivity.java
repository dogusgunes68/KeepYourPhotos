package com.example.keepyourphotos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CreateFolderActivity extends AppCompatActivity {

    EditText folderNameText;
    Button createButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_folder);

         folderNameText = findViewById(R.id.folderNameText);
         createButton = findViewById(R.id.createButton);


    }

    public void createClicked(View view){

        String folderName = folderNameText.getText().toString();

        uploadData(folderName);

        Intent intent = new Intent(CreateFolderActivity.this, FolderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }







    public void uploadData(String folderName){

        try {

            SQLiteDatabase database = this.openOrCreateDatabase("Folders",MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS folders(name VARCHAR)");

            String sqlString = "INSERT INTO folders (name) VALUES (?) ";
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);

            sqLiteStatement.bindString(1,folderName);
            sqLiteStatement.execute();

        }catch (Exception e){

            Toast.makeText(CreateFolderActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();

        }

    }
}