package com.p17107.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText editText1,editText2;
    private FirebaseAuth myAuth;
    FirebaseUser currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1=findViewById(R.id.editTextTextPersonName);
        editText2=findViewById(R.id.editTextTextPassword);
        myAuth = FirebaseAuth.getInstance();
    }

    public void signin(View view){//Firebase authentication
        if(editText2.getText().toString().isEmpty()){ Toast.makeText(this, "Παρακαλώ εισάγετε όλα τα απαραίτητα πεδία προκειμένου να συνδεθείτε.", Toast.LENGTH_SHORT).show();}
        else{
        myAuth.signInWithEmailAndPassword(editText1.getText().toString(),editText2.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            currUser=myAuth.getCurrentUser();
                            Intent intent=new Intent(getApplicationContext(),MainActivity3.class);
                            intent.putExtra("userid",currUser.getUid());

                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();}
                    }
                });}
    }

    public void signup(View view){
        Intent intent=new Intent(getApplicationContext(),MainActivity2.class);
        startActivity(intent);

    }
}