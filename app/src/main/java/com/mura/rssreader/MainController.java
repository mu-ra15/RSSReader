package com.mura.rssreader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
/**
 * Created by mura on 16/04/04.
 */
public class MainController {
    Context context;
    ImageView imgMain;

    MainController(Context context, ImageView imgMain) {
        this.context = context;
        this.imgMain = imgMain;
    }

    public void setImgMain(String path) {
        Glide.with(context).load(path).fitCenter().into(imgMain);
    }
}
