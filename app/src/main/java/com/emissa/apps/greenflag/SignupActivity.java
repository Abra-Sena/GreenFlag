package com.emissa.apps.greenflag;

import static com.emissa.apps.greenflag.Validator.isPassValid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    public static final String LOG_TAG = SignupActivity.class.getSimpleName();

    private FirebaseAuth mAuth;
    private EditText etEmailAddress;
    private EditText etPassword;
    private EditText etEtPasswordCheck;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initializing Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Initializing all views
        etEmailAddress = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etEtPasswordCheck = findViewById(R.id.et_password_repeat);
        btnNext = findViewById(R.id.btn_next);


        // Set click listener to registration button 'next' and proceed to validations
        btnNext.setOnClickListener(view -> {
            // Retrieve data user entered
            String userEmail = etEmailAddress.getText().toString();
            String userPass = etPassword.getText().toString();
            String passRepeated = etEtPasswordCheck.getText().toString();

            boolean passwordValid = isPassValid(userPass);

            // Enable 'Next' button if passwords match and have the correct length
            // Show corresponding error message otherwise
            if (passwordValid) {
                if (passRepeated.equals(userPass)) {
                    Toast.makeText(SignupActivity.this,
                            "Password matched", Toast.LENGTH_LONG).show();
                    Log.d(LOG_TAG, "Click on button 'next'");
                    btnNext.setEnabled(true);
                } else {
                    // Passwords do not match
                    etEtPasswordCheck.setError("The Passwords do  not match!");
                }
            } else {
                // Password do not match pattern
                etPassword.setError("Please see below the required format for the password to create!");
            }

            // If the new account is created, the user is also signed in.
            mAuth.createUserWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with current user's information
                            Log.d(LOG_TAG, "Success on account creation");
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            // Sign in fails, display error message to User
                            Log.w(LOG_TAG, "Failure on account creation", task.getException());
                            Toast.makeText(SignupActivity.this,
                                    "Authentication failed.",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
                });
        });
    }
}