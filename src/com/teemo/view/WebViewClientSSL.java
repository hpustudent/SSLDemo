/**
 * 
 */
package com.teemo.view;

import android.content.Context;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.ClientCertRequestHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author Administrator
 *
 */
public class WebViewClientSSL extends WebViewClient {
    private final String TAG = "WebViewClientSSL";
    

    public WebViewClientSSL(Context ctx) {
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        Log.e(TAG, "onReceivedError ： " + description);
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequestHandler handler, String host_and_port) {
        Log.e(TAG, "onReceivedClientCertRequest ： " + host_and_port);
        if ((null != AppConfig.mPrivateKey)
                && ((null != AppConfig.mX509Certificates) && (AppConfig.mX509Certificates.length != 0))) {
            handler.proceed(AppConfig.mPrivateKey, AppConfig.mX509Certificates);
        } else {
            handler.cancel();
        }
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        Log.e(TAG, "onReceivedSslError ： ");
        //handler.cancel();
        handler.proceed();
    }
}
