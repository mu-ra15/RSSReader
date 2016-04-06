package com.mura.rssreader;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.concurrent.ExecutionException;

/**
 * Created by mura on 16/04/04.
 */
public class MainController {
    Context context;
    ImageView imgMain;
    String mPath;
    private Callbacks getCallback;

    public interface Callbacks {
        public void callbackMethod(Bitmap bitmap);
    }

    MainController(Context context, ImageView imgMain, Callbacks callback) {
        this.context = context;
        this.imgMain = imgMain;
        this.getCallback = callback;
    }

    public void setImgMain(String path) {
        Glide.with(context).load(path).fitCenter().into(imgMain);
        mPath = path;

    }

    public void getImgMain(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Bitmap Bitmap = Glide.with(context).load(mPath).asBitmap().into(-1, -1).get();
                    getCallback.callbackMethod(Bitmap);
                    Log.d("TEST", "aaaa");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
}
