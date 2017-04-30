package com.udacity.danilo.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.udacity.danilo.popularmovies.R;
import com.udacity.danilo.popularmovies.bs.Review;

import org.w3c.dom.Text;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private Review[] mData;
    protected ReviewAdapter(Review[] data){
        mData = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Review review = mData[position];
        holder.mTextAuthor.setText(review.getAuthor());
        holder.mTextContent.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView mTextAuthor;
        protected TextView mTextContent;
        protected ViewHolder(View itemView) {
            super(itemView);

            mTextAuthor = (TextView) itemView.findViewById(R.id.tv_author);
            mTextContent = (TextView) itemView.findViewById(R.id.tv_review_content);
        }
    }
}
