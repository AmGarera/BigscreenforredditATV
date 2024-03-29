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

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anthony.bigscreenforreddit.Models.RedditListing;
import com.example.anthony.bigscreenforreddit.Models.RedditObject;
import com.example.anthony.bigscreenforreddit.Models.RedditObjectWrapper;
import com.example.anthony.bigscreenforreddit.Models.RedditResponse;
import com.example.anthony.bigscreenforreddit.PostInfo;
import com.example.anthony.bigscreenforreddit.PostList;
import com.example.anthony.bigscreenforreddit.PostStuff;

import com.example.anthony.bigscreenforreddit.UI.LinkAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


public class MainFragment extends BrowseFragment {
    private static final String TAG = "MainFragment";

    private static final int CARD_WIDTH_IN_DP = 260;
    private static final int CARD_HEIGHT_IN_DP = 150;
    private static final int BACKGROUND_UPDATE_DELAY = 300;
    private static final int GRID_ITEM_WIDTH = 300;
    private static final int GRID_ITEM_HEIGHT = 200;
    private static final int NUM_ROWS = 50;
    private static final int NUM_COLS = 15;
    private static final int SUBREDDIT_ROW = 1;

    private ArrayObjectAdapter mRowsAdapter;
    private Drawable mDefaultBackground;
    private Target mBackgroundTarget;
    private DisplayMetrics mMetrics;
    private Timer mBackgroundTimer;
    private final Handler mHandler = new Handler();
    private URI mBackgroundURI;
    Movie mMovie;
    CardPresenter mCardPresenter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onActivityCreated(savedInstanceState);

        prepareBackgroundManager();

        setupUIElements();

        loadRows();

        setupEventListeners();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mBackgroundTimer) {
            Log.d(TAG, "onDestroy: " + mBackgroundTimer.toString());
            mBackgroundTimer.cancel();
        }
    }

    private void setupUIElements() {
        // setBadgeDrawable(getActivity().getResources().getDrawable(
        // R.drawable.videos_by_google_banner));
        setTitle(getString(R.string.browse_title)); // Badge, when set, takes precedent
        // over title
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        // set fastLane (or headers) background color
        setBrandColor(getResources().getColor(R.color.accent_material_dark));
        // set search icon color
        setSearchAffordanceColor(getResources().getColor(R.color.accent_material_light));
    }

    private void loadRows() {

        /*mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        *//* GridItemPresenter *//*
        HeaderItem gridItemPresenterHeader = new HeaderItem(0, "GridItemPresenter");

        GridItemPresenter mGridPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        gridRowAdapter.add("ITEM 1");
        gridRowAdapter.add("ITEM 2");
        gridRowAdapter.add("ITEM 3");
        mRowsAdapter.add(new ListRow(gridItemPresenterHeader, gridRowAdapter));

        *//* CardPresenter *//*
        HeaderItem cardPresenterHeader = new HeaderItem(1, "CardPresenter");
        CardPresenter cardPresenter = new CardPresenter();
        ArrayObjectAdapter cardRowAdapter = new ArrayObjectAdapter(cardPresenter);

        for(int i=0; i<10; i++) {
            Movie movie = new Movie();
            if(i%3 == 0) {
                movie.setCardImageUrl("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02580.jpg");
            } else if (i%3 == 1) {
                movie.setCardImageUrl("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02630.jpg");
            } else {
                movie.setCardImageUrl("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02529.jpg");
            }
            movie.setTitle("title" + i);
            movie.setStudio("studio" + i);
            cardRowAdapter.add(movie);

        mRowsAdapter.add(new ListRow(cardPresenterHeader, cardRowAdapter));

        *//* Set *//*
        setAdapter(mRowsAdapter); }*/



        //List<PostInfo> list = PostList.setupReddit();
        List<RedditObject> list = RedditObjectWrapper.class;
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        mCardPresenter = new CardPresenter();

        int i;
        for (i = 0; i < NUM_ROWS; i++) {
            if (i != 0) {
                Collections.shuffle(list);
            }
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(mCardPresenter);
            for (int j = 0; j < NUM_COLS; j++) {
                listRowAdapter.add(list.get(j % 5));
            }
//            HeaderItem header = new HeaderItem(i, MovieList.MOVIE_CATEGORY[i], null);
            HeaderItem header = new HeaderItem(i, Constants.Reddit.DEFAULT_SUBREDDITS[i]);
            mRowsAdapter.add(new ListRow(header, listRowAdapter));
        }

//        HeaderItem gridHeader = new HeaderItem(i, "PREFERENCES", null);
        HeaderItem gridHeader = new HeaderItem(i, "PREFERENCES");

        GridItemPresenter mGridPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        gridRowAdapter.add(getResources().getString(R.string.grid_view));
        gridRowAdapter.add(getString(R.string.error_fragment));
        gridRowAdapter.add(getResources().getString(R.string.personal_settings));
        gridRowAdapter.add(getString(R.string.Login));
        mRowsAdapter.add(new ListRow(gridHeader, gridRowAdapter));

        setAdapter(mRowsAdapter);

    }
//    private void addCardInfoElementsToRowsAdapter(int title, List<CardInfo> elements,
//                                                  ArrayObjectAdapter rowsAdapter, Presenter presenter, int id) {
//        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(presenter);
//        for (Object element : elements) {
//            listRowAdapter.add(element);
//        }
//        HeaderItem header = new HeaderItem(id, getString(title), null);
//        rowsAdapter.add(new ListRow(header, listRowAdapter));
//    }
//
//    private void addImageInfoElementsToRowAdapter(int title, List<ImageInfo> elements,
//                                                  ArrayObjectAdapter rowsAdapter, Presenter presenter, int id) {
//        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(presenter);
//        for (Object element : elements) {
//            listRowAdapter.add(element);
//        }
//        HeaderItem header = new HeaderItem(id, getString(title), null);
//        rowsAdapter.add(new ListRow(header, listRowAdapter));
//    }
//
//    private void addIconInfoElementsToRowAdapter(String title, List<IconInfo> preferences,
//                                                 ArrayObjectAdapter rowsAdapter, Presenter presenter, int id) {
//        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(presenter);
//        for (IconInfo iconInfo : preferences) {
//            listRowAdapter.add(iconInfo);
//        }
//        rowsAdapter.add(new ListRow(new HeaderItem(id, title, ""), listRowAdapter));
//    }
//
//
//    @Override public void showMainInformation(List<PostInfo> subRedditName){
//
//        ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
//
//        CardPresenter bigCardPresentor = new CardPresenter(CARD_WIDTH_IN_DP,CARD_HEIGHT_IN_DP);
//        CardPresenter smallCardPresentor = new CardPresenter();
//
//
//        addCardInfoElementsToRowsAdapter(R.string.subReddit_name, subRedditName, rowsAdapter, smallCardPresentor, SUBREDDIT_ROW);
//
//    };

    private void prepareBackgroundManager() {

        BackgroundManager backgroundManager = BackgroundManager.getInstance(getActivity());
        backgroundManager.attach(getActivity().getWindow());
        mBackgroundTarget = new PicassoBackgroundManagerTarget(backgroundManager);

        mDefaultBackground = getResources().getDrawable(R.drawable.default_background);

        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }



    private void setupEventListeners() {
        setOnSearchClickedListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Fuck searching", Toast.LENGTH_LONG)
                        .show();
            }
        });

        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {

            if (item instanceof Movie) {
                Movie movie = (Movie) item;
                Log.d(TAG, "Item: " + item.toString());
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.MOVIE, movie);

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
                        DetailsActivity.SHARED_ELEMENT_NAME).toBundle();
                getActivity().startActivity(intent, bundle);
            } else if (item instanceof String) {
//                if (((String) item).indexOf(getString(R.string.Login)) >= 0) {
//                    Intent intent = new Intent(getActivity(), OAuth.class);
//                    getActivity().startActivity(intent);
//                } else {
//                    Toast.makeText(getActivity(), ((String) item), Toast.LENGTH_SHORT)
//                            .show();
//                }
            }
        }
    }


    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
            if (item instanceof Movie) {
                mBackgroundURI = ((Movie) item).getBackgroundImageURI();
                startBackgroundTimer();
            }

        }
    }

    protected void setDefaultBackground(Drawable background) {
        mDefaultBackground = background;
    }

    protected void setDefaultBackground(int resourceId) {
        mDefaultBackground = getResources().getDrawable(resourceId);
    }

    protected void updateBackground(URI uri) {
        Picasso.with(getActivity())
                .load(uri.toString())
                .resize(mMetrics.widthPixels, mMetrics.heightPixels)
                .centerCrop()
                .error(mDefaultBackground)
                .into(mBackgroundTarget);
    }

    protected void updateBackground(Drawable drawable) {
        BackgroundManager.getInstance(getActivity()).setDrawable(drawable);
    }

    protected void clearBackground() {
        BackgroundManager.getInstance(getActivity()).setDrawable(mDefaultBackground);
    }

    private void startBackgroundTimer() {
        if (null != mBackgroundTimer) {
            mBackgroundTimer.cancel();
        }
        mBackgroundTimer = new Timer();
        mBackgroundTimer.schedule(new UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY);
    }

    private class UpdateBackgroundTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mBackgroundURI != null) {
                        updateBackground(mBackgroundURI);
                    }
                }
            });

        }
    }

    private class GridItemPresenter extends Presenter {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            TextView view = new TextView(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.setBackgroundColor(getResources().getColor(R.color.default_background));
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Object item) {
            ((TextView) viewHolder.view).setText((String) item);
        }

        @Override
        public void onUnbindViewHolder(ViewHolder viewHolder) {
        }
    }

}
