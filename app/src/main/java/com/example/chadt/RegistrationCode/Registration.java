package com.example.chadt.RegistrationCode;

import android.os.AsyncTask;

import com.example.chadt.GlobalVariables.CONSTANTS;
import com.example.chadt.algorithms.SHA256;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Registration extends AsyncTask<String,Void,String> {
    private Socket clientSocket = null;

    private OutputStreamWriter outputStreamWriter = null;
    private BufferedWriter bufferedWriter = null;

    private InputStreamReader inputStreamReader = null;
    private BufferedReader bufferedReader = null;

    private String msgFromServer = null;
    @Override
    protected String doInBackground(String... voids) {
        System.out.println("registration");
        String name = voids[0], password  =  voids[1];
        try {

            System.out.println("presocket");

            clientSocket = new Socket(CONSTANTS.ipAddress, CONSTANTS.port);
            outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream());
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            System.out.println("postsocket");

            inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            SHA256 passwordSHA = new SHA256(password);
            passwordSHA.encryptText();

            System.out.println("premessage");

            bufferedWriter.write("r " + name + " " + passwordSHA.getCipherText());
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("postmessage");
            msgFromServer = bufferedReader.readLine();

            System.out.println(msgFromServer);

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
