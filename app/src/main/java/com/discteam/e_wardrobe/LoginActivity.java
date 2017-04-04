package com.discteam.e_wardrobe;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class LoginActivity extends SingleFragmentActivity {

    public static final String EXTRA_LOGIN = "com.discteam.e_wardrobe.login";
    public static final String EXTRA_PASSWORD = "com.discteam.e_wardrobe.password";

    @Override
    protected Fragment createFragment(){
        return LoginFragment.newInstance();
    }

    public static Intent newIntent(Context context, String login, String password) {
        Intent i = new Intent(context, LoginActivity.class);
        i.putExtra(EXTRA_LOGIN, login);
        i.putExtra(EXTRA_PASSWORD, password);
        return i;
    }
}
