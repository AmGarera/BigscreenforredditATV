package com.example.anthony.bigscreenforreddit;


/**
 * This is a class that holds the data of the JSON objects
 * returned by the //Reddit API.
 *
 * @author Hathy
 *//**
 * Created by Anthony on 4/29/2015.
 */
public class Post {
    String subreddit;
    String title;
    String author;
    int points;
    int numComments;
    String permalink;
    String url;
    String domain;
    String id;

    String getDetails(){
        String details=author
                +" posted this and got "
                +numComments
                +" replies";
        return details;
    }

    String getTitle(){
        return title;
    }

    String getScore(){
        return Integer.toString(points);
    }
}

