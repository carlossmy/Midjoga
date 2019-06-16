package com.example.carlossmy.midjogaapp.fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carlossmy.midjogaapp.R;
import com.example.carlossmy.midjogaapp.activities.MainActivity;
import com.example.carlossmy.midjogaapp.entities.Cagnotte;
import com.example.carlossmy.midjogaapp.entities.CustomDate;
import com.example.carlossmy.midjogaapp.fragments.HomeFragment.OnListFragmentInteractionListener;
import com.example.carlossmy.midjogaapp.fragments.dummy.DummyContent.DummyItem;
import com.example.carlossmy.midjogaapp.retrofit.CagnotteClient;
import com.example.carlossmy.midjogaapp.utils.StaticFunctions;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCagnotteRecyclerViewAdapter extends RecyclerView.Adapter<MyCagnotteRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Cagnotte> mValues;
    private final OnListFragmentInteractionListener mListener;
    private static final String TIME_REMAINING= " Jours restants";
    private static final String DONNORS= " Personnes ont contisé";
    private static final String AMOUNT= " FCFA Récoltés";

    public MyCagnotteRecyclerViewAdapter(ArrayList<Cagnotte> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_cagnotte, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item = mValues.get(position);
        holder.titleView.setText(mValues.get(position).getTitle());
        holder.descView.setText(mValues.get(position).getDesc());
        holder.amountView.setText(StaticFunctions.Amount(mValues.get(position).getAmount(),mValues.get(position).getGoal()));
        holder.remainingView.setText(StaticFunctions.timeRemaining(mValues.get(position).getDue_date()));
        //holder.remainingView.setText("En cours");
        holder.bar.setProgress(StaticFunctions.barPercentage(mValues.get(position).getAmount(),mValues.get(position).getGoal()));
        loadImage(holder.imgView.getContext(),holder.imgView,mValues.get(position).getId());
        getDonnors(holder.imgView.getContext(),holder.NbView,mValues.get(position).getId());



        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.item);
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
        public final TextView titleView;
        public final TextView descView;
        public final TextView amountView;
        public final TextView NbView;
        public final TextView remainingView;
        public final ImageView imgView;
        public final ProgressBar bar;
        public Cagnotte item;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            titleView = (TextView) view.findViewById(R.id.item_title);
            descView = (TextView) view.findViewById(R.id.item_desc);
            amountView = (TextView) view.findViewById(R.id.item_amount);
            NbView = (TextView) view.findViewById(R.id.item_nb);
            remainingView = (TextView) view.findViewById(R.id.item_remaining);
            imgView=(ImageView)view.findViewById(R.id.item_img);
            bar=view.findViewById(R.id.bar);

        }

        @Override
        public String toString() {
            return super.toString() + " '" +titleView.getText() + "'";
        }
    }
    /*
    Les différentes méthode d'adaptation
     */

    private void loadImage(final Context ct, final ImageView v, int id){
        Call<ResponseBody> call = CagnotteClient
                .getInstance()
                .getApi()
                .getImage(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Toast.makeText(ct,response.toString(),Toast.LENGTH_LONG).show();
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    if (json.has("name")){
                        if(json.getString("name")!=null){
                            Picasso.with(ct).load(StaticFunctions.IMG_URL+json.getString("name")).fit().centerCrop()
                                    .into(v);
                        }else
                            v.setImageResource(R.drawable.money);// a changer
                    }else
                        v.setImageResource(R.drawable.money);// a changer

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
    private void getDonnors(final Context ct, final TextView v, int id){
        Call<ResponseBody> call = CagnotteClient
                .getInstance()
                .getApi()
                .getDonnors(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Toast.makeText(ct,response.toString(),Toast.LENGTH_LONG).show();
                try {
                    if (response.body().string()==null)
                        v.setText(StaticFunctions.Donnors(0));// a changer
                    else {
                        JSONArray json = new JSONArray(response.body().string());

                        if (json.getJSONObject(0).has("donnors")) {

                            v.setText(StaticFunctions.Donnors(json.getJSONObject(0).getInt("donnors")));

                        } else
                            v.setText(StaticFunctions.Donnors(1));// a changer
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    v.setText(StaticFunctions.Donnors(2));// a changer
                } catch (JSONException e) {
                    e.printStackTrace();
                    v.setText(StaticFunctions.Donnors(4));// a changer
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                v.setText(StaticFunctions.Donnors(5));// a changer
            }
        });

    }


}
