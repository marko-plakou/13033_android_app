package com.p17107.project2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity4 extends AppCompatActivity implements LocationListener {
    EditText editText1,editText2,editText3,editText4;
    SharedPreferences pref;
    String userid;
    private String phoneNumber="13033";
    private static final int REC_RESULT = 653;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String location_and_timestamp;
    Calendar calendar=Calendar.getInstance();
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        database=FirebaseDatabase.getInstance();
        userid=getIntent().getStringExtra("userid");

        myRef=database.getReference("user:"+userid);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        
        editText1=findViewById(R.id.editTextNumber);
        editText2=findViewById(R.id.editTextTextPersonName3);
        editText3=findViewById(R.id.editTextTextPersonName4);
        editText4=findViewById(R.id.editTextTextPersonName5);
        pref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editText2.setText(pref.getString(userid+"firstname","usernotfound"));
        editText3.setText(pref.getString(userid+"lastname","usernotfound"));
        editText4.setText(pref.getString(userid+"address","addressnotfound"));
    }
    //When the user changes his personal data,and resumes this form they need to reload the data.
    @Override
    protected void onResume() {
        super.onResume();
        editText2.setText(pref.getString(userid+"firstname","usernotfound"));
        editText3.setText(pref.getString(userid+"lastname","usernotfound"));
        editText4.setText(pref.getString(userid+"address","addressnotfound"));

    }
    //it saves the location and timestamp to the firebase realitime databased as shown in the class.
    public void save_location(String location_timestamp){
        myRef.setValue(location_timestamp);


    }
    //Where the user can view again the codes available.
    public void view_messagelist(View view){
        Intent intent=new Intent(getApplicationContext(),MainActivity5.class);
        intent.putExtra("userid",userid);


        startActivity(intent);
    }
    //where the user's personal data can be viewed.
    public void view_personaldata(View view){
        Intent intent=new Intent(getApplicationContext(),MainActivity6.class);
        intent.putExtra("userid",userid);
        startActivity(intent);
    }
 //it calls the send_The_sms function
    public void send_sms_button(View view){
        String choice=editText1.getText().toString();
        send_the_sms(choice);


    }
    //the function used for the actual sms send process.
    public void send_the_sms(String choice){
        String fullname=editText2.getText().toString()+editText3.getText().toString();
        String address=editText4.getText().toString();
        String sms=choice+" "+fullname+" "+address;
    //acquire location permition
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 13);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details
            Toast.makeText(this, "Αναγνώριση τοποθεσίας.", Toast.LENGTH_SHORT).show();

            return; }
    try {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0000, 0, this);
    }catch (Exception q){location_and_timestamp="null";}

    //acquire sms sending permission
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1310);
        }
        else{
            //the procedure for the sms sending.The phonenumber is 13033 and it is default.
            SmsManager manager=SmsManager.getDefault();
            manager.sendTextMessage(phoneNumber,null,sms,null,null);
            Toast.makeText(this,"Το μύνημα μετακίνησης στάλθηκε.",Toast.LENGTH_SHORT).show();
        }
    }
    //the user can choose the movement code within speach recognition as well.
    public void send_voicesms(View view){
        Toast.makeText(this, "Παρακαλώ πείτε τον αριθμό μετακίνησης (1-6) που επιθυμείτε.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Παρακαλώ πείτε τον αριθμό μετακίνησης που επιθυμείτε (1-6)!");
        startActivityForResult(intent,REC_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {//SEARCHING THE USER'S VOICE COMMANDS
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REC_RESULT && resultCode==RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            if (matches.contains("ένα")||matches.contains("1")) {
                send_the_sms("1");

            }
            else if (matches.contains("δύο")||matches.contains("2")) {
                send_the_sms("2");}
            else if (matches.contains("τρία")||matches.contains("3")) {
                send_the_sms("3");}
            else if (matches.contains("τέσσερα")||matches.contains("4")) {
                send_the_sms("4");}
            else if (matches.contains("πέντε")||matches.contains("5")) {
                send_the_sms("5");}
            else if (matches.contains("έξι")||matches.contains("6")) {
                send_the_sms("6");}


            else{
                Toast.makeText(this, "Ο κωδικός δεν αναγνωρίστηκε.Η λειτουργία υποστηρίζει μόνο τους κωδικούς 1 εώς 6.", Toast.LENGTH_SHORT).show();}

        }}



    @Override
    public void onLocationChanged(@NonNull Location location) {

        double  lat=location.getLatitude();
        double longt=location.getLongitude();
        String timestamp=sdf.format(calendar.getTime());
        location_and_timestamp= " latitude:"+String.valueOf(lat)+" longtitude:"+String.valueOf(longt)+
                " timestamp:"+timestamp;
        save_location(location_and_timestamp);
        try{locationManager.removeUpdates(this);}catch (Exception k){}


    }
}