package com.group3.karakiaapp.fragments;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentBase extends Fragment {
    public FragmentBase(@LayoutRes int contentLayoutId) {
        super(contentLayoutId);
    }
    public FragmentBase() {
        super();
    }

    public static FragmentBase last;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        last = this;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        last = this;
    }
}
