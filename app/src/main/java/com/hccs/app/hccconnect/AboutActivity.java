package com.hccs.app.hccconnect;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by theotherside on 11/04/15.
 */
public class AboutActivity extends SingleFragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        return new AboutFragment();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
