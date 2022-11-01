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

public class HomeFragment extends Fragment {
    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController nav = Navigation.findNavController(view);
        ((Button)view.findViewById(R.id.tempButton1)).setOnClickListener((x) ->
            nav.navigate(HomeFragmentDirections.actionHomeToKarakiaFragment(5))
        );
        ((Button)view.findViewById(R.id.tempButton2)).setOnClickListener((x) ->
            nav.navigate(HomeFragmentDirections.actionHomeToKarakiaFragment(-1))
        );
        if (!MainActivity.Settings.agreedToToS)
            nav.navigate(HomeFragmentDirections.actionHomeFragmentToTOSFragment());
    }
}
