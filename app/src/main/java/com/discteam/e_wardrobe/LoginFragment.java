package com.discteam.e_wardrobe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginFragment extends Fragment
    implements NumberRequestTask.CallBacks {

    private static final int MIN_LOGIN_LENGTH = 3;
    private static final int MIN_PASSWORD_LENGTH = 4;

    EditText mLoginEditText;
    EditText mPasswordEditText;
    Button mSignInButton;
    Button mSignUpButton;
    TextView mIncorrectLoginTextView;
    CheckBox mRememberCheckBox;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isUserRemembered = NumberPreferences.isUserRemembered(getContext());
        if (isUserRemembered) {
            mLoginEditText.setText(NumberPreferences.getLogin(getContext()));
            mPasswordEditText.setText(NumberPreferences.getPassword(getContext()));
            mSignInButton.callOnClick();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mLoginEditText = (EditText)view.findViewById(R.id.fragment_login_login_edit_text);
        mPasswordEditText = (EditText)view.findViewById(R.id.fragment_login_password_edit_text);
        mSignInButton = (Button)view.findViewById(R.id.fragment_login_sign_in_button);
        mSignUpButton = (Button)view.findViewById(R.id.fragment_login_sign_up_button);
        mIncorrectLoginTextView = (TextView)view.findViewById(R.id.fragment_login_incorrect_login_text_view);
        mRememberCheckBox = (CheckBox)view.findViewById(R.id.fragment_login_remember_check_box);

        String login = (String)getActivity().getIntent().getCharSequenceExtra(LoginActivity.EXTRA_LOGIN);
        String password = (String)getActivity().getIntent().getCharSequenceExtra(LoginActivity.EXTRA_PASSWORD);
        if (login != null) {
            mLoginEditText.setText(login);
        }
        if (password != null) {
            mPasswordEditText.setText(password);
        }

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = mLoginEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                if (userDataIsCorrect(login, password)) {
                    new NumberRequestTask(LoginFragment.this, null).execute(NumberRequestTask.LOGIN, login, password);
                } else {
                    mIncorrectLoginTextView.setVisibility(View.VISIBLE);
                }
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = RegistrationActivity.newIntent(getActivity());
                startActivity(i);
            }
        });
        return view;
    }

    @Override
    public void onNumberRequestCompleted(Integer number) {
        if (number == -1) {
            mIncorrectLoginTextView.setVisibility(View.VISIBLE);
        } else {
            mIncorrectLoginTextView.setVisibility(View.GONE);
            NumberPreferences.setLogin(getActivity(), mLoginEditText.getText().toString());
            NumberPreferences.setPassword(getActivity(), mPasswordEditText.getText().toString());
            NumberPreferences.setUserRemembered(getActivity(), mRememberCheckBox.isChecked());
            NumberPreferences.setNumber(getActivity(), number);
            Intent i = WardrobeActivity.newIntent(getActivity());
            startActivity(i);
        }
    }

    static public boolean userDataIsCorrect(String login, String password) {
        if (login.length() < MIN_LOGIN_LENGTH || password.length() < MIN_PASSWORD_LENGTH) {
            return false;
        }
        for (int i = 0; i < login.length(); ++i) {
            char c = login.charAt(i);
            if (c < 'a' && c > 'z' &&
                    c < 'A' && c > 'Z' &&
                    c < '0' && c > '9') {
                return false;
            }

        }
        for (int i = 0; i < password.length(); ++i) {
            char c = password.charAt(i);
            if (c < 'a' && c > 'z' &&
                    c < 'A' && c > 'Z' &&
                    c < '0' && c > '9') {
                return false;
            }

        }
        return true;
    }
}
