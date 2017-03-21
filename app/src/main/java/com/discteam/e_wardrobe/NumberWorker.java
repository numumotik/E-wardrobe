package com.discteam.e_wardrobe;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class NumberWorker {

    private static final String TAG = "NumberWorker";
    private static final String GET_NUMBER_URL = "https://point-device-cramp.000webhostapp.com/getnumber.php";
    private static final String PASS_NUMBER_URL = "https://point-device-cramp.000webhostapp.com/passnumber.php";


    private Integer mNumber;

    NumberWorker(Integer number){
        mNumber = number;
    }

    private String getUrlString(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return new String(out.toByteArray());

        } finally {
            connection.disconnect();
        }
    }

    public String getNumber(){
        try {
            String str = getUrlString(GET_NUMBER_URL);
            mNumber = Integer.parseInt(str);
        }
        catch (IOException ioe){
            Log.e(TAG, "Failed to get number: ", ioe);
        }
        return mNumber.toString();
    }

    public void passNumber(){
        HttpURLConnection connection = null;
        try {
            URL url = new URL(PASS_NUMBER_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String postDataNumber = URLEncoder.encode("number", "UTF-8") + "=" + URLEncoder.encode(mNumber.toString(), "UTF-8");
            writer.write(postDataNumber);
            writer.flush();
            writer.close();
            outputStream.close();

            InputStream inputStream = connection.getInputStream();
            inputStream.close();
        } catch (MalformedURLException mURLe) {
            Log.e(TAG, "Failed to parse URL: ", mURLe);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to pass number: ", ioe);
        } finally {
            connection.disconnect();
        }
    }
}
