package com.mura.rssreader.api;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mura on 16/03/24.
 */
@Root(name = "channel", strict = false)
public class Channels implements Serializable {
    @ElementList(inline = true, name="item")
    private List<FeedItem> mFeedItems;

    public List<FeedItem> getFeedItems() {
        return mFeedItems;
    }

    public Channels() {
    }

    public Channels(List<FeedItem> mFeedItems) {
        this.mFeedItems = mFeedItems;
    }
}
