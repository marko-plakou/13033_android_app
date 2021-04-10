package com.p17107.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity7 extends AppCompatActivity {
    EditText editText1,editText2;
    String userid;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        editText1=findViewById(R.id.editTextNumber2);
        editText2=findViewById(R.id.editTextTextMultiLine);
        userid=getIntent().getStringExtra("userid");
        db=openOrCreateDatabase("movement_listdb", Context.MODE_PRIVATE,null);
    }
    //the user is inputing the code number that it will be modified and the text of this choice is displayd in order for the user to modified it.
    public void code_choice(View view){
        cursor=db.rawQuery("SELECT movement_reasons FROM "+"m"+userid+" WHERE rowid="+editText1.getText().toString(),null);
        if(cursor.getCount()>0) {// BUILDING OF THE DATASTRING
            StringBuilder builder = new StringBuilder();
            while (cursor.moveToNext()) {
                builder.append(cursor.getString(0)).append(".").append("\n");
                editText2.setText(builder.toString());


            }
        }
        else{ Toast.makeText(this, "Η κωδικός δεν υπάρχει.", Toast.LENGTH_SHORT).show();}
    }
    // the movement list is updated including the user's modifications.
            public void code_update (View view){
        String choice=editText1.getText().toString();

                ContentValues cv=new ContentValues();
                cv.put("movement_reasons",editText2.getText().toString());

                db.update("m"+userid,cv,"rowid= ? ",new String[]{choice});
                Toast.makeText(this, "Επιτυχής διόρθωση.", Toast.LENGTH_SHORT).show();
            }
        }