package com.example.projectpapb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseUser currentuser;
    Button login;
    TextView register;
    EditText edtEmail, edtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.login);
        edtEmail = findViewById(R.id.editemail);
        edtPass = findViewById(R.id.editpass);
        register = findViewById(R.id.register1);

//        list<AuthUI.IdpConfig> profiders = Arrays.asList(
//                new AuthUI.IdpConfig.EmailBuilder().build(),
//                new AuthUI.IdpConfig.GoogleBuilder().build(),
//                new AuthUI.IdpConfig.PhoneBuilder().build()
//        );

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("nama");
        currentuser = mAuth.getCurrentUser();
        if (currentuser != null) {
            if (currentuser.isEmailVerified()) {
                Intent hlm = new Intent(this, HlmPelajar.class);
                hlm.putExtra("email", currentuser.getEmail());
                startActivity(hlm);
            }
        }

        register.setOnClickListener(v -> {
            Intent regist = new Intent(MainActivity.this, Register.class);
            startActivity(regist);
        });

        login.setOnClickListener(v -> {
            if (edtEmail.getText().toString().equals("")) {
                Toast.makeText(MainActivity.this, "harus isi email dulu", Toast.LENGTH_SHORT).show();
            } else if (edtPass.getText().toString().equals("")) {
                Toast.makeText(MainActivity.this, "harus isi pass dulu", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(MainActivity.this, "test", Toast.LENGTH_SHORT).show();
//                Log.d("test", "test");
                //myRef.setValue(edtEmail);
                mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtPass.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                // if (user.isEmailVerified()){
                                Intent hlm = new Intent(MainActivity.this, HlmPelajar.class);
                                hlm.putExtra("email", user.getEmail());
                                startActivity(hlm);
                                // } else {
                                //Toast.makeText(MainActivity.this, "Not verified", Toast.LENGTH_LONG).show();
                                // }
                            } else {
                                Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Authentification Failed", Toast.LENGTH_LONG).show();
                        }
                    }

                });
            }
        });
    }

//    @Override
//    public void onClick(View v) {
//        if (v.getId()==R.id.login){
//            Toast.makeText(this,"test", Toast.LENGTH_SHORT).show();
//            mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(),edtPass.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()){
//                        FirebaseUser user = mAuth.getCurrentUser();
//                        if (user!=null){
//                            if (user.isEmailVerified()){
//                                Intent hlm = new Intent(MainActivity.this, HlmPelajar.class);
//                                hlm.putExtra("email", edtEmail.getText().toString());
//                                startActivity(hlm);
//                            } else {
//                                Toast.makeText(MainActivity.this, "Not verified", Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    } else {
//                        Toast.makeText(MainActivity.this, "Authentification Failed", Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
//        } else if (v.getId()==R.id.register1){
//            mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(),edtPass.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()){
//                        FirebaseUser user = mAuth.getCurrentUser();
//                        if (user!=null){
//                            UserProfileChangeRequest profilUpdate;
////                            profilUpdate = new UserProfileChangeRequest().Builder()
////                                    .setDisplayName("User Test")
////                                    .build();
////                            user.updateProfile(profilUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
////                                @Override
////                                public void onComplete(@NonNull Task<Void> task) {
////                                    if (task.isSuccessful()){
////                                        Log.d("gagal update profil", "User profil update");
////                                    }
////                                }
////                            });
//                            if (user.isEmailVerified()){
//                                Intent hlm = new Intent(MainActivity.this, HlmPelajar.class);
//                                hlm.putExtra("email", edtEmail.getText().toString());
//                                startActivity(hlm);
//                            } else {
//                                final String email = user.getEmail();
//                                user.sendEmailVerification().addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()){
//                                            Toast.makeText(MainActivity.this, "Verification sent to"+email,Toast.LENGTH_SHORT).show();
//                                        } else {
//                                            Log.e("Error di verifikasi", "sentEmailVerifikasi", task.getException());
//                                            Toast.makeText(MainActivity.this, "Failed to sent verification email", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//                            }
//                        }
//                    } else {
//                        Toast.makeText(MainActivity.this, "Authentification Failed", Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
//        }
//    }
    }