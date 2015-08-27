package com.example.anthony.bigscreenforreddit.Models;

import com.example.anthony.bigscreenforreddit.Models.RedditComment;
import com.example.anthony.bigscreenforreddit.Models.RedditLink;
import com.example.anthony.bigscreenforreddit.Models.RedditListing;
import com.example.anthony.bigscreenforreddit.Models.RedditMore;
/**
 * Created by Anthony on 8/12/2015.
 */
public enum RedditType {
    t1(RedditComment.class),
    t3(RedditLink.class),
    Listing(RedditListing.class),
    more(RedditMore.class);

    private final Class mCls;

    RedditType(Class cls) {
        mCls = cls;
    }

    public Class getDerivedClass() {
        return mCls;
    }
}
