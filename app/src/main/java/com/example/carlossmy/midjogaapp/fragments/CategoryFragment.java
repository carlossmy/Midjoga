package com.example.carlossmy.midjogaapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carlossmy.midjogaapp.R;
import com.example.carlossmy.midjogaapp.activities.CreateActivity;
import com.example.carlossmy.midjogaapp.activities.MainActivity;
import com.example.carlossmy.midjogaapp.entities.Category;
import com.example.carlossmy.midjogaapp.fragments.adapters.MyCategoryRecyclerViewAdapter;
import com.example.carlossmy.midjogaapp.fragments.dummy.DummyContent;
import com.example.carlossmy.midjogaapp.fragments.dummy.DummyContent.DummyItem;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CategoryFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 3;
    private OnListFragmentInteractionListener mListener;
    private ArrayList<Category>categoryList;
    RecyclerView recycler;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CategoryFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CategoryFragment newInstance(int columnCount) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);
        recycler= view.findViewById(R.id.list);
        setRecyclerViewAdapter(recycler);
        // Set the adapter

        return view;
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
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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
    private void addCategory(){
        categoryList= new ArrayList<Category>();
        categoryList.add(new Category(1,"Anniversaire",null));
        categoryList.add(new Category(2,"Anniversaire",null));
        categoryList.add(new Category(3,"Anniversaire",null));
        categoryList.add(new Category(4,"Anniversaire",null));
        categoryList.add(new Category(5,"Anniversaire",null));
        categoryList.add(new Category(6,"Anniversaire",null));
        categoryList.add(new Category(7,"Anniversaire",null));
        categoryList.add(new Category(1,"Anniversaire",null));
        categoryList.add(new Category(1,"Anniversaire",null));
    }
}
