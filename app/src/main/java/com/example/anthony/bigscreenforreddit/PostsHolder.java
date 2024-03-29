package com.example.anthony.bigscreenforreddit;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.example.anthony.bigscreenforreddit.Post;

/**
 * This is the class that creates Post objects out of the Reddit
 * API, and maintains a list of these postlist for other classes
 * to use.
 * package com.jdepths.alien;

/**
 * Created by Anthony on 4/29/2015.
 */
public class PostsHolder {

    /**
     * We will be fetching JSON data from the API.
     */
    private final String URL_TEMPLATE=
            "http://www.reddit.com/r/SUBREDDIT_NAME/"
                    +".json"
                    +"?after=AFTER";

    String subreddit;
    String url;
    String after;

    PostsHolder(String sr){
        subreddit=sr;
        after="";
        generateURL();
    }

    /**
     * Generates the actual URL from the template based on the
     * subreddit name and the 'after' property.
     */
    private void generateURL(){
        url=URL_TEMPLATE.replace("SUBREDDIT_NAME", subreddit);
        url=url.replace("AFTER", after);
    }

    /**
     * Returns a list of Post objects after fetching data from
     * Reddit using the JSON API.
     *
     * @return
     */
    List<Post> fetchPosts(){
        String raw= com.example.anthony.bigscreenforreddit.RemoteData.readContents(url);
        List<Post> list=new ArrayList<Post>();
        try{
            JSONObject data=new JSONObject(raw)
                    .getJSONObject("data");
            JSONArray children=data.getJSONArray("children");

            //Using this property we can fetch the next set of
            //postlist from the same subreddit
            after=data.getString("after");

            for(int i=0;i<children.length();i++){
                JSONObject cur=children.getJSONObject(i)
                        .getJSONObject("data");
                Post p=new Post();
                p.title=cur.optString("title");
                p.url=cur.optString("url");
                p.numComments=cur.optInt("num_comments");
                p.points=cur.optInt("score");
                p.author=cur.optString("author");
                p.subreddit=cur.optString("subreddit");
                p.permalink=cur.optString("permalink");
                p.domain=cur.optString("domain");
                p.id=cur.optString("id");
                if(p.title!=null)
                    list.add(p);
            }
        }catch(Exception e){
            Log.e("fetchPosts()",e.toString());
        }
        return list;
    }

    /**
     * This is to fetch the next set of postlist
     * using the 'after' property
     * @return
     */
    List<Post> fetchMorePosts(){
        generateURL();
        return fetchPosts();
    }
}