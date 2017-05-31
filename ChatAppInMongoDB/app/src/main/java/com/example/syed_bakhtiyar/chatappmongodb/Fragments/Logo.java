package com.example.syed_bakhtiyar.chatappmongodb.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.syed_bakhtiyar.chatappmongodb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Logo extends Fragment {


    public Logo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logo, container, false);
    }

}
