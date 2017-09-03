package com.codepath.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dphan on 9/1/17.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    private class ViewHolder {
        ImageView ivImage;
        TextView tvTitle;
        TextView tvOverview;

        private void populateData(Movie movie) {
            tvTitle.setText(movie.getOriginalTitle());
            tvOverview.setText(movie.getOverview());
            loadImage(movie);
        }

        private void loadImage(Movie movie) {
            int orientation = getContext().getResources().getConfiguration().orientation;

            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Picasso.with(getContext()).load(movie.getPosterPath()).into(ivImage);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Picasso.with(getContext()).load(movie.getBackdropPath()).into(ivImage);
            }
        }
    }

    public MovieAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        ViewHolder viewHolder = new ViewHolder();
        if (canRecycle(convertView)) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflateNewView(viewHolder, parent);
        }

        viewHolder.populateData(movie);

        return convertView;
    }

    private boolean canRecycle(View view) {
        return view != null;
    }

    private View inflateNewView(ViewHolder viewHolder, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.item_movie, parent, false);

        viewHolder.ivImage = view.findViewById(R.id.ivMovieImage);
        viewHolder.tvTitle = view.findViewById(R.id.tvTitle);
        viewHolder.tvOverview = view.findViewById(R.id.tvOverview);
        view.setTag(viewHolder);
        return view;
    }
}
