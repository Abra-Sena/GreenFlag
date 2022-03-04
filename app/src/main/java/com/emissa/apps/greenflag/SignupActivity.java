package com.emissa.apps.greenflag;

import static com.emissa.apps.greenflag.ApplicationUtils.updateUI;

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
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity implements TextWatcher {
// check this: https://www.journaldev.com/9412/android-shared-preferences-example-tutorial
// check this: https://developer.android.com/codelabs/advanced-android-kotlin-training-login#3


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

        // Set listener to validate email and password
        etEmailAddress.addTextChangedListener(this);
        etPassword.addTextChangedListener(this);

        // Set click listener to registration button 'next' and proceed to validations
        btnNext.setOnClickListener(view -> {
            Log.d(LOG_TAG, "Click on button 'next'");
            String userEmail = etEmailAddress.getText().toString();
            String userPass = etPassword.getText().toString();
            String passRepeated = etEtPasswordCheck.getText().toString();

            if (passRepeated.equals(userPass)){
                Toast.makeText(SignupActivity.this, "Password matched", Toast.LENGTH_LONG).show();
                // Enable button when email & password match the required format of the app
                btnNext.setEnabled(true);
                Log.d(LOG_TAG, "Button 'next' activated!");

                // If the new account is created, the user is also signed in.
                mAuth.createUserWithEmailAndPassword(userEmail, userPass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with current user's information
                                Log.d(LOG_TAG, "Success on account creation");
                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user, LOG_TAG);
                                startActivity(intent);
                            } else {
                                // Sign in fails, display error message to User
                                Log.w(LOG_TAG, "Failure on account creation", task.getException());
                                Toast.makeText(SignupActivity.this,
                                        "Authentication failed.",
                                        Toast.LENGTH_LONG
                                ).show();
                                updateUI(null, LOG_TAG);
                            }
                        }
                    });
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Checking if user is already signed in to update UI accordingly
        FirebaseUser currentUSer = mAuth.getCurrentUser();
        if (currentUSer != null) currentUSer.reload();
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        int length = editable.toString().length();
        // Enable 'Next' button if passwords match and have the correct length
        // Show corresponding error message otherwise
        if (etPassword.hasFocus()) {
            btnNext.setEnabled(length >= 8);
        }
    }
}