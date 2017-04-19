package com.mshvdvskgmail.technoparkmessenger.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mshvdvskgmail.technoparkmessenger.R;

/**
 * Created by andrey on 29.11.2016.
 */

public class SimpleFragment extends BaseFragment {
    public final static String TEXT = "SimpleFragmentText";
    private TextView textView;

    private String text = "NoText";

    public SimpleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        text = getArguments().getString(TEXT);
        if(text == null) text = "NoText";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple, container, false);

        //Find the +1 button
        textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(text);

        return view;
    }
}
