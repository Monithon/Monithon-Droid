package org.monithon.monithon.util;

import android.app.Application;

/**
 * Created by MMo on 03/12/2014.
 */
public class GlobalState extends Application {

    private String last_url = "http://monithon.org/";

    private Float lon;
    private Float lat;

    public String getLast_url() {
        return last_url;
    }

    public void setLast_url(String last_url) {
        this.last_url = last_url;
    }


}
