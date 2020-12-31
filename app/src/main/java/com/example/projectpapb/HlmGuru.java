package com.example.projectpapb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HlmGuru extends AppCompatActivity {

    TextView lokasi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_guru);

        lokasi = findViewById(R.id.lokasi);
        lokasi.setOnClickListener(v -> {
            Intent pindah = new Intent(HlmGuru.this, Maps.class);
            startActivity(pindah);
        });
    }
}
