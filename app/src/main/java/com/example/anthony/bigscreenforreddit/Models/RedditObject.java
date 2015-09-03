package com.example.anthony.bigscreenforreddit.Models;

import com.example.anthony.bigscreenforreddit.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anthony on 8/12/2015.
 */
public abstract class RedditObject {

   public static List<RedditObject> redditObjectList;

    public static List<RedditObject> setupReddit() {

        redditObjectList = new ArrayList<RedditObject>();
        String title[] = {RedditLink.setupReddit()};
    }

}
