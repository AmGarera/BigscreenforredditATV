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
import android.widget.EditText;


/*
 * MainActivity class that loads MainFragment
 */
public class MainActivityBigScreen extends FragmentActivity {
    private static final String TAG = "bsfr::MainActivity";
    Button b1;
    EditText ed1,ed2;
    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_big_screen);
        Log.d(TAG, "Created");
        addFragment();
        b1 = (Button) findViewById(R.id.btn);
        ed1 = (EditText) findViewById(R.id.editText2);
        ed2 = (EditText) findViewById(R.id.editText1);

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


        }

