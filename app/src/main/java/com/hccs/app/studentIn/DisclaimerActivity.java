package com.hccs.app.studentIn;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by theotherside on 11/04/15.
 */
public class DisclaimerActivity extends SingleFragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        return new DisclaimerFragment();
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        // Log.d(TAG,"out here");
        Intent resultIntent;
        resultIntent = new Intent(this,AboutActivity.class);
        return resultIntent;

    }
}
