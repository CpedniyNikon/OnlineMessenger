package com.example.chadt.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chadt.AuthorizationCode.AuthorizationActivity;
import com.example.chadt.GlobalVariables.GLOBALVALUES;
import com.example.chadt.R;
import com.example.chadt.ReceivingTasks.MessageReceiver;
import com.example.chadt.SendingTasks.MessageSender;

public class GeneralChatActivity extends Fragment {
    private LinearLayout chatLayout;
    private EditText msgText;
    private Button sendButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.general_chat_activity, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        sendButton = (Button) getView().findViewById(R.id.sendButton);
        msgText = (EditText) getView().findViewById(R.id.msgText);
        chatLayout = (LinearLayout) getView().findViewById(R.id.chatLayout);
        sendButton.setOnClickListener(v -> {
            String msg = msgText.getText().toString().replaceAll("\r", "$").replaceAll("\n", "$").replaceAll(" ", "$");
            showYourMessage(msg);
            MessageSender messageSender = new MessageSender();
            messageSender.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, msg);
            msgText.setText("");
        });
        MessageReceiver messageReceiver = new MessageReceiver();
        messageReceiver.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this);
    }

    public LinearLayout getLayout() {
        return chatLayout;
    }

    public void showYourMessage(String msg) {


        TextView textView = new TextView(getContext());
        textView.setText("  " + msg + "  ");
        textView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.my_message));
        textView.setTextColor(Color.BLACK);

        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textViewParams.gravity = Gravity.CENTER | Gravity.END;
        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(
                80, 80
        );
        imageViewParams.gravity = Gravity.CENTER | Gravity.END;
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.avatar);

        textViewParams.setMargins(5, 50, 110, 10);
        textView.setLayoutParams(textViewParams);
        chatLayout.addView(textView);
        imageViewParams.setMargins(0, -90, 20, 0);
        imageView.setLayoutParams(imageViewParams);
        chatLayout.addView(imageView);
    }

}