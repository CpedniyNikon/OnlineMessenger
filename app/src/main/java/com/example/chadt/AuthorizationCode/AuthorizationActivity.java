package com.example.chadt.AuthorizationCode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chadt.GlobalVariables.GLOBALVALUES;
import com.example.chadt.MainActivity;
import com.example.chadt.MenuActivity;
import com.example.chadt.R;

import java.util.concurrent.ExecutionException;

public class AuthorizationActivity extends AppCompatActivity  {
    private EditText Name;
    private EditText Password;
    private Button logIn;
    private Button backB;

    private Authorization authorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autorization_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
            getWindow().setStatusBarColor(getResources().getColor(R.color.darkBlue));
        }
        getSupportActionBar().hide();

        Name = (EditText) findViewById(R.id.Name);
        Password = (EditText) findViewById(R.id.Pass);

        logIn = (Button) findViewById(R.id.authrstn);
        backB = (Button) findViewById(R.id.BackFromAuth);

        logIn.setOnClickListener(v -> {
            try {
                if(Name.getText().toString().indexOf(' ') != -1 || Password.getText().toString().indexOf(' ') != -1) {
                    Toast.makeText(AuthorizationActivity.this, "You cannot register with these fields", Toast.LENGTH_SHORT).show();
                } else {
                    send();
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        backB.setOnClickListener(v -> {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        });
    }

    public void send() throws ExecutionException, InterruptedException {
        authorization = new Authorization();
        authorization.execute(Name.getText().toString(), Password.getText().toString());
        String msgFromServer = authorization.get();
        if(msgFromServer.equals("You logged in")) {
            Log.d("TAG", "You logged in");
            finish();
            GLOBALVALUES.name = Name.getText().toString();
            startActivity(new Intent(this, MenuActivity.class));
        }
        if(msgFromServer.equals("Error while logging")) {
            Toast.makeText(AuthorizationActivity.this, "there is no user with these login and password", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "Error while logging");
        }
    }


}