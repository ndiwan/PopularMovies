package com.example.work.movies;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private String poster_path;
    private String original_title;
    private String release_date;
    private double vote_average;
    private String overview;

    public Movie(String poster_path, String original_title, String release_date, double vote_average, String overview) {
        this.poster_path = poster_path;
        this.original_title = original_title;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }



    public String getOriginal_title() {
        return original_title;
    }


    public String getRelease_date() {
        return release_date;
    }



    public double getVote_average() {
        return vote_average;
    }



    public String getOverview() {
        return overview;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster_path);
        dest.writeString(original_title);
        dest.writeString(release_date);
        dest.writeDouble(vote_average);
        dest.writeString(overview);
    }

    protected Movie(Parcel in) {
        poster_path = in.readString();
        original_title = in.readString();
        release_date = in.readString();
        vote_average = in.readDouble();
        overview = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

}
