package com.example.carlossmy.midjogaapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carlossmy.midjogaapp.R;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PaiementInformationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PaiementInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaiementInformationFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText number,montant;
    TextView mode;
    Button payer;
    public static String TOGOCEL="TOGOCEL";
    public static String MOOV="MOOV";
    public static final Pattern TOGOCEL_PATTERN_1 = Pattern.compile("^9[0-3][0-9][0-9][0-9][0-9][0-9][0-9]$");
    public static final Pattern TOGOCEL_PATTERN_2 = Pattern.compile("^70[0-9][0-9][0-9][0-9][0-9][0-9]$");
    public static final Pattern MOOV_PATTERN_1 = Pattern.compile("^9[7-9][0-9][0-9][0-9][0-9][0-9][0-9]$");
    public static final Pattern MOOV_PATTERN_2 = Pattern.compile("^79[0-9][0-9][0-9][0-9][0-9][0-9]$");
    public static final Pattern NUMBER_PATTERN_ = Pattern.compile("^[0-9][0-9][0-9][0-9][0-9][0-9]$");

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PaiementInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaiementInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaiementInformationFragment newInstance(String param1, String param2) {
        PaiementInformationFragment fragment = new PaiementInformationFragment();
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
        View view = inflater.inflate(R.layout.fragment_paiement_information, container, false);
        initViews(view);
        return view;
    }
    private void initViews(View v){
        number=v.findViewById(R.id.telephone);
        montant=v.findViewById(R.id.montant_a_payer);
        payer=v.findViewById(R.id.payer);
        mode=v.findViewById(R.id.mode);
        payer.setOnClickListener((this));
        setPaymentMode(number);


    }
    private void testNumber(String number){
        if (number!=""){
            if (TOGOCEL_PATTERN_1.matcher(number).matches()||TOGOCEL_PATTERN_2.matcher(number).matches()){
               mode.setText(TOGOCEL);
               return;
            }
            else if((MOOV_PATTERN_1.matcher(number).matches()||MOOV_PATTERN_2.matcher(number).matches())){
                mode.setText(MOOV);
                return;
            }
            else {
                mode.setText("INVALIDE");
                return;

            }

        }
        else {
                mode.setText("");
                return;
        }
    }

    private void setPaymentMode(EditText et){
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                testNumber(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
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
    private int getTypePaiement(TextView number){
        if (number.getText().toString().trim()!=TOGOCEL&&number.getText().toString()!=MOOV)
                return 0;
         else if (number.getText().toString().trim()==TOGOCEL)
            return 1;
         else if (number.getText().toString().trim()==MOOV)
             return 2;
         return 0;
        }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        double amount = Double.valueOf(montant.getText().toString().trim());
        int type = getTypePaiement(mode);
        if (type==0){
            Toast.makeText(getContext(),"Numéro de téléphone non valide",Toast.LENGTH_SHORT).show();
            return;
        }
        mListener.payForCagnotte(amount,type);

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
        void payForCagnotte(double amount,int typePaiement);
        void modeInteraction(String number);
    }
}
