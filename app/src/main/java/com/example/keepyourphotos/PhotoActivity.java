package com.example.keepyourphotos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class PhotoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SQLiteDatabase database;
    ArrayList<Bitmap> imageArray;
    ArrayList<String> explainArray;
    RecyclerViewAdapter recyclerViewAdapter;
    int folderId;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.photo_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.add_photo_menu){

            Intent intent = new Intent(PhotoActivity.this,AddPhotoActivity.class);
            intent.putExtra("id",folderId);
            intent.putExtra("info","new");
            startActivity(intent);

        }else if (item.getItemId() == R.id.goto_folder_activity){

            Intent intentToFolder = new Intent(PhotoActivity.this,FolderActivity.class);
            intentToFolder.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentToFolder);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        imageArray = new ArrayList<>();
        explainArray = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(imageArray,explainArray);
        recyclerView.setAdapter(recyclerViewAdapter);

        Intent intent = getIntent();
        String info = intent.getStringExtra("info");

       if (info.matches("olds")){
           folderId = intent.getIntExtra("folderId",1);
           getData();
       }else{
           folderId = Integer.parseInt(info);
           getData();
       }

    }

    public void getData(){


        try {

            database = this.openOrCreateDatabase("Photos",MODE_PRIVATE,null);

            Cursor cursor = database.rawQuery("SELECT * FROM photos WHERE folderid = ?", new String[] {String.valueOf(folderId)});
            int imageIx = cursor.getColumnIndex("image");
            int explainIx = cursor.getColumnIndex("comment");

            while (cursor.moveToNext()){

                byte[] bytes = cursor.getBlob(imageIx);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imageArray.add(bitmap);
                explainArray.add(cursor.getString(explainIx));

            }

            recyclerViewAdapter.notifyDataSetChanged();

            cursor.close();

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}