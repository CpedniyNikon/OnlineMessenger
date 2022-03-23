package com.example.chadt.RegistrationCode;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chadt.AuthorizationCode.AuthorizationActivity;
import com.example.chadt.MainActivity;
import com.example.chadt.R;

import java.util.concurrent.ExecutionException;

public class RegistrationActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private Button signUp;
    private Button backB;

    private Registration registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
            getWindow().setStatusBarColor(getResources().getColor(R.color.darkBlue));
        }
        getSupportActionBar().hide();

        Name = (EditText) findViewById(R.id.name);
        Password = (EditText) findViewById(R.id.pass);
        signUp = (Button) findViewById(R.id.registr);
        backB = (Button) findViewById(R.id.BackFromReg);

        signUp.setOnClickListener(v ->{
            try {
                if(Name.getText().toString().indexOf(' ') != -1 || Password.getText().toString().indexOf(' ') != -1) {
                    Toast.makeText(RegistrationActivity.this, "You cannot register with these fields", Toast.LENGTH_SHORT).show();
                } else {
                    send();
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        backB.setOnClickListener(v->{
//            finish();
            startActivity(new Intent(this, MainActivity.class));
        });
    }


    public void send() throws ExecutionException, InterruptedException {
        registration = new Registration();
        registration.execute(Name.getText().toString(), Password.getText().toString());
        String msgFromServer = registration.get();
        if(msgFromServer.equals("You successfully registered")) {
            Log.d("TAG", "You successfully registered");
            finish();
            startActivity(new Intent(this, AuthorizationActivity.class));
        }
        if(msgFromServer.equals("This user has already registered")) {
            Log.d("TAG", "This user has already registered");
        }
    }
}
