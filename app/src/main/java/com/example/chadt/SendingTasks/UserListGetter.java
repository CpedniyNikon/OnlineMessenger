package com.example.chadt.SendingTasks;

import android.os.AsyncTask;

import com.example.chadt.GlobalVariables.CONSTANTS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class UserListGetter extends AsyncTask<Void,Void, ArrayList<String>> {
    private ArrayList<String> userList = new ArrayList<>();
    private Socket clientSocket = null;
    private OutputStreamWriter outputStreamWriter = null;
    private BufferedWriter bufferedWriter = null;
    private InputStreamReader inputStreamReader = null;
    private BufferedReader bufferedReader = null;
    private String msgFromServer = null;
    @Override
    protected ArrayList<String> doInBackground(Void... voids) {
        System.out.println("show users");
        try {
            System.out.println("preSocket");
            clientSocket = new Socket(CONSTANTS.ipAddress, 4000);
            System.out.println("postSocket");

            outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream());
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);

            bufferedWriter.write("returnUserList");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            int len = bufferedReader.read();
            System.out.println("ShowUsers " + len);
            for(int i = 0; i < len; i++) {
                msgFromServer = bufferedReader.readLine();
                userList.add(msgFromServer);
            }
            clientSocket.close();
            outputStreamWriter.close();
            bufferedWriter.close();
            inputStreamReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
