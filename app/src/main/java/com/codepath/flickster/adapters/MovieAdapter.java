package com.codepath.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.flickster.R;
import com.codepath.flickster.models.Config;
import com.codepath.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dphan on 9/1/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Config config;
    Context context;
    ArrayList<Movie> movies;

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable @BindView(R.id.ivPosterImg) ImageView ivPoster;
        @Nullable @BindView(R.id.ivBackdropImg) ImageView ivBackdrop;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvOverview) TextView tvOverview;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_movie, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        populateData(holder, position);
    }

    private void populateData(ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.tvTitle.setText(movie.getOriginalTitle());
        holder.tvOverview.setText(movie.getOverview());

        String imgUrl = buildUrl(movie);
        ImageView imageView = getImageView(holder);

        loadImage(imgUrl, imageView);
    }

    private void loadImage(String path, ImageView imageView) {

        Glide.with(context)
                .load(path)
                .bitmapTransform(new RoundedCornersTransformation(context, 25, 0))
                .placeholder(R.drawable.ic_movie)
                .into(imageView);
    }

    private String buildUrl(Movie movie) {
        if (isPortrait()) {
            return config.createImageUrl(movie.getPosterPath(), config.getPosterSize());
        } else {
            return config.createImageUrl(movie.getBackdropPath(), config.getBackdropSize());
        }
    }

    private ImageView getImageView(ViewHolder holder) {
        if (isPortrait()) {
            return holder.ivPoster;
        } else {
            return holder.ivBackdrop;
        }
    }

    private boolean isPortrait() {
        return context.getResources()
                .getConfiguration()
                .orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}
