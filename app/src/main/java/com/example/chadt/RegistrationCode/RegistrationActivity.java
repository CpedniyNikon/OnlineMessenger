package com.example.chadt.RegistrationCode;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chadt.AuthorizationCode.AuthorizationActivity;
import com.example.chadt.GlobalVariables.GLOBALVALUES;
import com.example.chadt.MainActivity;
import com.example.chadt.R;
import com.example.chadt.algorithms.SHA256;

import java.util.concurrent.ExecutionException;

public class RegistrationActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private EditText SecondPassword;
    private Button Next;
    private Button Back;

    private Registration registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.white));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            );
            getWindow().setStatusBarColor(0x00000000);
        }
        getSupportActionBar().hide();


        Name = (EditText) findViewById(R.id.name);
        Password = (EditText) findViewById(R.id.pass);
        SecondPassword = (EditText) findViewById(R.id.secondPass);
        Next = (Button) findViewById(R.id.next);
        Back = (Button) findViewById(R.id.BackFromReg);

        Next.setOnClickListener(v -> {
            try {
                if (Password.getText().toString().equals(SecondPassword.getText().toString())) {
                    send();
                } else {
                    Toast.makeText(RegistrationActivity.this, "You cannot register with these fields", Toast.LENGTH_SHORT).show();
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        Back.setOnClickListener(v -> {
            finish();
//            startActivity(new Intent(this, MainActivity.class));
        });
    }

    public void send() throws ExecutionException, InterruptedException {
        registration = new Registration();
        registration.execute(Name.getText().toString(), Password.getText().toString());
        String msgFromServer = registration.get();
        SHA256 passwordSHA = new SHA256(Password.getText().toString());
        passwordSHA.encryptText();
        GLOBALVALUES.login = Name.getText().toString();
        GLOBALVALUES.password = passwordSHA.getCipherText();
        System.out.println(GLOBALVALUES.login + " " + GLOBALVALUES.password);
        if (msgFromServer.equals("You Can Register")) {
            Log.d("TAG", "You Can Register");
            startActivity(new Intent(this, RegistrationNickNameActivity.class));
        }
        if (msgFromServer.equals("This user has already registered")) {
            Toast.makeText(RegistrationActivity.this, msgFromServer, Toast.LENGTH_SHORT).show();
            Log.d("TAG", "This user has already registered");
        }
    }
}
