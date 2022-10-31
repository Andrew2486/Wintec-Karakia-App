package com.group3.karakiaapp.fragments;

import com.group3.karakiaapp.R;

import android.net.Uri;
import android.os.*;
import android.util.Log;
import android.view.*;
import android.widget.MediaController;
import android.widget.VideoView;

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
        VideoView player = (VideoView)view.findViewById(R.id.player);
        MediaController controller = new MediaController(this.getContext());
        controller.setAnchorView(player);
        player.setMediaController(controller);
        player.setVideoURI(Uri.parse("android.resource://" + this.getContext().getPackageName() + "/" +
                ResourceManager.Instance().karakias.get(5).video));
        player.start();
        Log.d("words","words: " + ResourceManager.Instance().karakias.get(5).words);
    }
}
