package org.monithon.monithon.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONObject;
import org.monithon.monithon.R;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UploadService extends IntentService {

    Context that = this;

    public UploadService() {
        super("UploadService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle x = intent.getExtras();
        String url  =x.getString("url");
        String path = x.getString("path");
        Float lon = x.getFloat("lon");
        Float lat = x.getFloat("lat");
        String date = x.getString("date");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("file", new File(path));
        params.put("lat",lat);
        params.put("lon",lon);
        params.put("date", date);
        params.put("filetype", "img");
        params.put("filename", path.split("/")[path.split("/").length]);



        AQuery aq = new AQuery(this);
        aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>(){
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                NotificationCompat.Builder b = new NotificationCompat.Builder(that);
                b.setContentTitle("Monithon Upload Completed");
                b.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
            }
        });
    }
}
