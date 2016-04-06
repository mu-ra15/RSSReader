package com.mura.rssreader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.define.Define;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetailActivity extends AppCompatActivity implements MainController.Callbacks {
    private static String TAG = "DetailActivity";

    ArrayList<String> path = new ArrayList<>();
    ImageView imgMain;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    GallaryAdapter mainAdapter;
    MainController mainController;
    ImageView mDragView;
    Bitmap mBgImg;
    Bitmap mFgImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back_mtrl);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgMain = (ImageView) findViewById(R.id.img_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mainController = new MainController(this, imgMain, this);
        mainAdapter = new GallaryAdapter(this, mainController, path);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mainAdapter);

        // ドラッグ対象Viewとイベント処理クラスを紐付ける
        mDragView = (ImageView) findViewById(R.id.logo_img);
        DragViewListener listener = new DragViewListener(mDragView);
        mDragView.setOnTouchListener(listener);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFgImg = ((BitmapDrawable)mDragView.getDrawable()).getBitmap();
                mainController.getImgMain();
            }
        });

        FishBun.with(DetailActivity.this)
                .setPickerCount(20)
                .setPickerSpanCount(3)
                .setActionBarColor(Color.parseColor("#3F51B5"), Color.parseColor("#303F9F"))
                .textOnImagesSelectionLimitReached("Limit Reached!")
                .textOnNothingSelected("Nothing Selected")
                .setArrayPaths(path)
                .setButtonInAlbumActiviy(true)
                .setCamera(true)
                .startAlbum();
    }

    // 画像取得コールバック
    @Override
    public void callbackMethod(Bitmap bgImg) {
        Bitmap combineImages = combineImages(bgImg, mFgImg);
        saveBitamap(combineImages);
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageData) {
        super.onActivityResult(requestCode, resultCode, imageData);
        Log.d(TAG, "onActivityResult");
        switch (requestCode) {
            case Define.ALBUM_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    path = imageData.getStringArrayListExtra(Define.INTENT_PATH);
                    mainAdapter.changePath(path);
                    break;
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public Bitmap combineImages(Bitmap bgd, Bitmap fg) {
        Bitmap cs;

        int width = bgd.getWidth() > fg.getWidth()
                ? bgd.getWidth() : fg.getWidth();
        int height = bgd.getHeight() > fg.getHeight()
                ? bgd.getHeight() : fg.getHeight();

        cs = Bitmap.createBitmap( width, height,
                Bitmap.Config.ARGB_8888 );

        Canvas comboImage = new Canvas( cs );
        comboImage.drawBitmap(bgd, new Matrix(), null );
        comboImage.drawBitmap( fg, new Matrix(), null );

        return cs;
    }

    private void saveBitamap(Bitmap bitmap){
        try {
            // sdcardフォルダを指定
            File root = Environment.getExternalStorageDirectory();

            // 日付でファイル名を作成　
            Date mDate = new Date();
            SimpleDateFormat fileName = new SimpleDateFormat("yyyyMMdd_HHmmss");

            // 保存処理開始
            FileOutputStream fos = null;
            fos = new FileOutputStream(new File(root, fileName.format(mDate) + ".jpg"));

            // jpegで保存
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            // 保存処理終了
            fos.close();
        } catch (Exception e) {
            Log.e("Error", "" + e.toString());
        }
    }
}
