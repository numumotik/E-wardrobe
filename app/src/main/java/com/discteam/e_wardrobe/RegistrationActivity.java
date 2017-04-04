package com.discteam.e_wardrobe;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class RegistrationActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return RegistrationFragment.newInstance();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }
}
