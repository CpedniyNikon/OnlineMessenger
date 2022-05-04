package com.example.chadt.AuthorizationCode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chadt.GlobalVariables.GLOBALVALUES;
import com.example.chadt.MenuCode.MenuActivity;
import com.example.chadt.R;
import com.example.chadt.RegistrationCode.RegistrationActivity;

import java.util.concurrent.ExecutionException;

public class AuthorizationActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private Button logIn;
    private TextView registrationView;

    private Authorization authorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autorization_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.white));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            );
            getWindow().setStatusBarColor(0x00000000);
        }
        getSupportActionBar().hide();

        Name = (EditText) findViewById(R.id.Name);
        Password = (EditText) findViewById(R.id.Pass);
        registrationView = (TextView) findViewById(R.id.authorizationView);

        SpannableString ss = new SpannableString("Don't have an account? Register it!");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(AuthorizationActivity.this, RegistrationActivity.class));
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(getResources().getColor(R.color.continueColor));
                textPaint.setUnderlineText(false);
            }
        };

        ss.setSpan(clickableSpan, 23, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        registrationView.setText(ss);
        registrationView.setMovementMethod(LinkMovementMethod.getInstance());
        registrationView.setHighlightColor(Color.TRANSPARENT);

        logIn = (Button) findViewById(R.id.authrstn);

        logIn.setOnClickListener(v -> {
            try {
                if (Name.getText().toString().indexOf(' ') != -1 || Password.getText().toString().indexOf(' ') != -1) {
                    Toast.makeText(AuthorizationActivity.this, "You cannot register with these fields", Toast.LENGTH_SHORT).show();
                } else {
                    send();
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

    public void send() throws ExecutionException, InterruptedException {
        authorization = new Authorization();
        authorization.execute(Name.getText().toString(), Password.getText().toString());
        String msgFromServer = authorization.get();
        if (msgFromServer.startsWith("You logged in")) {
            Log.d("TAG", "You logged in");
            GLOBALVALUES.login = Name.getText().toString();
            GLOBALVALUES.name = msgFromServer.substring(14);
            finish();
            startActivity(new Intent(this, MenuActivity.class));
        }
        if (msgFromServer.equals("Error while logging")) {
            Toast.makeText(AuthorizationActivity.this, "there is no user with these login and password", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "Error while logging");
        }
    }

}