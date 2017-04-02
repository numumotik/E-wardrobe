package com.discteam.e_wardrobe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationFragment extends Fragment
    implements NumberRequestTask.CallBacks {

    EditText mLoginEditText;
    EditText mPasswordEditText;
    Button mSignUpButton;
    TextView mIncorrectLoginTextView;

    public static RegistrationFragment newInstance(){
        return new RegistrationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        mLoginEditText = (EditText)view.findViewById(R.id.fragment_registration_login_edit_text);
        mPasswordEditText = (EditText)view.findViewById(R.id.fragment_registration_password_edit_text);
        mSignUpButton = (Button)view.findViewById(R.id.fragment_registration_sign_up_button);
        mIncorrectLoginTextView = (TextView)view.findViewById(R.id.fragment_registration_incorrect_login_text_view);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = mLoginEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                if (LoginFragment.userDataIsCorrect(login, password)) {
                    new NumberRequestTask(RegistrationFragment.this, null).execute(NumberRequestTask.REGISTRATION, login, password);
                } else {
                    mIncorrectLoginTextView.setText(R.string.incorrect_login_text);
                    mIncorrectLoginTextView.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }

    @Override
    public void onNumberRequestCompleted(Integer number) {
        if (number == 0) {
            mIncorrectLoginTextView.setText(R.string.user_exists_text);
            mIncorrectLoginTextView.setVisibility(View.VISIBLE);
        } else {
            mIncorrectLoginTextView.setVisibility(View.GONE);
            Intent i = LoginActivity.newIntent(getActivity());
            startActivity(i);
        }
    }
}
