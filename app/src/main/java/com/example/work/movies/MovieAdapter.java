package com.example.work.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(Context context, ArrayList<Movie> movieList){
        super(context,0,movieList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Movie movie=getItem(position);

        if(convertView== null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.poster_view,parent,false);
        }

        assert movie != null;
        String posterUrl= Constants.POSTER_BASE_URL+Constants.POSTER_SIZE_SMALL+movie.getPoster_path();

        Picasso.with(getContext())
                .load(posterUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into((ImageView)convertView);

        return convertView;
    }
}
