package com.emissa.apps.greenflag;

import static com.emissa.apps.greenflag.MainActivity.mAuth;
import static com.emissa.apps.greenflag.MainActivity.mainBinding;

import android.util.Log;
import android.view.View;

import com.emissa.apps.greenflag.databinding.ActivityMainBinding;

public class ApplicationUtils {
    public static void updateUI(Object object, String tag) {
        Log.d(tag, tag + " calling updateUI.");
        if (object.equals(null)) return;

        String userEmail = mAuth.getCurrentUser().getEmail();
//        mainBinding.tvUserInfo.setVisibility(View.VISIBLE);
        mainBinding.tvUserInfo.setText("Current User: " + userEmail);
        Log.d(tag, tag + ", text setted: " + mainBinding.tvUserInfo.getText().toString());
//        userData.setVisibility(View.VISIBLE);
        Log.d(tag, tag + " ends, UI updated.");
    }
}
