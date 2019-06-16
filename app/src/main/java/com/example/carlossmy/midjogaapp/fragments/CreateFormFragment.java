package com.example.carlossmy.midjogaapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.carlossmy.midjogaapp.R;
import com.example.carlossmy.midjogaapp.activities.CreateActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateFormFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateFormFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button create,selectDate;
    TextInputLayout descInput,amountInput,localisationInput;
    TextView date,point;
    RadioButton radio_private;
    RadioButton radio_public;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CreateFormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateFormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateFormFragment newInstance(String param1, String param2) {
        CreateFormFragment fragment = new CreateFormFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_create_form, container, false);
        initViews(view);
        return view;
    }
    private void initViews(View v){
        create=v.findViewById(R.id.create_cagnotte);
        selectDate=v.findViewById(R.id.due_date);
        descInput=(TextInputLayout)v.findViewById(R.id.description);
        amountInput=(TextInputLayout)v.findViewById(R.id.montant);
        localisationInput=(TextInputLayout)v.findViewById(R.id.localisation);
        date=(TextView)v.findViewById(R.id.date);
        point=(TextView)v.findViewById(R.id.point);
        radio_private=(RadioButton)v.findViewById(R.id.radio_private);
        radio_public=(RadioButton)v.findViewById(R.id.radio_public);
        selectDate.setOnClickListener(this);
        create.setOnClickListener(this);
        radio_private.setOnClickListener(this);
        radio_public.setOnClickListener(this);

    }
    private void setCagnotteInformation(){
        String description = descInput.getEditText().getText().toString().trim();
        double amount =(double) Double.parseDouble(amountInput.getEditText().getText().toString());
        String localisation = localisationInput.getEditText().getText().toString().trim();
        CreateActivity.cagnotte.setDesc(description);
        CreateActivity.cagnotte.setAmount(amount);
        CreateActivity.cagnotte.setLocalisation(localisation);

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            switch (view.getId()){
                case (R.id.create_cagnotte):
                    setCagnotteInformation();
                    mListener.createCagnotte();
                    break;
                case(R.id.due_date):
                    mListener.selectDate();
                    break;
                case (R.id.radio_public):
                    mListener.onRadioButtonClicked(view);
                case (R.id.radio_private):
                    mListener.onRadioButtonClicked(view);
            }

        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void selectDate();
        void createCagnotte();
        void onRadioButtonClicked(View v);
    }
}
