package com.group3.karakiaapp.fragments;

import com.group3.karakiaapp.R;

import android.net.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import androidx.annotation.*;
import androidx.constraintlayout.widget.*;
import androidx.fragment.app.*;
import androidx.navigation.*;
import com.group3.karakiaapp.*;

import java.io.PrintWriter;

public class TOSFragment extends FragmentBase {
    public TOSFragment() { super(R.layout.fragment_tos); }
    ConstraintLayout.LayoutParams toolbarBefore;
    NavController nav;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nav = Navigation.findNavController(view);
        if (MainActivity.Settings.agreedToToS) {
            MainActivity.Settings.agreedToToS = false;
            ResourceManager.Instance().WriteSettings();
        }
        ((Button)view.findViewById(R.id.button_agreeToS)).setOnClickListener((x) -> {
            MainActivity.Settings.agreedToToS = true;
            ResourceManager.Instance().WriteSettings();
            nav.navigateUp();
        });
        ((Button)view.findViewById(R.id.button_declineToS)).setOnClickListener((x) -> {
            getActivity().finish();
            System.exit(0);
        });
        toolbarBefore = (ConstraintLayout.LayoutParams)MainActivity.instance.toolbar.getLayoutParams();
        ConstraintLayout.LayoutParams newParams = new ConstraintLayout.LayoutParams(toolbarBefore);
        newParams.height = 1;
        MainActivity.instance.toolbar.setLayoutParams(newParams);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MainActivity.instance.toolbar.setLayoutParams(toolbarBefore);
    }


}
