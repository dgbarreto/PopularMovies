package com.udacity.danilo.popularmovies.bs;

import android.net.ParseException;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Review implements Parcelable {
    private String mID;
    private String mAuthor;
    private String mContent;
    private String mUrl;

    private final String OWS_ID = "id";
    private final String OWS_AUTHOR = "author";
    private final String OWS_CONTENT = "content";
    private final String OWS_URL = "url";

    public Review(JSONObject data) throws JSONException, ParseException{
        mID = data.getString(OWS_ID);
        mAuthor = data.getString(OWS_AUTHOR);
        mContent = data.getString(OWS_CONTENT);
        mUrl = data.getString(OWS_URL);
    }

    private Review(Parcel in) {
        mID = in.readString();
        mAuthor = in.readString();
        mContent = in.readString();
        mUrl = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public String getID(){ return mID; }
    public String getAuthor(){ return mAuthor; }
    public String getContent(){ return mContent; }
    public String getUrl(){ return mUrl; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mID);
        parcel.writeString(mAuthor);
        parcel.writeString(mContent);
        parcel.writeString(mUrl);
    }
}
