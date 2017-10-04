package com.kediri.humas.sambutan;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Sambutan extends Fragment {

    Button undangan;


    public Sambutan() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sambutan, container, false);

            undangan = (Button)rootView.findViewById(R.id.button2);

            undangan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent und=new Intent(getContext(),SambutanUndangan.class);
                    startActivity(und);
                }
            });

        return rootView;
    }

}
