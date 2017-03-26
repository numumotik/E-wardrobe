package com.discteam.e_wardrobe;

import android.os.AsyncTask;

public class NumberRequestTask extends AsyncTask<String, Void, Integer> {

    public static final String GET_NUMBER = "getNumber";
    public static final String PASS_NUMBER = "passNumber";
    public static final String LOGIN = "login";
    public static final String REGISTRATION = "registration";

    CallBacks mHost;
    Integer mNumber;

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
        switch(opType) {
            case GET_NUMBER:
                return new WardrobeWorker(mNumber).getNumber();

            case PASS_NUMBER:
                new WardrobeWorker(mNumber).passNumber();
                return null;

            case LOGIN:
                String login = params[1];
                String password = params[2];
                return null;

            case REGISTRATION:
                return null;

            default:
                return null;
        }
    }

    @Override
    protected void onPostExecute(Integer number) {
       mHost.onNumberRequestCompleted(number);
    }

}
