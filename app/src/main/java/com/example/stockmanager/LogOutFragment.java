package com.example.stockmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



public class LogOutFragment extends Fragment implements View.OnClickListener {

    Button logOut;
    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_logout, container, false);
        logOut=(Button)view.findViewById(R.id.buttonFinalL);
        logOut.setOnClickListener((View.OnClickListener) this);

        return view;

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.buttonFinalL:

                Intent intent =new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            break;


        }

    }
}
