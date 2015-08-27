package com.example.anthony.bigscreenforreddit;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.example.anthony.bigscreenforreddit.Models.RedditLink;
import com.example.anthony.bigscreenforreddit.Models.RedditListing;
import com.example.anthony.bigscreenforreddit.Models.RedditObject;
import com.example.anthony.bigscreenforreddit.Models.RedditResponse;
import com.example.anthony.bigscreenforreddit.UI.LinkAdapter;

/**
 * Created by Anthony on 8/17/2015.
 */
    public class SubredditActivity extends Activity {
        public static final String EXTRA_SUBREDDIT = "subreddit";
        private LinkAdapter mLinkAdapter;
        private ProgressDialog mProgressDialog;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            String subreddit = getIntent().getStringExtra(EXTRA_SUBREDDIT);
            setContentView(R.layout.activity_subreddit);

            ListView mListView = (ListView) findViewById(android.R.id.list);
            mLinkAdapter = new LinkAdapter(this);
            mListView.setAdapter(mLinkAdapter);
            mListView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            showComments(mLinkAdapter.getItem(position));
                        }
                    });

            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading subreddit...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

            RedditService.Implementation.get().getSubreddit(subreddit,
                    new Callback<RedditResponse<RedditListing>>() {
                        @Override
                        public void success(RedditResponse<RedditListing> listing, Response response) {
                            if (isDestroyed()) return;
                            mProgressDialog.dismiss();
                            onListingReceived(listing);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            if (isDestroyed()) return;
                            mProgressDialog.dismiss();
                            new AlertDialog.Builder(SubredditActivity.this)
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

        private void showComments(RedditLink item) {
            Intent intent = new Intent(this, CommentsActivity.class);
            intent.putExtra(CommentsActivity.EXTRA_SUBREDDIT, item.getSubreddit());
            intent.putExtra(CommentsActivity.EXTRA_LINK_ID, item.getId());
            startActivity(intent);
        }

        private void onListingReceived(RedditResponse<RedditListing> listing) {
            mLinkAdapter.clear();
            for (RedditObject redditObject : listing.getData().getChildren()) {
                RedditLink link = (RedditLink) redditObject;
                mLinkAdapter.add(link);
            }
        }
    }
