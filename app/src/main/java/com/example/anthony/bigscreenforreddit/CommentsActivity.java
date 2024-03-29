package com.example.anthony.bigscreenforreddit;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anthony.bigscreenforreddit.Models.RedditComment;
import com.example.anthony.bigscreenforreddit.Models.RedditLink;
import com.example.anthony.bigscreenforreddit.Models.RedditListing;
import com.example.anthony.bigscreenforreddit.Models.RedditObject;
import com.example.anthony.bigscreenforreddit.Models.RedditResponse;
import com.example.anthony.bigscreenforreddit.UI.CommentsAdapter;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Anthony on 8/17/2015.
 */
public class CommentsActivity extends Activity {
    public static final String EXTRA_LINK_ID = "link_id";
    public static final String EXTRA_SUBREDDIT = "subreddit";
    private ListView mListView;
    private CommentsAdapter mAdapter;
    private ViewGroup mContentView;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments);
        String subreddit = getIntent().getStringExtra(EXTRA_SUBREDDIT);
        String linkId = getIntent().getStringExtra(EXTRA_LINK_ID);

        mListView = (ListView) findViewById(android.R.id.list);
        mAdapter = new CommentsAdapter(this);
        mListView.setAdapter(mAdapter);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Loading comments...");
        mProgressDialog.show();
        RedditService.Implementation.get().getComments(subreddit, linkId,
                new Callback<List<RedditResponse<RedditListing>>>() {
                    @Override
                    public void success(List<RedditResponse<RedditListing>> listings, Response response) {
                        if (isDestroyed()) return;
//                        mProgressDialog.dismiss();
                        onListingsReceived(listings);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (isDestroyed()) return;
                        mProgressDialog.dismiss();
                        new AlertDialog.Builder(CommentsActivity.this)
                                .setMessage("Loading failed :(")
                                .setCancelable(false)
                                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProgressDialog.dismiss();
    }

    private void onListingsReceived(List<RedditResponse<RedditListing>> listings) {
        RedditObject linkObject = listings.get(0).getData().getChildren().get(0);
        RedditLink link = (RedditLink) linkObject;
        TextView scoreTextView = (TextView) findViewById(R.id.post_score);
        TextView titleTextView = (TextView) findViewById(R.id.post_title);
        //TextView authorTextView = (TextView) findViewById(R.id.author_textview);

        scoreTextView.setText(String.valueOf(link.getScore()));
        titleTextView.setText(String.valueOf(link.getTitle()));
        //authorTextView.setText(String.valueOf(link.getAuthor()));

        addCommentsToAdapter(listings.get(1).getData().getChildren());
    }

    private void addCommentsToAdapter(List<RedditObject> children) {
        for (RedditObject child : children) {
            if (child instanceof RedditComment) {
                RedditComment comment = (RedditComment) child;
                addCommentToAdapter(comment);
            }
        }
    }

    private void addCommentToAdapter(RedditComment comment) {
        mAdapter.add(comment);
        if (comment.getReplies() != null) {
            RedditListing repliesListing = (RedditListing) comment.getReplies();
            for (RedditObject redditObject : repliesListing.getChildren()) {
                // replies can be of type 'more' but we're not going to worry about that here
                if (redditObject instanceof RedditComment) {
                    RedditComment childComment = (RedditComment) redditObject;
                    // increment the depth of the child so it can be indented
                    childComment.setDepth(comment.getDepth() + 1);
                    addCommentToAdapter(childComment);
                }
            }
        }
    }
}
