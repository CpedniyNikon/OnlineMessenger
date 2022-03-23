package com.example.chadt.SendingTasks;

import android.os.AsyncTask;

import com.example.chadt.GlobalVariables.CONSTANTS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MessageSender extends AsyncTask<String, Void, Void> {
    private Socket clientSocket = null;
    private OutputStreamWriter outputStreamWriter = null;
    private BufferedWriter bufferedWriter = null;
    private InputStreamReader inputStreamReader = null;
    private BufferedReader bufferedReader = null;

    @Override
    protected Void doInBackground(String... voids) {
        System.out.println("sendingProcess");
        String text = voids[0];
        try {
            clientSocket = new Socket(CONSTANTS.ipAddress, 4000);

            outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream());
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);

            bufferedWriter.write("sendToEveryone " + text);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        System.out.println("sender closed");
        try {
            clientSocket.close();
            outputStreamWriter.close();
            bufferedWriter.close();
            inputStreamReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
