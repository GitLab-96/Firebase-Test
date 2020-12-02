package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText singUpEmail_ET,signUpPasswordEt;
    private TextView signInTextView;
    private Button signUpButton;

    private FirebaseAuth mAuth;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.setTitle("Sign Up Activity");

        mAuth = FirebaseAuth.getInstance();




        progressBar = findViewById(R.id.signUpprogressBarId);
        singUpEmail_ET = findViewById(R.id.signUp_emailId);
        signUpPasswordEt = findViewById(R.id.signUp_passwordId);
        signInTextView = findViewById(R.id.signInTextViewId);
        signUpButton = findViewById(R.id.signup_buttonId);

        signInTextView.setOnClickListener(this);
        signUpButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup_buttonId:

                userRegister();

                break;

            case R.id.signInTextViewId:

                Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(intent);

                break;


        }
    }

    private void userRegister() {

        String email = singUpEmail_ET.getText().toString().trim();
        String password = signUpPasswordEt.getText().toString().trim();

        if (email.isEmpty()){

            singUpEmail_ET.setError("Enter an EmailAddress");
            singUpEmail_ET.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            singUpEmail_ET.setError("Enter a Valid Email");
            singUpEmail_ET.requestFocus();
            return;
        }

        if (password.isEmpty()){

            signUpPasswordEt.setError("Enter a  Password");
            singUpEmail_ET.requestFocus();
            return;
        }
        if (password.length()<6){

            signUpPasswordEt.setError("Minimum lenth of a password should be 6");
            singUpEmail_ET.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()) {

                            fileList();
                            Intent intent = new Intent(SignUpActivity.this,ProfileActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){

                                Toast.makeText(SignUpActivity.this, "User is Already register",
                                        Toast.LENGTH_SHORT).show();

                            }else {

                                Toast.makeText(SignUpActivity.this, "Error: "+task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();

                            }
                            // If sign in fails, display a message to the user.

                        }

                        // ...
                    }
                });

    }
}