package com.example.chadt;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chadt.AuthorizationCode.AuthorizationActivity;
import com.example.chadt.RegistrationCode.RegistrationActivity;


public class MainActivity extends AppCompatActivity {
    private Button authorizationButton;
    private Button registrationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
            getWindow().setStatusBarColor(getResources().getColor(R.color.darkBlue));
        }
        getSupportActionBar().hide();

        authorizationButton = (Button) findViewById(R.id.authorizationButton);
        registrationButton = (Button) findViewById(R.id.registrationButton);


        authorizationButton.setOnClickListener(v -> {
            finish();
            startActivity(new Intent(this, AuthorizationActivity.class));
        });

        registrationButton.setOnClickListener(v -> {
            finish();
            startActivity(new Intent(this, RegistrationActivity.class));
        });

    }

}
