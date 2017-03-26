package com.discteam.e_wardrobe;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment
    implements NumberRequestTask.CallBacks {

    EditText mLoginEditText;
    EditText mPasswordEditText;
    Button mSignInButton;

    public static LoginFragment newInstance(){
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mLoginEditText = (EditText)view.findViewById(R.id.login_edit_text);
        mPasswordEditText = (EditText)view.findViewById(R.id.password_edit_text);
        mSignInButton = (Button)view.findViewById(R.id.sign_in_button);

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = mLoginEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                //TODO: check on correct
                if (login.length() >= 3 && password.length() >= 5) {
                    new NumberRequestTask(LoginFragment.this, null).execute(NumberRequestTask.LOGIN, login, password);
                }
            }
        });
        return view;
    }

    @Override
    public void onNumberRequestCompleted(Integer number) {
        //TODO: start wardrobe activity if login is success
        Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();
    }
}
