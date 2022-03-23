package com.example.chadt.ReceivingTasks;

import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chadt.GeneralChatActivity;
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
    private EditText editText = null;
    private TextView textView = null;
    private LinearLayout linearLayout = null;
    private boolean flag = true;

    @Override
    protected Void doInBackground(GeneralChatActivity... voids) {
        generalChatActivity = voids[0];
        editText = voids[0].getEditText();
        linearLayout = voids[0].getLayout();
        System.out.println("MessageReceiver started");
        System.out.println("KEK");
        try {
            System.out.println("preSocket");
            clientSocket = new Socket(CONSTANTS.ipAddress, 4000);
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
        do {
            System.out.println("Receiving");
            try {
                msgFromServer = bufferedReader.readLine();
                System.out.println("MessageReceiver " + msgFromServer);
                if (!msgFromServer.equals(""))
                    publishProgress();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (flag);
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        textView = new TextView(generalChatActivity);
        textView.setLayoutParams(new LinearLayout.LayoutParams(generalChatActivity.getEditText().getWidth(), generalChatActivity.getEditText().getHeight() / 2));
        textView.setText(msgFromServer);
        textView.setTextColor(Color.WHITE);
        linearLayout.addView(textView);
        super.onProgressUpdate(values);
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
        System.out.println("postFlag");
    }
}