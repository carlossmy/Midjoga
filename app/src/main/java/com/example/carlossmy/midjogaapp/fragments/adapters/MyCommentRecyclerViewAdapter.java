package com.example.carlossmy.midjogaapp.fragments.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.carlossmy.midjogaapp.R;
import com.example.carlossmy.midjogaapp.entities.Comment;
import com.example.carlossmy.midjogaapp.utils.StaticFunctions;

import java.util.ArrayList;

public class MyCommentRecyclerViewAdapter extends RecyclerView.Adapter<MyCommentRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Comment> mValues;

    public MyCommentRecyclerViewAdapter(ArrayList<Comment> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mUserView.setText(mValues.get(position).getUser().getNomComplet());
        holder.mContentView.setText(mValues.get(position).getContent());
        holder.mDateView.setText(mValues.get(position).getDate().toString());
        // holder.mImageView.setImageResource(R.drawable.logo_gmail);
       /* if (mValues.get(position).getImgUrl()!=null)
            Picasso.with(holder.mImageView.getContext()).load(mValues.get(position).getImgUrl()).fit().centerCrop()
                    .into(holder.mImageView);*/

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mUserView,mContentView;
        public final TextView mDateView;
        public Comment mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mUserView = view.findViewById(R.id.comment_user);
            mContentView = view.findViewById(R.id.comment_content);
            mDateView = view.findViewById(R.id.comment_date);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
