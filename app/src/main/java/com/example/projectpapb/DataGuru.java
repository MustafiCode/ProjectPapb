package com.example.projectpapb;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataGuru extends AppCompatActivity {
    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //firebase database reference object
    DatabaseReference databaseGuru;

    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout, buttonAdd, btnGuru;
    private EditText editNip,editNama;
    private RecyclerView recyclerView;


    private GuruAdapter guruAdapter;
    private ArrayList<Guru> guruList = new ArrayList<Guru>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hlm_pelajar);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //getting the reference of Dosen node
        databaseGuru = FirebaseDatabase.getInstance().getReference("guru");

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(this, MainActivity.class));
            finish();

        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        buttonLogout = (Button) findViewById(R.id.logout);
        btnGuru = (Button) findViewById(R.id.hlm_guru);
//        editNama =(EditText) findViewById(R.id.edit_nama);
//        editNip =(EditText) findViewById(R.id.edit_nip);
//        buttonAdd = (Button) findViewById(R.id.btn_add);

        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        guruAdapter = new GuruAdapter(this);

        //displaying logged in user name
        textViewUserEmail.setText("Welcome "+user.getDisplayName()+" "+user.getEmail());

        //adding listener to button
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logging out the user
                firebaseAuth.signOut();
                //closing activity
                finish();
                //starting login activity
                startActivity(new Intent(DataGuru.this, MainActivity.class));
            }
        });

        btnGuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hlmGuru = new Intent(DataGuru.this, HlmGuru.class);
                startActivity(hlmGuru);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertDosen();
            }
        });



        //attaching value event listener
        databaseGuru.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous dosen list
                guruList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting dosen
                    Guru guru = postSnapshot.getValue(Guru.class);
                    //adding dosen to the list
                    guruList.add(guru);
                }

                //creating adapter
                guruAdapter.addItem(guruList);

                //attaching adapter to the recyclerview
                recyclerView.setAdapter(guruAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void insertDosen() {
        //getting the values to save
        String nip = editNip.getText().toString();
        String name = editNama.getText().toString();

        //checking if the value is provided
        if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(nip)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Dosen
            //String id = databaseDosen.push().getKey();

            //creating an dosen Object
            Guru dosen = new Guru(nip, name);

            //Saving the Dosen
            databaseGuru.child(nip).setValue(dosen);

            //setting edittext to blank again
            editNama.setText("");
            editNip.setText("");


            //displaying a success toast
            Toast.makeText(this, "Dosen added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name and nip", Toast.LENGTH_LONG).show();
        }
    }
}
