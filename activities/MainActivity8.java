package com.p17107.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity8 extends AppCompatActivity {
    EditText editText1;
    String userid;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        editText1=findViewById(R.id.editTextTextMultiLine2);
        userid=getIntent().getStringExtra("userid");
        db=openOrCreateDatabase("movement_listdb", Context.MODE_PRIVATE,null);
    }
    //the user can add a new code as a text.The code number is serially increased automatically and the addition is inserted into the database.
    public void add_code(View view){
        db.execSQL("INSERT INTO "+"m"+userid+" (movement_reasons)VALUES('"+editText1.getText().toString()+"')");
        Toast.makeText(this, "Επιτυχής προσθήκη.", Toast.LENGTH_SHORT).show();

    }
}