package com.p17107.project2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {

    String userid;
    SharedPreferences pref;

    private static final int REC_RESULT = 653;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        pref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        userid=getIntent().getStringExtra("userid");

    }
    //where the user can format and send a new message.
    public void new_message(View view){
        Intent intent=new Intent(getApplicationContext(),MainActivity4.class);
        intent.putExtra("userid",userid);
        startActivity(intent);
    }
    //Where the user can view his message list.
    public void view_messagelist(View view){
        Intent intent=new Intent(getApplicationContext(),MainActivity5.class);
        intent.putExtra("userid",userid);
        startActivity(intent);
    }
    //Where the user can view and edit his/her personal data.
    public void view_personaldata(View view){
        Intent intent=new Intent(getApplicationContext(),MainActivity6.class);
        intent.putExtra("userid",userid);

        startActivity(intent);
    }
    //Each function from the above can be activated by speach recognition.
    public void voice_command(View view){//VOICE RECORDING FUNCTION AS SHOWN IN THE CLASS
        Toast.makeText(this, "Παρακαλώ πείτε τον αριθμό λειτρουργίας που επιθυμείτε (1-3).", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Παρακαλώ μιλήστε!");
        startActivityForResult(intent,REC_RESULT);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {//SEARCHING THE USER'S VOICE COMMANDS
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REC_RESULT && resultCode==RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            if (matches.contains("ένα")||matches.contains("1")) {
                Intent intent=new Intent(getApplicationContext(),MainActivity4.class);
                intent.putExtra("userid",userid);

                startActivity(intent);
            }
            else if(matches.contains("δύο")||matches.contains("2")){Intent intent=new Intent(getApplicationContext(),MainActivity5.class);
                intent.putExtra("userid",userid);

                startActivity(intent);}

            else if(matches.contains("τρία")||matches.contains("3")){Intent intent=new Intent(getApplicationContext(),MainActivity6.class);
                intent.putExtra("userid",userid);

                startActivity(intent);}
            else{
            Toast.makeText(this, "Η εντολή δεν αναγνωρίστηκε.Η φωνητική λειτουργία υποστηρίζει τους αριθμούς 1 έώς 3.", Toast.LENGTH_SHORT).show();}

        }
    }









}