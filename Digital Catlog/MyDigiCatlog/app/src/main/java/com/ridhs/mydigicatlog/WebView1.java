package com.ridhs.mydigicatlog;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by noesisimac on 10/18/16.
 */
public class WebView1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_pdf);

        WebView webView=(WebView)findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);

        String pdf = "http://www.pdf995.com/samples/pdf.pdf";

        webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);


    }
}
