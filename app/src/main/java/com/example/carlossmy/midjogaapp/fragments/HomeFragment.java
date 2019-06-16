package com.example.carlossmy.midjogaapp.fragments;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.carlossmy.midjogaapp.R;
import com.example.carlossmy.midjogaapp.activities.MainActivity;
import com.example.carlossmy.midjogaapp.entities.Cagnotte;
import com.example.carlossmy.midjogaapp.fragments.adapters.MyCagnotteRecyclerViewAdapter;
import com.example.carlossmy.midjogaapp.fragments.adapters.MyCategoryRecyclerViewAdapter;
import com.example.carlossmy.midjogaapp.fragments.dummy.DummyContent;
import com.example.carlossmy.midjogaapp.fragments.dummy.DummyContent.DummyItem;

import java.sql.Date;
import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private SwipeRefreshLayout refresh;
    private RelativeLayout noConnection;
    private Button tryAgain;
    private ProgressBar loadingBar;
    RecyclerView recyclerView;
    FloatingActionButton floating;
    ArrayList<Cagnotte>list;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomeFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HomeFragment newInstance(int columnCount) {
        HomeFragment fragment = new HomeFragment();
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
    private void initViews(View v){
        refresh=v.findViewById(R.id.refresh);
        refresh.setOnRefreshListener(this);
        recyclerView= v.findViewById(R.id.list);
        setRecyclerViewAdapter(recyclerView);
        floating= v.findViewById(R.id.add_fab);
        floating.setOnClickListener(this);
        noConnection = v.findViewById(R.id.no_connection);
        tryAgain= v.findViewById(R.id.no_connection_try_again);
        tryAgain.setOnClickListener(this);
        loadingBar= v.findViewById(R.id.loading_bar);
    }
    private void disableFailureMessage(){
        noConnection.setVisibility(View.GONE);
        loadingBar.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Set the adapter
        initViews(view);
        setRecyclerViewAdapter(recyclerView);
        return view;
    }
    private void setRecyclerViewAdapter(RecyclerView view){
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        recyclerView.setAdapter(new MyCagnotteRecyclerViewAdapter(MainActivity.list, mListener));

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
    @Override
    public void onRefresh() {
        mListener.cagnotteListUpdate();
        setRecyclerViewAdapter(recyclerView);
        loadingBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        refresh.setRefreshing(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_fab:
                mListener.addCagnotte();
                break;
            case R.id.no_connection_try_again :
                disableFailureMessage();
                mListener.cagnotteListUpdate();
                setRecyclerViewAdapter(recyclerView);
                break;


        }
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
        void onListFragmentInteraction(Cagnotte item);
        void cagnotteListUpdate();
        void addCagnotte();
        void tryAgainClicked();
    }

}
