package com.example.anthony.bigscreenforreddit.Models;

/**
 * Created by Anthony on 8/12/2015.
 */
public class RedditLink extends RedditSubmission {
    private String domain;
    private String selftext_html;
    private String selftext;
    private String link_flair_text;
    private boolean clicked;
    private boolean hidden;
    private String thumbnail;
    private boolean is_self;
    private String permalink;
    private boolean stickied;
    private String url;
    public String title;
    private int num_comments;
    private boolean visited;

    public String getDomain() {
        return domain;
    }

    public String getSelftextHtml() {
        return selftext_html;
    }

    public String getSelftext() {
        return selftext;
    }

    public String getLinkFlairText() {
        return link_flair_text;
    }

    public boolean isClicked() {
        return clicked;
    }

    public boolean isHidden() {
        return hidden;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public boolean isSelf() {
        return is_self;
    }

    public String getPermalink() {
        return permalink;
    }

    public boolean isStickied() {
        return stickied;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public int getNumComments() {
        return num_comments;
    }

    public boolean isVisited() {
        return visited;
    }

    @Override
    public String toString(){
        return "redditlink{" +
                "title=" + title +
                "subreddit=" + subreddit + '\'' +
                "thumbnail=" + thumbnail + '\'' +
                '}';
    }

}
