package com.example.anthony.bigscreenforreddit.Models;

/**
 * Created by Anthony on 8/12/2015.
 */
public class RedditResponse<T> {
    RedditResponse(T data) {
        this.data = data;
    }

    T data;

    public T getData() {
        return data;
    }

}
