package com.project.angelcanturamirez.appcontrolacceso.Fragments;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.angelcanturamirez.appcontrolacceso.R;
import com.project.angelcanturamirez.appcontrolacceso.SplashActivity;

public class IndicacionPagosFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    TextView txtIndicacion;
    ImageView imgIndicacion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_indicaccion_pagos, container, false);
        // Inflate the layout for this fragment
        imgIndicacion = (ImageView)view.findViewById(R.id.imgPagos);
        txtIndicacion = (TextView)view.findViewById(R.id.txtPagos);

        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.fade_in_indicaciones);
        imgIndicacion.startAnimation(animation);
        txtIndicacion.startAnimation(animation);
        return view;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
