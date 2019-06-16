package com.example.carlossmy.midjogaapp.fragments.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carlossmy.midjogaapp.R;
import com.example.carlossmy.midjogaapp.activities.MainActivity;
import com.example.carlossmy.midjogaapp.entities.Category;
import com.example.carlossmy.midjogaapp.fragments.CategoryFragment.OnListFragmentInteractionListener;
import com.example.carlossmy.midjogaapp.fragments.dummy.DummyContent.DummyItem;
import com.example.carlossmy.midjogaapp.utils.StaticFunctions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCategoryRecyclerViewAdapter extends RecyclerView.Adapter<MyCategoryRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Category> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyCategoryRecyclerViewAdapter(ArrayList<Category> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).getLibelle());
        // holder.mImageView.setImageResource(R.drawable.logo_gmail);
        if (mValues.get(position).getImgUrl()!=null)
            Picasso.with(holder.mImageView.getContext()).load(mValues.get(position).getImgUrl()).fit().centerCrop()
                    .into(holder.mImageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final ImageView mImageView;
        public Category mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.category_title);
            mImageView = (ImageView) view.findViewById(R.id.category_img);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}
