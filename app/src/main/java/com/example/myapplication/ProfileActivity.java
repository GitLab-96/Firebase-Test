package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    private Button saveDataButtn,showButtn;
    private EditText nameET,ageET;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        saveDataButtn = findViewById(R.id.saveButttonID);
        showButtn= findViewById(R.id.showButttonID);
        nameET = findViewById(R.id.nameEditTextID);
        ageET = findViewById(R.id.ageEditTextID);

        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Student");


        saveDataButtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });


        showButtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProfileActivity.this,DetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveData() {

        String name = nameET.getText().toString().trim();
        String age = ageET.getText().toString().trim();

        String key = databaseReference.push().getKey();

        Student student = new Student(name,age);

        databaseReference.child(key).setValue(student).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ProfileActivity.this, "Succesfully Done", Toast.LENGTH_SHORT).show();
            }
        });



        nameET.setText("");
        ageET.setText("");



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.signOutMenuId){
            FirebaseAuth.getInstance().signOut();
            finish();

            Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}