package org.monithon.monithon.activities;

import org.monithon.monithon.R;
import org.monithon.monithon.base.BaseActivity;
import org.monithon.monithon.services.MonithonService;
import org.monithon.monithon.util.GlobalState;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.melnykov.fab.FloatingActionButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class ProjectListActivity extends BaseActivity {

    @InjectView(R.id.add_els)
    FloatingActionButton add_els;

    @InjectView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainweb);
        ButterKnife.inject(this);

        final GlobalState state = (GlobalState)getApplication();

        add_els.setVisibility(View.GONE);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportMultipleWindows (false);
        webview.addJavascriptInterface(new WebViewInterface(this), "Android");
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.contains("projects/")){
                    add_els.setVisibility(View.VISIBLE);
                } else {
                    add_els.setVisibility(View.GONE);
                }
                state.setLast_url(url);
                view.loadUrl(url);
                return false;
            }

        });

        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });
        webview.loadUrl(state.getLast_url());

        Intent ms = new Intent(this, MonithonService.class);
        startService(ms);
    }

    @OnClick(R.id.add_els)
    public void add_elements() {
        Intent intent = new Intent (this, UploadDialogActivity.class);
        this.startActivity(intent);
    }
}
