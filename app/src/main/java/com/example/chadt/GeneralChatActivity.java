package com.example.chadt;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chadt.GlobalVariables.GLOBALVALUES;
import com.example.chadt.ReceivingTasks.MessageReceiver;
import com.example.chadt.SendingTasks.CommandSender;
import com.example.chadt.SendingTasks.MessageSender;


public class GeneralChatActivity extends AppCompatActivity {

    private Button sendButton = null;
    private EditText msgText = null;
    private LinearLayout msgLayout = null;
    private MessageReceiver messageReceiver = null;
    private MessageSender messageSender = null;
    private Button backButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.generalchat_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
            getWindow().setStatusBarColor(getResources().getColor(R.color.darkBlue));
        }

        getSupportActionBar().hide();

        sendButton = (Button) findViewById(R.id.sndMsg);
        backButton = (Button) findViewById(R.id.back);
        msgText = (EditText) findViewById(R.id.ntrMsg);
        msgLayout = (LinearLayout) findViewById(R.id.msgLt);

        messageReceiver = new MessageReceiver();
        messageReceiver.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,this);
        sendButton.setOnClickListener(v->{
            System.out.println("sending");
            messageSender = new MessageSender();
            messageSender.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, msgText.getText().toString());
//            msgText.setText("");
        });

        backButton.setOnClickListener(v-> {
            messageReceiver.setFlag(false);
            finish();
            startActivity(new Intent(this, MenuActivity.class));
        });

    }

    @Override
    protected void onPause() {
        System.out.println("onStop");
        CommandSender commandSender = new CommandSender();
        commandSender.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "skip " + GLOBALVALUES.idOfUser);
        super.onPause();
    }
    // TODO: 23.03.2022 FINALIZE
    // TODO: 23.03.2022 RESTART
    @Override
    protected void onRestart() {
        System.out.println("restart");
        CommandSender commandSender = new CommandSender();
        commandSender.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "start " + GLOBALVALUES.idOfUser);
        super.onRestart();
    }

    public EditText getEditText() {
        return msgText;
    }
    public LinearLayout getLayout() {return msgLayout;}
}



