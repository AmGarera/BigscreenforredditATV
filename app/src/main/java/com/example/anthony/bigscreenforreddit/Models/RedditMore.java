package com.example.anthony.bigscreenforreddit.Models;

import java.util.List;
/**
 * Created by Anthony on 8/12/2015.
 */
public class RedditMore extends RedditObject {
    int count;
    String parent_id;
    String id;
    String name;
    List<String> children;

    public int getCount() {
        return count;
    }

    public String getParentId() {
        return parent_id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getChildren() {
        return children;
    }


}
