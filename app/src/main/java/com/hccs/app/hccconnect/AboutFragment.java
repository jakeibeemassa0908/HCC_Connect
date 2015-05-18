package com.hccs.app.hccconnect;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by theotherside on 27/03/15.
 */
public class AboutFragment extends Fragment {

    public WebView mWebView;
    public String abouturl="https://psmobile.hccs.edu/index.php/app/about";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.about_fragment,container,false);
        mWebView = (WebView)rootView.findViewById(R.id.webView_about);

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

                mWebView.loadUrl("javascript:(function() { "
                        + "document.getElementById('setting').style.visibility='hidden';"
                        + "})()");
            }

        });
        mWebView.loadUrl(abouturl);
        return rootView;
    }
}
