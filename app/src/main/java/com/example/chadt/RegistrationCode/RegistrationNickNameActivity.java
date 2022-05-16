package com.example.chadt.RegistrationCode;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chadt.AuthorizationCode.AuthorizationActivity;
import com.example.chadt.GlobalVariables.GLOBALVALUES;
import com.example.chadt.R;

import java.util.concurrent.ExecutionException;


public class RegistrationNickNameActivity extends AppCompatActivity {
    private EditText nickName;
    private Button signUp;
    private Button Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_nickname_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.white));
        getWindow().setStatusBarColor(getResources().getColor(R.color.background));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            );
            getWindow().setStatusBarColor(0x00000000);
        }
        getSupportActionBar().hide();

        nickName = (EditText) findViewById(R.id.NickName);
        signUp = (Button) findViewById(R.id.registrationButton);
        Back = (Button) findViewById(R.id.BackFromRegNickName);
        signUp.setOnClickListener(v -> {
            if (!nickName.getText().toString().equals("")) {
                GLOBALVALUES.name = nickName.getText().toString();
                try {
                    send();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Back.setOnClickListener(v -> {
            finish();
//            startActivity(new Intent(this, MainActivity.class));
        });
    }

    private void send() throws ExecutionException, InterruptedException {
        RegistrationNickName registrationNickName = new RegistrationNickName();
        registrationNickName.execute(nickName.getText().toString());
        finish();
        startActivity(new Intent(this, AuthorizationActivity.class));
    }
}
