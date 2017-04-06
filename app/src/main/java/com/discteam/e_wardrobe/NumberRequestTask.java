package com.discteam.e_wardrobe;

import android.os.AsyncTask;

public class NumberRequestTask extends AsyncTask<String, Void, Integer> {

    private static final String TAG = "NumberRequestTask";

    public static final String GET_NUMBER = "getNumber";
    public static final String PASS_NUMBER = "passNumber";
    public static final String LOGIN = "login";
    public static final String REGISTRATION = "registration";

    private CallBacks mHost;
    private Integer mNumber;

    public interface CallBacks {
        void onNumberRequestCompleted(Integer number);
    }

    NumberRequestTask(CallBacks host, Integer number) {
        mHost = host;
        mNumber = number;
    }

    @Override
    protected Integer doInBackground(String... params) {
        String opType = params[0];
        String login = params[1];
        String password = params[2];
        WardrobeWorker worker = new WardrobeWorker(mNumber, login, password);
        switch(opType) {
            case GET_NUMBER:
                return worker.getNumber();

            case PASS_NUMBER:
                worker.passNumber();
                return null;

            case LOGIN:
                return worker.signIn();

            case REGISTRATION:
                return worker.signUp();

            default:
                return null;
        }
    }

    @Override
    protected void onPostExecute(Integer number) {
       mHost.onNumberRequestCompleted(number);
    }

}
