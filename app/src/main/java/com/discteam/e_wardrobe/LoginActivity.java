package com.discteam.e_wardrobe;

import android.support.v4.app.Fragment;

public class LoginActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return LoginFragment.newInstance();
    }
}
