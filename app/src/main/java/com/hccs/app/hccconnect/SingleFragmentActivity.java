package com.hccs.app.hccconnect;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Jake Ibee Massa on 07/03/15.
 */
public abstract class SingleFragmentActivity extends ActionBarActivity {

    protected abstract android.app.Fragment createFragment();

    protected int getLayoutResId(){
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(getLayoutResId());

        android.app.FragmentManager fm = getFragmentManager();
        android.app.Fragment fragment = fm.findFragmentById(R.id.container);

        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.container,fragment)
                    .commit();
        }

        getSupportActionBar().setElevation(0);

    }
}
