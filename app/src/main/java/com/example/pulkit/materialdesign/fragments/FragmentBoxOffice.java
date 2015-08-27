package com.example.pulkit.materialdesign.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.pulkit.materialdesign.Keys;
import com.example.pulkit.materialdesign.MyApplication;
import com.example.pulkit.materialdesign.R;
import com.example.pulkit.materialdesign.adapters.BoxOfficeAdapter;
import com.example.pulkit.materialdesign.model.Movie;
import com.example.pulkit.materialdesign.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.pulkit.materialdesign.Keys.EndpointBoxOffice.*;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBoxOffice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBoxOffice extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String URL_ROTTEN_TOMATOES_BOX_OFFICE = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json";
    public static final String URL_THE_MOVIE_DB_BOX_OFFICE = "http://api.themoviedb.org/3/movie/now_playing";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private ArrayList<Movie> listMovies = new ArrayList<>();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private RecyclerView recyclerView;
    private BoxOfficeAdapter boxOfficeAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBoxOffice.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBoxOffice newInstance(String param1, String param2) {
        FragmentBoxOffice fragment = new FragmentBoxOffice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static String getRottenTomatoesRequestUrl(int limit) {
        return URL_ROTTEN_TOMATOES_BOX_OFFICE + "?apikey=" + MyApplication.API_KEY_ROTTEN_TOMATOS+ "&limit=" + limit;
    }

    public static String getTheMovieDbBoxRequestUrl(){
        return URL_THE_MOVIE_DB_BOX_OFFICE + "?api_key=" + MyApplication.API_KEY_THE_MOVIE_DB;
    }

    public FragmentBoxOffice() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        volleySingleton = VolleySingleton.getsInstance();
        requestQueue = volleySingleton.getRequestQueue();
        sendJsonRequest();
    }

    public void sendJsonRequest() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                getRottenTomatoesRequestUrl(10),
//                getTheMovieDbBoxRequestUrl(),  // Use this for the TheMovieDB
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listMovies = parseJsonResponse(response);
                        boxOfficeAdapter.setMovieList(listMovies);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }

    private ArrayList<Movie> parseJsonResponse(JSONObject response) {
        ArrayList<Movie> listMovies = new ArrayList<>();
        if (response != null || response.length() > 0) {
            try {
                if (response.has(KEY_MOVIES)) {  //For  TheMovieDB change the key to KEY_RESULTS

                    JSONArray arrayMovies = response.getJSONArray(KEY_MOVIES); //For  TheMovieDB change the key to KEY_RESULTS

                    for (int i=0; i < arrayMovies.length(); i++) {
                        JSONObject currentMovie = arrayMovies.getJSONObject(i);
                        long id = currentMovie.getLong(KEY_ID);
                        String title = currentMovie.getString(KEY_TITLE);

//                    Releasing Date

                        JSONObject objectReleaseDate = currentMovie.getJSONObject(KEY_RELEASE_DATE);
                        String releaseDate = null;
                        if (objectReleaseDate.has(KEY_THEATER)) {
                            releaseDate = objectReleaseDate .getString(KEY_THEATER);
                        } else {
                            releaseDate = "NA";
                        }

//              Audience score for the current movie
                        JSONObject objectRating = currentMovie.getJSONObject(KEY_RATINGS);
                        int audienceScore = -1;
                        if (objectRating.has(KEY_AUDIENCE_SCORE)) {
                            audienceScore = objectRating.getInt(KEY_AUDIENCE_SCORE);
                        }

//                    Movie Synopsis
                        String synopsis = currentMovie.getString(KEY_SYNOPSIS);

//                Movie Poster
                        JSONObject moviePoster = currentMovie.getJSONObject(KEY_POSTERS);
                        String poster = null;
                        if (moviePoster.has(KEY_THUMBNAIL)) {
                            poster = moviePoster.getString(KEY_THUMBNAIL);
                        }

                        Movie movie = new Movie();
                        movie.setId(id);
                        movie.setTitle(title);
                        Date date = dateFormat.parse(releaseDate);
                        movie.setReleaseDateTheater(date);
                        movie.setAudienceScore(audienceScore);
                        movie.setSynopsis(synopsis);
                        movie.setUrlThumbnail(poster);

                        listMovies.add(movie);
                    }
                    Toast.makeText(getActivity(),listMovies.toString(),Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {

            }catch (ParseException e) {

            }
        }
        return listMovies;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_box_office, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.listMovieHits);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        boxOfficeAdapter = new BoxOfficeAdapter(getActivity());
        recyclerView.setAdapter(boxOfficeAdapter);
        sendJsonRequest();
        return view;
    }


}
