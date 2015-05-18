package com.hccs.app.hccconnect;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URL;

/**
 * A placeholder fragment containing a simple view.
 */
public  class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private String mBaseUrl= "https://psmobile.hccs.edu";
    private String mUrl;
    private WebView mWebView;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceholderFragment() {
    }

    @SuppressWarnings("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int number = getArguments().getInt(ARG_SECTION_NUMBER);
        switch (number){
            case 1:
                //Dashboard
                mUrl=mBaseUrl;
                break;
            case 2:
                //Financials
                mUrl =mBaseUrl+"/index.php/app/profile/submenus/financials";
                break;
            case 3:
                //Schedule
                mUrl= mBaseUrl+"/index.php/app/user/schedule/index";
                break;
            case 4:
                //academics
                mUrl = mBaseUrl+"/index.php/app/profile/submenus/myacad";
                break;
            default:
                mUrl=mBaseUrl;
        }

        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mWebView = (WebView)rootView.findViewById(R.id.webView);

        mWebView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                return false;
            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(mUrl);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((Dashboard) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
