package org.monithon.monithon.activities;

import android.content.Context;
import android.webkit.JavascriptInterface;

/**
 * Created by MMo on 02/12/2014.
 */
public class WebViewInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    WebViewInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    //@JavascriptInterface
    //public void showToast(String toast) {
    //    Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    //}
}
