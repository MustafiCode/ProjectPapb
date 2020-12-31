package com.example.projectpapb;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText mFullname, mEmail, mPassword, mUlangipassword;
    Button mRegisterBtn;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ProgressBar progressBar;
    TextView Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mFullname = findViewById(R.id.fullname);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mUlangipassword = findViewById(R.id.Ulangipassword);
        mRegisterBtn = findViewById(R.id.button);
        Login = findViewById(R.id.txtLogin);

        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("pelajar");
        progressBar = findViewById(R.id.progressBar);

        Login.setOnClickListener(v -> {
            Intent login = new Intent(Register.this, MainActivity.class);
            startActivity(login);
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email salah");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Password salah");
                    return;
                }
                if(password.length() < 8){
                    mPassword.setError("Password harus >= 8 Characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                if (fAuth.getCurrentUser() != null){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }


                // Get
                String nama = mFullname.getText().toString();
                String eemail = mEmail.getText().toString();
                String pass = mPassword.getText().toString();
                String uPass = mUlangipassword.getText().toString();
                Guru dataguru = new Guru(nama, eemail, pass, uPass);
                myRef.setValue(dataguru);
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this, "user create.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else {
                            Toast.makeText(Register.this, "Error !"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
