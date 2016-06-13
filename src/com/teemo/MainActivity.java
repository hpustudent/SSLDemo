package com.teemo;

import com.teemo.view.AppConfig;
import com.teemo.view.WebViewClientSSL;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;

public class MainActivity extends Activity implements OnClickListener {
    private final String TAG = "MainActivity";
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        this.findViewById(R.id.main_load_btn).setOnClickListener(this);
        this.findViewById(R.id.main_cfg_btn).setOnClickListener(this);
        mWebView = (WebView) this.findViewById(R.id.main_webview_view);
    }

    private void configWebView() {
        Log.e(TAG, "configWebView : " + "start config web view.");
        try {
            AppConfig.initPrivateKeyAndX509Certificate(this);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "configWebView : " + e.getMessage());
        }
        WebViewClientSSL client = new WebViewClientSSL(this);
        mWebView.setWebViewClient(client);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.main_load_btn:
            mWebView.loadUrl(AppConfig.SERVER_URL);
            break;
        case R.id.main_cfg_btn:
            //configWebView();
            transform(null);
            break;
        }
    }

    private void transform(String str) {
        byte[] param = addZeroRightToMod8(str.getBytes());
        Log.e(TAG, "param length = " + param);
    }

    /**
     * 功能描述：在一个byte[]数组的末尾补零，直至该byte[]数组的长度是8的整数倍
     * 
     * @param data
     * @return
     */
    public static byte[] addZeroRightToMod8(byte[] data) {
        byte[] addObject = new byte[] { 0 };
        if (data.length % 8 != 0) {
            while (data.length % 8 != 0) {
                data = copyarray(data, addObject);
            }
            return data;
        } else {
            return data;
        }
    }

    /**
     * 合并byte数组
     * 
     * @param data1
     * @param data2
     * @return
     */
    public static byte[] copyarray(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);

        return data3;
    }
}
