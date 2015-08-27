package com.example.anthony.bigscreenforreddit.Models;


import java.util.List;

/**
 * Created by Anthony on 8/12/2015.
 */
public class RedditListing extends RedditObject {
    String modhash;
    String after;
    String before;
    List<RedditObject> children;

    public String getModhash() {
        return modhash;
    }

    public String getAfter() {
        return after;
    }

    public String getBefore() {
        return before;
    }

    public List<RedditObject> getChildren() {
        return children;
    }

}
