package com.example.carlossmy.midjogaapp.fragments.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carlossmy.midjogaapp.R;
import com.example.carlossmy.midjogaapp.entities.Paiement;
import com.example.carlossmy.midjogaapp.entities.User;

import java.util.ArrayList;

public class MyPaiementRecyclerViewAdapter extends  RecyclerView.Adapter<MyPaiementRecyclerViewAdapter.ViewHolder> {
    private final ArrayList<Paiement> mValues;

    public MyPaiementRecyclerViewAdapter(ArrayList<Paiement> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.users_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getUser().getNomComplet());
        holder.mMontantView.setText((int)mValues.get(position).getMontant());
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
        public final TextView mNameView,mMontantView;
        public final ImageView mImageView;
        public Paiement mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.user_name);
            mImageView = (ImageView) view.findViewById(R.id.user_img);
            mMontantView = (TextView) view.findViewById(R.id.paiement_montant);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
