package com.example.anthony.bigscreenforreddit;

import com.google.gson.Gson;
 import com.google.gson.GsonBuilder;
 import com.example.anthony.bigscreenforreddit.Models.RedditListing;
 import com.example.anthony.bigscreenforreddit.Models.RedditObject;
 import com.example.anthony.bigscreenforreddit.Models.RedditResponse;
import com.example.anthony.bigscreenforreddit.Models.RedditObjectDeserializer;
import com.example.anthony.bigscreenforreddit.Models.DateTimeDeserializer;
import org.joda.time.DateTime;
import java.util.List;


import retrofit.Callback;
 import retrofit.RestAdapter;
 import retrofit.converter.GsonConverter;
 import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Anthony on 8/12/2015.
 */
public interface RedditService {
    @GET("/r/{subreddit}/comments/{id}.json")
    List<RedditResponse<RedditListing>> getComments(
            @Path("subreddit") String subreddit,
            @Path("id") String id
    );

    @GET("/r/{subreddit}/comments/{id}.json")
    void getComments(
            @Path("subreddit") String subreddit,
            @Path("id") String id,
            Callback<List<RedditResponse<RedditListing>>> callback
    );

    @GET("/r/{subreddit}.json")
    RedditResponse<RedditListing> getSubreddit(@Path("subreddit") String subreddit);

    @GET("/r/{subreddit}.json")
    void getSubreddit(
            @Path("subreddit") String subreddit,
            Callback<RedditResponse<RedditListing>> callback);

    public static class Implementation {
        public static RedditService get() {
            return getBuilder()
                    .build()
                    .create(RedditService.class);
        }

        static RestAdapter.Builder getBuilder() {
            return new RestAdapter.Builder()
                    .setConverter(new GsonConverter(getGson()))
                    .setEndpoint("http://www.reddit.com");
        }

        private static Gson getGson() {
            return new GsonBuilder()
                    .registerTypeAdapter(RedditObject.class, new RedditObjectDeserializer())
                    .registerTypeAdapter(DateTime.class, new DateTimeDeserializer())
                    .create();
        }
    }

}
