package com.example.keepyourphotos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FolderActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> folderNameList;
    ListViewAdapter listViewAdapter;
    SQLiteDatabase database;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_layout,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.add_folder_menu){

            Intent intent = new Intent(FolderActivity.this,CreateFolderActivity.class);
            intent.putExtra("activityType","folder");
            startActivity(intent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);

        listView = findViewById(R.id.listView);

        folderNameList = new ArrayList<>();

        listViewAdapter = new ListViewAdapter(folderNameList,FolderActivity.this);

        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(FolderActivity.this,PhotoActivity.class);
                intent.putExtra("folderId",position+1);
                intent.putExtra("info","olds");
                startActivity(intent);

            }
        });

        getData();


    }

    public void getData(){

            try {

                database = this.openOrCreateDatabase("Folders",MODE_PRIVATE,null);

                    Cursor cursor = database.rawQuery("SELECT * FROM folders", null);

                    int nameIx = cursor.getColumnIndex("name");

                    while (cursor.moveToNext()) {

                        folderNameList.add(cursor.getString(nameIx));

                    }

                    listViewAdapter.notifyDataSetChanged();

                    cursor.close();

            } catch (Exception e) {
              e.printStackTrace();
            }


    }
}