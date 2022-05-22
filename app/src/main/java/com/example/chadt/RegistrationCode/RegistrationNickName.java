package com.example.chadt.RegistrationCode;

import android.os.AsyncTask;

import com.example.chadt.GlobalVariables.CONSTANTS;
import com.example.chadt.GlobalVariables.GLOBALVALUES;
import com.example.chadt.algorithms.SHA256;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class RegistrationNickName extends AsyncTask<String, Void, Void> {
    private Socket clientSocket = null;

    private OutputStreamWriter outputStreamWriter = null;
    private BufferedWriter bufferedWriter = null;

    private InputStreamReader inputStreamReader = null;
    private BufferedReader bufferedReader = null;

    @Override
    protected Void doInBackground(String... voids) {
        String name = voids[0];
        try {
            clientSocket = new Socket(CONSTANTS.ipAddress, CONSTANTS.port);
            outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream());
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter.write("changeNickName " + GLOBALVALUES.login + " " + GLOBALVALUES.password + " " + name);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            clientSocket.close();
            outputStreamWriter.close();
            bufferedWriter.close();
            inputStreamReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
