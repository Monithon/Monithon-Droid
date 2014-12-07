package org.monithon.monithon.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;

import org.json.JSONObject;
import org.monithon.monithon.R;
import org.monithon.monithon.services.UploadService;
import org.monithon.monithon.util.GlobalState;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class UploadDialogActivity extends FragmentActivity implements ImageChooserListener {

    private AQuery aq;
    private final Activity that = this;
    private String to_upload ;

    @InjectView(R.id.calendar)
    Button calendar;

    @InjectView(R.id.pics)
    LinearLayout pics;

    @InjectView(R.id.selected_pic)
    ImageView selected_pic;

    @OnClick(R.id.calendar)
    public void showDatePicker(){
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setTarget(calendar);
        newFragment.show(getFragmentManager(), "timePicker");
    }

    ImageChooserManager imageChooserManager;

    @OnClick(R.id.add_img)
    public void add_img(){
        imageChooserManager = new ImageChooserManager(this, ChooserType.REQUEST_PICK_PICTURE);
        imageChooserManager.setImageChooserListener(this);
        try {
            imageChooserManager.choose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.do_save)
    public void do_save(){
        aq = new AQuery(this);
        aq.ajax("", JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                GlobalState gs = (GlobalState)getApplication();
                Intent i  = new Intent(that, UploadService.class);
                 i.putExtra("url", gs.getLast_url()+"/upload");
                i.putExtra("path", to_upload);
                i.putExtra("lon", 1.4);
                i.putExtra("lat",1.3);
                startService(i);
                that.finish();
            }
        });
    }

    @OnClick(R.id.do_cancel)
    public void do_cancel(){
        this.finish();
    }

    @OnClick(R.id.add_cam)
    public void add_cam(){
        imageChooserManager = new ImageChooserManager(this, ChooserType.REQUEST_CAPTURE_PICTURE);
        imageChooserManager.setImageChooserListener(this);
        try {
            imageChooserManager.choose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK &&
                (requestCode == ChooserType.REQUEST_PICK_PICTURE||
                        requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            imageChooserManager.submit(requestCode, data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_dialog);
        ButterKnife.inject(this);
        this.setTitle("Upload Picture");
    }


    @Override
    public void onImageChosen(final ChosenImage chosenImage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (chosenImage != null) {
                    pics.setVisibility(View.GONE);
                    to_upload = chosenImage.getFilePathOriginal();
                    File imgFile = new File(chosenImage.getFilePathOriginal());

                    if(imgFile.exists()){
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        selected_pic.setImageBitmap(myBitmap);

                    }
                    selected_pic.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onError(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(that, s, Toast.LENGTH_LONG);
            }
        });
    }
}
