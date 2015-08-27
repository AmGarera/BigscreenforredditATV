package com.example.anthony.bigscreenforreddit.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anthony.bigscreenforreddit.Models.RedditComment;
import com.example.anthony.bigscreenforreddit.R;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Anthony on 8/17/2015.
 */
public class CommentsAdapter extends ArrayAdapter<RedditComment> {

    public CommentsAdapter(Context context) {
        super(context, 0);
    }

    private static class ViewHolder {

        private final TextView scoreTextView;
        private final TextView bodyTextView;

        private ViewHolder(final View view) {
            scoreTextView = (TextView) view.findViewById(R.id.post_score);
            bodyTextView = (TextView) view.findViewById(R.id.post_details);
        }
    }

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RedditComment comment = getItem(position);
        final ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.view_comment, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.scoreTextView.setText(String.valueOf(comment.getScore()));
        vh.bodyTextView.setText(comment.getBody());
        final int padding = getContext().getResources()
                .getDimensionPixelSize(R.dimen.comment_padding);
        convertView.setPadding(comment.getDepth() * padding, 0, 0, 0);
        return convertView;
    }*/
}
