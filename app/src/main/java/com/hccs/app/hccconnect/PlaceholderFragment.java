package com.hccs.app.hccconnect;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    private  ProgressBar progressBar;
    private LinearLayout noInternetLayout;


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
            case 9:
                mUrl = mBaseUrl+"/index.php/app/about";
                break;
            case 10:
                //Logout
                mUrl=mBaseUrl+"/index.php/app/profile/logout";
                break;
            default:
                mUrl=mBaseUrl;
        }

        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        progressBar =(ProgressBar)rootView.findViewById(R.id.progress);
        progressBar.setMax(100);
        noInternetLayout = (LinearLayout)rootView.findViewById(R.id.noInternetLayout);
        Button tryAgain = (Button)rootView.findViewById(R.id.try_again_button);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndLoad(mWebView,mUrl);
            }
        });
        //progressBar.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);

        mWebView = (WebView)rootView.findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //if network active and connected
                checkAndLoad(view,url);
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

                mWebView.loadUrl("javascript: var x=document.getElementsByTagName('footer')[0].innerHTML='<span>hello</span>';");


                SharedPreferences mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                if(mSharedPrefs.getBoolean(SettingsActivity.PREF_AUTOFILL,false)){
                    if(url.equals("https://psmobile.hccs.edu/index.php/app/profile/loginform")){
                        String user=mSharedPrefs.getString(SettingsActivity.PREF_USERNAME,"");
                        String pwd =mSharedPrefs.getString(SettingsActivity.PREF_PWD,"");
                        mWebView.loadUrl("javascript: var x=document.getElementsByName('username')[0].value ='"+user+"';");
                        mWebView.loadUrl("javascript: var x=document.getElementsByName('password')[0].value ='"+pwd+"';");
                    }
                }
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon){
                mWebView.loadUrl("javascript:(function() { "
                        + "document.getElementById('open-sidebar').style.visibility='hidden';"
                        + "})()");
            }
        });


        mWebView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView webView, int progress){
                if(progress ==100){
                    mWebView.setVisibility(View.VISIBLE);
                }else{
                    mWebView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(progress);
                }
            }
        });

        checkAndLoad(mWebView,mUrl);

        return rootView;
    }

    private boolean checkAndLoad(WebView webView, String url){
        if(url.startsWith("tel:")){
            String number =url.replace("-","").replace("/","");
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
            startActivity(intent);
            return true;
        }else if(url.startsWith("mailto:")){
            String email=url.replace("mailto:","").trim();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL,new String[]{email});
            try{
                startActivity(intent);
                return true;
            }catch (android.content.ActivityNotFoundException ex ){
                Toast.makeText(getActivity(), getString(R.string.no_email_client), Toast.LENGTH_LONG).show();
                return false;
            }
        }

        //if network active and connected
        if(isNetworkAvailable()){
            webView.loadUrl(url);
            noInternetLayout.setVisibility(View.GONE);
        }else{
            mWebView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            noInternetLayout.setVisibility(View.VISIBLE);
        }
        return true;
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
