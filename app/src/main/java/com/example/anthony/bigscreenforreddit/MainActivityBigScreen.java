/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.anthony.bigscreenforreddit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.Submission;
import net.dean.jraw.paginators.SubredditPaginator;


/*
 * MainActivity class that loads MainFragment
 */
public class MainActivityBigScreen extends FragmentActivity {
    private static final String TAG = "bsfr::MainActivity";
    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_big_screen);
        Log.d(TAG, "Created");
        addFragment();
    }
    void addFragment(){
        Fragment pf = PostsFragment.newInstance("askreddit");
        Log.d(TAG, "add a postwithimage fragment");
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_browse_fragment
                        , pf)
                .commit();
    }

    public void FrontPage(View view) {
        UserAgent myUserAgent = UserAgent.of("Android", "com.example.anthony.bigscreenforreddit", "0.1", "Atrix2noon");
        RedditClient reddit = new RedditClient(myUserAgent);
        for (Submission link : new SubredditPaginator(reddit).next()) {
            System.out.printf("%s? /r/%s - %s\n", link.getScore(), link.getSubredditName(), link.getTitle());
        }

    }
    Button button;
            public void onClick(View v) {
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
            }
        }
