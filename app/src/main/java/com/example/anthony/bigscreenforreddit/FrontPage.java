package com.example.anthony.bigscreenforreddit;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.paginators.SubredditPaginator;

/**
 * Created by AnthonyGarera on 6/24/15.
 */
//public class FrontPage {
//    UserAgent myUserAgent = UserAgent.of("Android", "com.example.anthony.bigscreenforreddit", "0.1", "Atrix2noon");
//    RedditClient reddit = new RedditClient(myUserAgent);
//    SubredditPaginator frontPage = new SubredditPaginator(reddit);
//
//    Listing<Submission> submissions = frontPage.next();
//    for (Submission s : submissions) {
//        // Print some basic stats about the posts
//        System.out.printf("[/r/%s - %s karma] %s\n", s.getSubredditName(), s.getScore(), s.getTitle());
//    }
//    }
