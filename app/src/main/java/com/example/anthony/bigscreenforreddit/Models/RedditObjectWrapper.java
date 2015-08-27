package com.example.anthony.bigscreenforreddit.Models;

import com.google.gson.JsonElement;

/**
 * Created by Anthony on 8/12/2015.
 */
public class RedditObjectWrapper {
    RedditType kind;
    JsonElement data;

    public RedditType getKind() {
        return kind;
    }

    public JsonElement getData() {
        return data;
    }

}
