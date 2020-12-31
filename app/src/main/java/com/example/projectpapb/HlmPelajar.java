package com.example.projectpapb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HlmPelajar extends AppCompatActivity implements View.OnClickListener {

    TextView result;
    Button logout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hlm_pelajar);

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);

        Intent dataIntent = getIntent();
        String data = dataIntent.getStringExtra("email");
        result = findViewById(R.id.header);
        result.setText(data);
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View v) {
        FirebaseAuth.getInstance().signOut();
        this.finish();
    }
}
