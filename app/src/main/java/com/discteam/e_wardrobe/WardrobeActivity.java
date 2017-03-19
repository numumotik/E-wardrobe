package com.discteam.e_wardrobe;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class WardrobeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return WardrobeFragment.newInstance();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, WardrobeActivity.class);
    }
}

