package com.photo.testphoto;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.photo.testphoto.view.MosaicView;
import com.photo.testphoto.view.PopMenuList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProMosaicActivity extends AppCompatActivity {


    public static final String TAG = "ProMosaic";
    private static final int REQ_PICK_IMAGE = 1984;

    private MosaicView mvImage;

    private Button btClear;
    private Button btLoad;
    private Button btSave;
    private Button btEffect;
    private Button btMode;
    private Button btAbout;
    private Button btErase;

    private PopMenuList effectList;
    private PopMenuList modeList;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mosaic);

        mvImage = (MosaicView) findViewById(R.id.iv_content);

        btLoad = (Button) findViewById(R.id.bt_load);
        btClear = (Button) findViewById(R.id.bt_clear);
        btSave = (Button) findViewById(R.id.bt_save);
        btAbout = (Button) findViewById(R.id.bt_about);
        btMode = (Button) findViewById(R.id.bt_mode);
        btEffect = (Button) findViewById(R.id.bt_effect);
        btErase = (Button) findViewById(R.id.bt_erase);
        btLoad.setOnClickListener(cl);
        btClear.setOnClickListener(cl);
        btSave.setOnClickListener(cl);
        btAbout.setOnClickListener(cl);
        btEffect.setOnClickListener(cl);
        btMode.setOnClickListener(cl);
        btErase.setOnClickListener(cl);
    }

    private View.OnClickListener cl = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (view.equals(btLoad)) {
//                Intent intent = new Intent(ProMosaicActivity.this, ImageGridActivity.class);
//                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, false); // 是否是直接打开相机
//                startActivityForResult(intent, REQ_PICK_IMAGE);
            } else if (view.equals(btClear)) {
                mvImage.clear();
                mvImage.setErase(false);
            } else if (view.equals(btSave)) {
                boolean succced = mvImage.save();
                String text = "save image "
                        + (succced ? " succeed" : " failed");
                Toast.makeText(view.getContext(), text, Toast.LENGTH_SHORT)
                        .show();
            } else if (view.equals(btEffect)) {
                initEffectList();
                effectList.show(btEffect);
            } else if (view.equals(btMode)) {
                initModeList();
                modeList.show(btMode);
            } else if (view.equals(btAbout)) {
//                Intent intent = new Intent(ProMosaicActivity.this, AboutMe.class);
//                startActivity(intent);
            } else if (view.equals(btErase)) {
                mvImage.setErase(true);
            }
        }
    };

    private void initEffectList() {
        if (effectList != null) {
            return;
        }
        effectList = new PopMenuList(this);
        List<PopMenuList.MenuItem> items = new LinkedList<PopMenuList.MenuItem>();
        items.add(new PopMenuList.MenuItem(null, getResources().getString(
                R.string.effect_grid)));
        items.add(new PopMenuList.MenuItem(null, getResources().getString(
                R.string.effect_blur)));
        items.add(new PopMenuList.MenuItem(null, getResources().getString(
                R.string.effect_color)));
        effectList.setItems(items);
        effectList.setListMenuListener(el);
    }

    private PopMenuList.ListMenuListener el = new PopMenuList.ListMenuListener() {

        @Override
        public void onItemClick(int index) {
            if (index == 0) {
                mvImage.setEffect(MosaicView.Effect.GRID);
            } else if (index == 1) {mvImage.setEffect(MosaicView.Effect.BLUR);

            } else if (index == 2) {
                mvImage.setMosaicColor(0xFF4D4D4D);
                mvImage.setEffect(MosaicView.Effect.COLOR);
            }
        }
    };

    private void initModeList() {
        if (modeList != null) {
            return;
        }
        modeList = new PopMenuList(this);
        List<PopMenuList.MenuItem> items = new LinkedList<PopMenuList.MenuItem>();
        items.add(new PopMenuList.MenuItem(null, getResources().getString(
                R.string.mode_path)));
        items.add(new PopMenuList.MenuItem(null, getResources().getString(
                R.string.mode_grid)));
        modeList.setItems(items);
        modeList.setListMenuListener(ml);
    }

    private PopMenuList.ListMenuListener ml = new PopMenuList.ListMenuListener() {

        @Override
        public void onItemClick(int index) {
            if (index == 0) {
                mvImage.setMode(MosaicView.Mode.PATH);
            } else if (index == 1) {
                mvImage.setMode(MosaicView.Mode.GRID);
            }
        }
    };

    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        // user cancelled
        if (resultCode != Activity.RESULT_OK) {
            Log.d(TAG, "user cancelled");
            return;
        }

//        if (reqCode == REQ_PICK_IMAGE) {
//            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//            if (images != null && images.size() > 0) {
//                mvImage.setSrcPath(images.get(0).path);
//            }
//
//        }
    }
    
}
