package com.emissa.apps.greenflag;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.emissa.apps.greenflag.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding mainBinding;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private TextView userData;
    private Button createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        userData = mainBinding.tvUserInfo;
        createAccount = mainBinding.btnCreateAccount;

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "Create button clicked!");
                Intent intent = new Intent(getBaseContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser != null) {
            currentUser.reload();
            userData.setText(getString(R.string.current_user) + currentUser.getEmail());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login_icon:
                gotoLoginActivity();
                break;
            case R.id.logout_icon:
                logOut();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logOut() {
        Log.d(LOG_TAG, "Click on logout icon from menu!");
        mAuth.signOut();
        Log.d(LOG_TAG, "User successfully logged out!");
        // Set text view of current user info to invisible
        userData.setVisibility(View.INVISIBLE);
    }

    private void gotoLoginActivity() {
        Log.d(LOG_TAG, "Click on login icon from menu!");
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
    }
}