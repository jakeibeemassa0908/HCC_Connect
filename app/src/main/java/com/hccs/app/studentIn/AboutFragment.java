package com.hccs.app.studentIn;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

/**
 * Created by theotherside on 27/03/15.
 */
public class AboutFragment extends Fragment {

    private WebView mWebView;
    private final String abouturl="https://psmobile.hccs.edu/index.php/app/about";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.about_fragment,container,false);
        mWebView = (WebView)rootView.findViewById(R.id.webView);

        final ProgressBar progressBar =(ProgressBar)rootView.findViewById(R.id.progress);
        progressBar.setMax(100);

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

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int progress) {
                if (progress == 100) {
                    mWebView.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(progress);
                }
            }
        });

        Button disclaimerButton = (Button)rootView.findViewById(R.id.disclaimer_button);
        disclaimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openDisclaimer = new Intent(getActivity(),DisclaimerActivity.class);
                startActivity(openDisclaimer);
            }
        });


        mWebView.loadUrl(abouturl);


        return rootView;
    }
}
