package com.example.carlossmy.midjogaapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.carlossmy.midjogaapp.R;
import com.example.carlossmy.midjogaapp.activities.CreateActivity;
import com.example.carlossmy.midjogaapp.activities.MainActivity;
import com.example.carlossmy.midjogaapp.entities.Category;
import com.example.carlossmy.midjogaapp.fragments.adapters.MyCategoryRecyclerViewAdapter;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 3;
    private CategoryFragment.OnListFragmentInteractionListener mListener;

    private RecyclerView recycler;
    private EditText inputResearch;
    private Button buttonResearch,buttonQR;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        View v =inflater.inflate(R.layout.fragment_search, null);
        initViews(v);
        return v;
    }
    private void initViews(View v){
        recycler= v.findViewById(R.id.list);
        inputResearch=v.findViewById(R.id.input_research);
        buttonResearch=v.findViewById(R.id.button_search);
        buttonQR=v.findViewById(R.id.button_QR);
        setRecyclerViewAdapter(recycler);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }
    private void setRecyclerViewAdapter(RecyclerView view){
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyCategoryRecyclerViewAdapter(MainActivity.categoryList, mListener));
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CategoryFragment.OnListFragmentInteractionListener) {
            mListener = (CategoryFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Category item);
    }
}
