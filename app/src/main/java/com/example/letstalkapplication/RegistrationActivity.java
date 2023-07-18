package com.example.letstalkapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    TextView signin;
    AppCompatButton signup;
    EditText edt_email,edt_pass,edt_confirm,edt_name;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    String username;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    SharedPreferences sharedPreferences;

    public static final String fileName = "login";
    public static final String Email = "email";
    public static final String Password = "password";
    public static final String Name = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup_btn);
        edt_email = findViewById(R.id.edt_mail);
        edt_pass = findViewById(R.id.edt_pass);
        edt_confirm = findViewById(R.id.edt_confirm_pass);
        edt_name = findViewById(R.id.edt_name);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuth();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
            }
        });
    }

    private void performAuth() {
        String fullName = edt_name.getText().toString();
        String email = edt_email.getText().toString();
        String password = edt_pass.getText().toString();
        String confirmPassword = edt_confirm.getText().toString();

        if(!email.matches(emailPattern)){
            edt_email.setError("Enter correct email!");
        }else if(password.isEmpty()||password.length()<6){
            edt_pass.setError("Enter proper password!");
        }else if(!password.equals(confirmPassword)){
            edt_confirm.setError("Password not matched!");
        }else{
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>(){

                @Override
                public void onSuccess(AuthResult authResult) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Email,email);
                    editor.putString(Password,password);
                    editor.putString(Name,fullName);
                    editor.apply();
                    String userID = firebaseAuth.getCurrentUser().getUid();
                    addUserToDatabase(fullName,email,firebaseAuth.getUid());
                    sendUserToNextActivity();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegistrationActivity.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                }
            });
    }
}
    private void addUserToDatabase(String username, String mail, String uid){
        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference();
        dRef.child("user").child(uid).child("name").setValue(username);
        dRef.child("user").child(uid).child("email").setValue(mail);
        dRef.child("user").child(uid).child("uid").setValue(uid);
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(RegistrationActivity.this,MainActivity.class);
        intent.putExtra("name",username);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finishAffinity();
    }
}