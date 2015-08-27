package com.example.pulkit.materialdesign.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.pulkit.materialdesign.R;
import com.example.pulkit.materialdesign.model.Movie;
import com.example.pulkit.materialdesign.network.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by pulkit on 8/27/15.
 */
public class BoxOfficeAdapter extends RecyclerView.Adapter<BoxOfficeAdapter.ViewHolder> {

    private ArrayList<Movie> listMovies = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;

    public BoxOfficeAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getsInstance();
        imageLoader = volleySingleton.getImageLoader();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.movie_box_office, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void setMovieList(ArrayList<Movie> listMovies) {
        this.listMovies = listMovies;
        notifyItemRangeChanged(0, listMovies.size());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Movie currentMovie = listMovies.get(position);
        holder.movieTitle.setText(currentMovie.getTitle());
        holder.movieDate.setText(currentMovie.getReleaseDateTheater().toString());
        holder.movieRating.setRating(currentMovie.getAudienceScore() / 20.0F);
        String urlThumbnail = currentMovie.getUrlThumbnail();
        if (urlThumbnail != null) {
            imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.movieThumbnail.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView movieThumbnail;
        private TextView movieTitle;
        private TextView movieDate;
        private RatingBar movieRating;

        public ViewHolder(View itemView) {
            super(itemView);
            movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            movieDate = (TextView) itemView.findViewById(R.id.movieReleaseDate);
            movieThumbnail = (ImageView) itemView.findViewById(R.id.movieThumbnail);
            movieRating = (RatingBar) itemView.findViewById(R.id.movieRating);
        }
    }
}
