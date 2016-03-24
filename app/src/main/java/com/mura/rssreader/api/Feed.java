package com.mura.rssreader.api;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import java.io.Serializable;

/**
 * Created by mura on 16/03/24.
 */
@Root(name = "rss", strict = false)
public class Feed implements Serializable {
    @Element(name = "channel")
    private Channels mChannel;

    public Channels getmChannel() {
        return mChannel;
    }

    public Feed() {
    }

    public Feed(Channels mChannel) {
        this.mChannel = mChannel;
    }
}
