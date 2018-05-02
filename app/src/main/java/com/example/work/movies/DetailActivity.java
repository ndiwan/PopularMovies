package com.example.work.movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Movie movie= getIntent().getParcelableExtra("movieList");

        TextView title=findViewById(R.id.titleTv);
        TextView releaseDate=findViewById(R.id.releaseDateTv);
        TextView rating=findViewById(R.id.ratingTv);
        TextView overview=findViewById(R.id.overviewTv);
        ImageView poster=findViewById(R.id.posterIv);

        Picasso.with(DetailActivity.this)
                .load("https://image.tmdb.org/t/p/"+Constants.POSTER_SIZE_SMALL+ movie.getPoster_path())
                .into(poster);

        title.setText(movie.getOriginal_title());
        releaseDate.setText(movie.getRelease_date());
        rating.setText(Double.toString(movie.getVote_average()));
        overview.setText(movie.getOverview());
    }
}
