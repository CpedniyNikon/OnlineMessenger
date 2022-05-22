package com.example.chadt.SendingTasks;

import android.os.AsyncTask;

import com.example.chadt.GlobalVariables.CONSTANTS;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class CommandSender extends AsyncTask<String, Void, Void> {
    private Socket clientSocket = null;
    private OutputStreamWriter outputStreamWriter = null;
    private BufferedWriter bufferedWriter = null;

    protected Void doInBackground(String... voids) {
        String text = voids[0];
        System.out.println("sendingProcess");
        try {
            clientSocket = new Socket(CONSTANTS.ipAddress, CONSTANTS.port);

            outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream());
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            bufferedWriter.write("CommandFromUser " + text);
            bufferedWriter.newLine();
            bufferedWriter.flush();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
