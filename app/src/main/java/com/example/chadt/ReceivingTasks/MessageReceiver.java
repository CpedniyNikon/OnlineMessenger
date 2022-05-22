package com.example.chadt.ReceivingTasks;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.chadt.Fragments.GeneralChatActivity;
import com.example.chadt.R;
import com.example.chadt.GlobalVariables.CONSTANTS;
import com.example.chadt.GlobalVariables.GLOBALVALUES;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MessageReceiver extends AsyncTask<GeneralChatActivity, Void, Void> {
    private Socket clientSocket = null;

    private InputStreamReader inputStreamReader = null;
    private BufferedReader bufferedReader = null;

    private OutputStreamWriter outputStreamWriter = null;
    private BufferedWriter bufferedWriter = null;

    private String msgFromServer = null;
    private GeneralChatActivity generalChatActivity = null;
    private LinearLayout linearLayout = null;

    private Vibrator vibrator;
    private MediaPlayer mediaPlayer;

    private boolean flag = true;

    @Override
    protected Void doInBackground(GeneralChatActivity... voids) {
        generalChatActivity = voids[0];
        linearLayout = voids[0].getLayout();
        System.out.println("MessageReceiver started");
        System.out.println("KEK");
        try {
            System.out.println("preSocket");
            clientSocket = new Socket(CONSTANTS.ipAddress, CONSTANTS.port);
            System.out.println("postSocket");
            System.out.println("socketConnected");

            inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);

            outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream());
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            bufferedWriter.write("new client");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println(msgFromServer = bufferedReader.readLine());
            System.out.println(msgFromServer.substring(0, 25));
            System.out.println(GLOBALVALUES.idOfUser = Integer.parseInt(msgFromServer.substring(26)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Receiving");
        while (flag) {
            System.out.println("Receiving");
            try {
                msgFromServer = bufferedReader.readLine();
                System.out.println("MessageReceiver " + msgFromServer);
                if (!msgFromServer.equals("")) {
                    publishProgress();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("ReceivingTask ended");
        return null;
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        TextView textView = new TextView(generalChatActivity.getContext());
        textView.setText("  " + msgFromServer + "  ");
        textView.setBackground(ContextCompat.getDrawable(generalChatActivity.getContext(), R.drawable.chat_message));
        textView.setTextColor(Color.WHITE);

        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(
                80, 80
        );
        ImageView imageView = new ImageView(generalChatActivity.getContext());
        imageView.setImageResource(R.drawable.avatar);

        textViewParams.setMargins(110, 50, 5, 0);
        textView.setLayoutParams(textViewParams);
        linearLayout.addView(textView);
        imageViewParams.setMargins(20, -90, 0, 0);
        imageView.setLayoutParams(imageViewParams);
        linearLayout.addView(imageView);
        super.onProgressUpdate(values);
    }


    public void killFlag() {
        this.flag = false;
    }
}