package com.p17107.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity2 extends AppCompatActivity {
    EditText editText1,editText2,editText3,editText4,editText5;
    private FirebaseAuth mAuth;
    private  FirebaseUser new_user;
    SharedPreferences pref;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editText1=findViewById(R.id.editTextTextPersonName2);//email
        editText2=findViewById(R.id.editTextTextPassword3);//password
        editText3=findViewById(R.id.editTextTextPersonName6);//firstname
        editText4=findViewById(R.id.editTextTextPersonName7);//lastname
        editText5=findViewById(R.id.editTextTextPersonName8);//address
        mAuth = FirebaseAuth.getInstance();
        pref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



    }
    //The firebase sign up
    //While the user is signing up ,the user is required to input his personal data such as his firstname
    //lastaname and address.I use these data in order to create the specific sms formation as it suggested by the government
    //and to avoid the constant input by the user.
    //These personal data are stored to a unique shared preference named the data+ the unique userid
    //An approach that in the case of an official realise i will definitely avoid due to security reasons.
    public void signup(View view){
        if(editText1.getText().toString().isEmpty()||editText2.getText().toString().isEmpty()||editText3.getText().toString().isEmpty()
                ||editText4.getText().toString().isEmpty()||editText5.getText().toString().isEmpty()){
            Toast.makeText(this, "Παρακαλώ εισάγετε όλα τα απαραίτητα πεδία προκειμένου να εγγραφείτε.", Toast.LENGTH_SHORT).show();
        }
        else{
        mAuth.createUserWithEmailAndPassword(editText1.getText().toString(), editText2.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            new_user = mAuth.getCurrentUser();
                            create_users_list(new_user.getUid());
                            String userid=new_user.getUid();
                            String firstname=editText3.getText().toString();
                            String lastname=editText4.getText().toString();
                            String address=editText5.getText().toString();
                            SharedPreferences.Editor editor=pref.edit();
                            editor.putString(userid+"firstname",firstname);
                            editor.putString(userid+"lastname",lastname);
                            editor.putString(userid+"address",address);
                            editor.apply();
                            Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                            intent.putExtra("userid", new_user.getUid());

                            startActivity(intent);


                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                }
                );}}


                    public void create_users_list(String userid){
        //For each new registration,a table for the specific user is created named "m"+ the unique user id +movement_reasons
        //in order to store his personalized movement reasons and codes.This table is initialized with the default codes
                        // The "m" is added in the rare case when a user id begins with an integer which is unacceptable by sqlite.
                        db=openOrCreateDatabase("movement_listdb", Context.MODE_PRIVATE,null);
                        db.execSQL("CREATE TABLE IF NOT EXISTS "+"m"+userid+" (movement_reasons TEXT)");
                        db.execSQL("INSERT INTO "+"m"+userid+" (movement_reasons)VALUES('Μετάβαση σε φαρμακείο ή επίσκεψη στον γιατρό, εφόσον αυτό συνιστάται μετά από σχετική επικοινωνία.')");
                        db.execSQL("INSERT INTO "+"m"+userid+" (movement_reasons)VALUES('Μετάβαση σε εν λειτουργία κατάστημα προμηθειών αγαθών πρώτης ανάγκης (σούπερ μάρκετ, μίνι μάρκετ, συνεργείο, ΚΤΕΟ), όπου δεν είναι δυνατή η αποστολή τους.')");
                        db.execSQL("INSERT INTO "+"m"+userid+" (movement_reasons)VALUES('Μετάβαση σε δημόσια υπηρεσία ή τράπεζα στο μέτρο που δεν είναι δυνατή η ηλεκτρονική συναλλαγή.')");
                        db.execSQL("INSERT INTO "+"m"+userid+" (movement_reasons)VALUES('Κίνηση για παροχή βοήθειας σε ανθρώπους που βρίσκονται σε ανάγκη ή συνοδεία ανηλίκων μαθητών από/προς το σχολείο.')");
                        db.execSQL("INSERT INTO "+"m"+userid+" (movement_reasons)VALUES('Μετάβαση σε τελετή κηδείας υπό τους όρους που προβλέπει ο νόμος ή μετάβαση διαζευγμένων γονέων ή γονέων που τελούν σε διάσταση που είναι αναγκαία για τη διασφάλιση της επικοινωνίας γονέων και τέκνων, σύμφωνα με τις κείμενες διατάξεις.')");
                        db.execSQL("INSERT INTO "+"m"+userid+" (movement_reasons)VALUES('Σωματική άσκηση σε εξωτερικό χώρο ή κίνηση με κατοικίδιο ζώο, ατομικά ή ανά δύο άτομα, τηρώντας στην τελευταία αυτή περίπτωση την αναγκαία απόσταση 1,5 μέτρου.')");


                    }






}
