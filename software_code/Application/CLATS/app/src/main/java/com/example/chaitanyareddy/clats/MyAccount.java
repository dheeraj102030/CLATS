package com.example.chaitanyareddy.clats;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class MyAccount extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.myaccount, container, false);
        TextView name = (TextView)myView.findViewById(R.id.name);
        TextView roll = (TextView)myView.findViewById(R.id.roll);
        TextView phone = (TextView)myView.findViewById(R.id.phone);
        TextView cycle = (TextView)myView.findViewById(R.id.cycle);

        String text1  = getArguments().getString("Name");
        String text2  = getArguments().getString("roll");
        String text3  = getArguments().getString("phone");
        String text4  = getArguments().getString("cycle");

        name.setText(text1);
        roll.setText(text2);
        phone.setText(text3);
        cycle.setText(text4);
        return myView;
    }
}
