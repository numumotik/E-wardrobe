package com.discteam.e_wardrobe;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;

public class WardrobeWorker {

    private static final String TAG = "WardrobeWorker";
    private static final String REQUEST_ADDRESS = "192.168.43.234";
    /*private static final String GET_NUMBER_URL = "https://point-device-cramp.000webhostapp.com/getnumber.php";
    private static final String PASS_NUMBER_URL = "https://point-device-cramp.000webhostapp.com/passnumber.php";
    private static final String LOGIN_URL = "https://point-device-cramp.000webhostapp.com/login.php";
    private static final String REGISTRATION_URL = "https://point-device-cramp.000webhostapp.com/registration.php";*/

    private static final int GET_NUMBER_REQUEST_CODE = 0;
    private static final int LOGIN_REQUEST_CODE = -1;
    private static final int REGISTRATION_REQUEST_CODE = -2;
    private static final int PORT = 8080;

    private Integer mNumber;
    private String mLogin;
    private String mPassword;

    WardrobeWorker(Integer number, String login, String password){
        mNumber = number;
        mLogin = login;
        mPassword = password;
    }

    private Integer doNumberRequest(/*String urlSpec*/int requestCode) throws IOException {
        /*HttpURLConnection connection = null;
        String result = "";
        try {
            URL url = new URL(urlSpec);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String postData = URLEncoder.encode("login", "UTF-8") + "=" + URLEncoder.encode(mLogin, "UTF-8");
            if (urlSpec.equals(LOGIN_URL) || urlSpec.equals(REGISTRATION_URL)) {
                postData += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(mPassword, "UTF-8");
            }
            if (urlSpec.equals(PASS_NUMBER_URL)) {
                postData += "&" + URLEncoder.encode("number", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(mNumber), "UTF-8");
            }
            writer.write(postData);
            writer.flush();
            writer.close();
            outputStream.close();
            if (!urlSpec.equals(PASS_NUMBER_URL)) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
            }
        } finally {
            connection.disconnect();
        }
        return result.equals("") ? null : Integer.parseInt(result);*/

        Integer numb = null;
        Socket s = new Socket(REQUEST_ADDRESS, PORT);
        OutputStream oStream = s.getOutputStream();
        DataOutputStream request = new DataOutputStream(oStream);
        String postData = URLEncoder.encode("login", "UTF-8") + "=" + URLEncoder.encode(mLogin, "UTF-8") +
                "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(mPassword, "UTF-8") +
                "&" + URLEncoder.encode("code", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(requestCode), "UTF-8");
        request.writeUTF(postData);
        if (requestCode <= 0) {
            InputStream iStream = s.getInputStream();
            DataInputStream response = new DataInputStream(iStream);
            numb = response.readInt();
        }
        s.close();
        return numb;
    }

    public Integer getNumber() {
        Integer numb = null;
        try {
            numb = doNumberRequest(/*GET_NUMBER_URL*/GET_NUMBER_REQUEST_CODE);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to get number: ", ioe);
        }
        return numb;
    }

    public void passNumber() {
        try {
            doNumberRequest(/*PASS_NUMBER_URL*/mNumber);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to pass number: ", ioe);
        }
    }

    /**
     * -1 when user was not found
     * 0 when number was not got
     * n when number = n
     **/
    public Integer signIn() {
        Integer numb = null;
        try {
            numb = doNumberRequest(/*LOGIN_URL*/LOGIN_REQUEST_CODE);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to sign in: ", ioe);
        }
        return numb;
    }

    /**
     * 0 when not success
     * 1 when success
     **/
    public Integer signUp() {
        Integer numb = null;
        try {
            numb = doNumberRequest(/*REGISTRATION_URL*/REGISTRATION_REQUEST_CODE);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to sign up: ", ioe);
        }
        return numb;
    }
}
