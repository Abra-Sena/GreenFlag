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

public class LoginActivity extends AppCompatActivity {

    public static final String LOG_TAG = LoginActivity.class.getSimpleName();

    private FirebaseAuth mAuth;
    private EditText etEmailAddress;
    private EditText etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initializing Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Initializing all views
        etEmailAddress = findViewById(R.id.et_email_login);
        etPassword = findViewById(R.id.et_password_login);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(view -> {
            String userEmail = etEmailAddress.getText().toString();
            String userPass = etPassword.getText().toString();
            boolean passwordValid = isPassValid(userPass);
            // Enable 'Next' button if passwords match and have the correct length
            // Show corresponding error message otherwise
            if (passwordValid) {
//                btnLogin.setEnabled(true);
                mAuth.signInWithEmailAndPassword(userEmail, userPass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with current user's information
                                Log.d(LOG_TAG, "Sign in Success!");
                                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                // Sign in fails, display error message to User
                                Log.w(LOG_TAG, "Failure on sign in!", task.getException());
                                Toast.makeText(
                                        getBaseContext(),
                                        "Authentication failed. Something went wrong!",
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                    });
            } else {
                // Password not valid, set error message
                etPassword.setError("Password incorrect!");
            }
        });
    }
}