package com.group3.karakiaapp.fragments;

import com.group3.karakiaapp.R;

import android.net.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import androidx.annotation.*;
import androidx.fragment.app.*;
import androidx.navigation.*;
import com.group3.karakiaapp.*;

public class TOSFragment extends Fragment {
    public TOSFragment() { super(R.layout.fragment_tos); }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (MainActivity.Settings.agreedToToS) {
            MainActivity.Settings.agreedToToS = false;
            ResourceManager.Instance().WriteSettings();
        }
        ((Button)view.findViewById(R.id.button_agreeToS)).setOnClickListener((x) -> {
            MainActivity.Settings.agreedToToS = true;
            ResourceManager.Instance().WriteSettings();
            Navigation.findNavController(view).navigateUp();
        });
        ((Button)view.findViewById(R.id.button_declineToS)).setOnClickListener((x) -> {
            getActivity().finish();
            System.exit(0);
        });
    }
}
