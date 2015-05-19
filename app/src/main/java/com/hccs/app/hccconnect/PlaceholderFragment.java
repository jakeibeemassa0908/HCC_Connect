package com.hccs.app.hccconnect;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * A placeholder fragment containing a simple view.
 */
public  class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static int mNumber;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @SuppressWarnings("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int number = getArguments().getInt(ARG_SECTION_NUMBER);
        mNumber=number;
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
            case 5:
                //Enrollment
                mUrl= mBaseUrl+"/index.php/app/profile/submenus/enrollment";
                break;
            case 6:
                //My Information
                mUrl =mBaseUrl+"/index.php/app/profile/submenus/myinfo";
                break;
            case 7:
                //Widgets
                mUrl= mBaseUrl+"/index.php/app/user/widget";
                break;
            case 8:
                //EGLS3
                mUrl = mBaseUrl+"/index.php/app/courseval/EGLS3";
                break;
            case 10:
                //Logout
                mUrl=mBaseUrl+"/index.php/app/profile/logout";
                break;
            default:
                mUrl=mBaseUrl;
        }

        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        final ProgressBar progressBar =(ProgressBar)rootView.findViewById(R.id.progress);
        progressBar.setMax(100);
        //progressBar.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);

        mWebView = (WebView)rootView.findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true; // super.shouldOverrideUrlLoading(view, url);
            }

            public void onPageFinished(WebView view, String url) {
                mWebView.loadUrl("javascript:(function() { "
                        + "document.getElementById('open-sidebar').style.visibility='hidden';"
                        + "})()");

                //If schedule view, leave the setting menu
                if(mNumber != 3){
                    mWebView.loadUrl("javascript:(function() { "
                            + "document.getElementById('setting').style.visibility='hidden';"
                            + "})()");
                }
                String user="W206630122";
                String pwd = "RienN_estImpossible1991";
                mWebView.loadUrl("javascript: var x=document.getElementsByName('username')[0].value ='"+user+"';");
                mWebView.loadUrl("javascript: var x=document.getElementsByName('password')[0].value ='"+pwd+"';");
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView webView, int progress){
                if(progress ==100){
                    mWebView.setVisibility(View.VISIBLE);
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(progress);
                }
            }
        });

        mWebView.loadUrl(mUrl);

        return rootView;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
