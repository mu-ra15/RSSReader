package com.mura.rssreader.api;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by mura on 16/03/24.
 */
public interface RssAdapter {
    @GET("/feed")
    void getItems(Callback<Feed> callback);
}
