package com.p17107.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity5 extends AppCompatActivity {
    TextView textViewew19;
    SQLiteDatabase db;
    Cursor cursor;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        textViewew19=findViewById(R.id.textView19);
        userid=getIntent().getStringExtra("userid");

        db=openOrCreateDatabase("movement_listdb", Context.MODE_PRIVATE,null);


        display_movement_list();
    }
    @Override//in order to view the changes made by the user in the case the user modified the movements codes
    protected void onResume() {
        super.onResume();
       display_movement_list();
    }
    //where the user can add a code
    public void add_messagelist(View view){
        Intent intent=new Intent(getApplicationContext(),MainActivity8.class);
        intent.putExtra("userid",userid);
        startActivity(intent);
    }
    //where the user can edit a code
    public void edit_messagelist(View view){
        Intent intent=new Intent(getApplicationContext(),MainActivity7.class);
        intent.putExtra("userid",userid);
        startActivity(intent);
    }
    //it displays the user's personalized movement codes list.
    public void display_movement_list(){
        cursor=db.rawQuery("SELECT rowid,movement_reasons FROM "+"m"+userid,null);
        if(cursor.getCount()>0){// BUILDING OF THE DATASTRING
            StringBuilder builder=new StringBuilder();
            while (cursor.moveToNext()){
                builder.append(cursor.getString(0)).append(".").append("\n");
                builder.append(cursor.getString(1)).append("\n");

            textViewew19.setText(builder.toString());
        }


    }
}}