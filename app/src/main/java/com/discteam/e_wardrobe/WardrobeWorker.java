package com.discteam.e_wardrobe;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class WardrobeWorker {

    private static final String TAG = "WardrobeWorker";
    private static final String GET_NUMBER_URL = "https://point-device-cramp.000webhostapp.com/getnumber.php";
    private static final String PASS_NUMBER_URL = "https://point-device-cramp.000webhostapp.com/passnumber.php";
    private static final String LOGIN_URL = "https://point-device-cramp.000webhostapp.com/login.php";
    private static final String REGISTRATION_URL = "https://point-device-cramp.000webhostapp.com/registration.php";

    private Integer mNumber;
    private String mLogin;
    private String mPassword;

    WardrobeWorker(Integer number, String login, String password){
        mNumber = number;
        mLogin = login;
        mPassword = password;
    }

    private Integer doNumberRequest(String urlSpec) throws IOException {
        HttpURLConnection connection = null;
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
            if (urlSpec.equals(LOGIN_URL) || urlSpec == REGISTRATION_URL) {
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
        return result.equals("") ? null : Integer.parseInt(result);
    }

    public Integer getNumber() {
        Integer numb = null;
        try {
            numb = doNumberRequest(GET_NUMBER_URL);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to get number: ", ioe);
        }
        return numb;
    }

    public void passNumber() {
        try {
            doNumberRequest(PASS_NUMBER_URL);
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
            numb = doNumberRequest(LOGIN_URL);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to sign in: ", ioe);
        }
        return numb;
    }

    //TODO: implementation
    public void signUp(String login, String password) {

    }
}
