package com.br.perguntaq;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private WebView pq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pq = findViewById(R.id.pqweb);
        pq.getSettings().setJavaScriptEnabled(true);
        pq.setFocusable(true);
        pq.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        pq.getSettings().setDomStorageEnabled(true);
        pq.getSettings().setAppCacheEnabled(true);
        pq.setWebViewClient(new WebViewClient());
        pq.setWebChromeClient(new pqsite());
        pq.loadUrl("https://perguntaq.com.br/");

    }


    private class pqsite extends WebChromeClient {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        pqsite() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView()
        {
            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846);
        }
    }
    @Override
    public void onBackPressed() {
        if (pq.canGoBack()) {
            pq.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
