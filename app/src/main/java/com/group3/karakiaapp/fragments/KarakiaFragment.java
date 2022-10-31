package com.group3.karakiaapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.group3.karakiaapp.Karakia;
import com.group3.karakiaapp.R;
import com.group3.karakiaapp.ResourceManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.*;

public class KarakiaFragment extends Fragment {
    VideoView player;
    public KarakiaFragment() {
        super(R.layout.fragment_karakia);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        player = (VideoView)view.findViewById(R.id.player);


        Bundle args = getArguments();
        Karakia song = null;
        if (args != null)
            song = ResourceManager.Instance().karakias.get(args.getInt("karakiaId"));

        MediaController controller = new MediaController(this.getContext());
        controller.setAnchorView(player);
        player.setMediaController(controller);
        if (song != null) {
            player.setVideoURI(Uri.parse("android.resource://" + this.getContext().getPackageName() + "/" +
                    song.video));
            player.start();
            //player.
            Log.d("words", "words: " + song.words);
        }
    }
}
