package com.example.chadt.AuthorizationCode;

import android.os.AsyncTask;

import com.example.chadt.GlobalVariables.CONSTANTS;
import com.example.chadt.algorithms.SHA256;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Authorization extends AsyncTask<String,Void,String> {

    private Socket clientSocket = null;

    private OutputStreamWriter outputStreamWriter = null;
    private BufferedWriter bufferedWriter = null;

    private InputStreamReader inputStreamReader = null;
    private BufferedReader bufferedReader = null;

    private String msgFromServer = null;
    @Override
    protected String doInBackground(String... voids) {
        String name = voids[0], password  =  voids[1];
        try {
            clientSocket = new Socket(CONSTANTS.ipAddress, 4000);

            outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream());
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);

            SHA256 passwordSHA = new SHA256(password);
            passwordSHA.encryptText();

            bufferedWriter.write("l " + name + " " + passwordSHA.getCipherText());
            bufferedWriter.newLine();
            bufferedWriter.flush();

            msgFromServer = bufferedReader.readLine();

            clientSocket.close();
            outputStreamWriter.close();
            bufferedWriter.close();
            inputStreamReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msgFromServer;
    }
}

