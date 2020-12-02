package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText singInEmail_ET,signInPasswordEt;
    private TextView signUpTextView;
    private Button signInButton;

    ProgressBar signInProgressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        signInProgressBar = findViewById(R.id.signInProgressBarId);
        singInEmail_ET = findViewById(R.id.signIn_emailId);
        signInPasswordEt = findViewById(R.id.signIn_passwordId);
        signUpTextView = findViewById(R.id.signUpTextViewId);
        signInButton = findViewById(R.id.signIn_buttonId);

        signUpTextView.setOnClickListener(this);
        signInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.signIn_buttonId:

                UserLogin();

                break;

            case R.id.signUpTextViewId:

                Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);

                break;


        }

    }

    private void UserLogin() {

        String email = singInEmail_ET.getText().toString().trim();
        String password = signInPasswordEt.getText().toString().trim();

        if (email.isEmpty()){

            singInEmail_ET.setError("Enter an EmailAddress");
            singInEmail_ET.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            singInEmail_ET.setError("Enter a Valid Email");
            singInEmail_ET.requestFocus();
            return;
        }

        if (password.isEmpty()){

            signInPasswordEt.setError("Enter a  Password");
            signInPasswordEt.requestFocus();
            return;
        }
        if (password.length()<6){

            signInPasswordEt.setError("Minimum lenth of a password should be 6");
            signInPasswordEt.requestFocus();
            return;
        }

        signInProgressBar.setVisibility(View.VISIBLE);

      mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {

        signInProgressBar.setVisibility(View.GONE);
        if (task.isSuccessful()){

            fileList();
            Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else {
            Toast.makeText(MainActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
        }


    }
});




    }
}