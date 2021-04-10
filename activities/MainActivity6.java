package com.p17107.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity6 extends AppCompatActivity {
    EditText editText1,editText2,editText3;
    String userid;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        editText1=findViewById(R.id.editTextTextPersonName9);
        editText2=findViewById(R.id.editTextTextPersonName10);
        editText3=findViewById(R.id.editTextTextPersonName11);
        userid=getIntent().getStringExtra("userid");
        pref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editText1.setText(pref.getString(userid+"firstname","usernotfound"));
        editText2.setText(pref.getString(userid+"lastname","usernotfound"));
        editText3.setText(pref.getString(userid+"address","addressnotfound"));
    }
    //where the user's personal data can be updated and saved.
    public  void update_personal_data(View view){
        String firstname=editText1.getText().toString();
        String lastname=editText2.getText().toString();
        String address=editText3.getText().toString();
        if(!firstname.isEmpty()&&!lastname.isEmpty()&&!address.isEmpty()){
        SharedPreferences.Editor editor=pref.edit();
        editor.putString(userid+"firstname",firstname);
        editor.putString(userid+"lastname",lastname);
        editor.putString(userid+"address",address);
        editor.apply();
            Toast.makeText(this, "Επιτυχής ανανέωση στοιείων!", Toast.LENGTH_SHORT).show();}
        else{
            Toast.makeText(this, "Παρακαλώ εισάγετε όλα τα απαραίτητα πεδία!", Toast.LENGTH_SHORT).show();
        }
    }
}