package com.example.chadt;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chadt.AuthorizationCode.AuthorizationActivity;
import com.example.chadt.SendingTasks.UserListGetter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MenuActivity extends AppCompatActivity {

    private Button settingsButton = null;
    private Button exitButton = null;
    private Button refreshButton = null;
    private UserListGetter userListGetter = null;

    private boolean isMenuOpen = false;
    private Button userButton = null;

    private LinearLayout usersLayout = null;

    private Button joinButton = null;


    private ArrayList<String> userList = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
            getWindow().setStatusBarColor(getResources().getColor(R.color.darkBlue));
        }

        getSupportActionBar().hide();

        settingsButton = (Button) findViewById(R.id.sttngs);
        exitButton = (Button) findViewById(R.id.xt);
        refreshButton = (Button) findViewById(R.id.rfrsh);
        usersLayout = (LinearLayout) findViewById(R.id.rfrshlt);
        joinButton = (Button) findViewById(R.id.jncht);

        exitButton.setActivated(false);
        exitButton.setVisibility(View.INVISIBLE);

        joinButton.setOnClickListener(v-> {
            startActivity(new Intent(this, GeneralChatActivity.class));
        });

        settingsButton.setOnClickListener(v-> {
            if(!isMenuOpen) {
                exitButton.setVisibility(View.VISIBLE);
                exitButton.setActivated(true);
                isMenuOpen = true;
            } else {
                exitButton.setActivated(false);
                exitButton.setVisibility(View.INVISIBLE);
                isMenuOpen = false;
            }
        });


        exitButton.setOnClickListener(v-> {
            finish();
            startActivity(new Intent(this, AuthorizationActivity.class));
        });

        refreshButton.setOnClickListener(v -> {
            try {
                send();
            } catch (ExecutionException | InterruptedException | IOException e) {
                e.printStackTrace();
            }
            usersLayout.removeAllViews();
            for(int i = 0; i < userList.size(); i++) {
                userButton = new Button(this);
                userButton.setLayoutParams(new LinearLayout.LayoutParams(settingsButton.getWidth(),settingsButton.getHeight()));
                userButton.setText(userList.get(i));
                usersLayout.addView(userButton);
            }
        });

    }

    private void send() throws ExecutionException, InterruptedException, IOException {
        System.out.println("send()");
        userListGetter = new UserListGetter();
        userListGetter.execute();
        userList =new ArrayList<>(userListGetter.get());
    }
}
