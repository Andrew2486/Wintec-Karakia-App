package com.group3.karakiaapp.fragments;

import android.net.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import com.group3.karakiaapp.Karakia;
import com.group3.karakiaapp.MainActivity;
import com.group3.karakiaapp.R;
import com.group3.karakiaapp.ResourceManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.*;

public class KarakiaFragment extends Fragment {
    VideoView player;
    TextView title;
    TextView contents;
    static boolean wordsInEnglish;
    Karakia song;
    public KarakiaFragment() {
        super(R.layout.fragment_karakia);
    }
    // TODO: See what can be done to add animations for changing between different bits of text in the words display.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        player = (VideoView)view.findViewById(R.id.video_karakia);
        title = (TextView)view.findViewById(R.id.text_karakiaName);
        contents = (TextView)view.findViewById(R.id.text_karakiaWords);

        Bundle args = getArguments();
        song = ResourceManager.Instance().karakias.get(args != null ? args.getInt("karakiaId") : -1);

        MediaController controller = new MediaController(getContext());
        controller.setAnchorView(player);
        player.setMediaController(controller);
        if (song != null) {
            player.setVideoURI(Uri.parse("android.resource://" + getContext().getPackageName() + "/" +
                    song.video));
            player.start();
            if (title != null)
                title.setText(song.name);
            if (contents != null) {
                UpdateWords();
                Switch translate = (Switch)view.findViewById(R.id.button_karakiaTranslate);
                if (translate != null) {
                    translate.setChecked(wordsInEnglish);
                    translate.setOnCheckedChangeListener((x, y) -> {
                        wordsInEnglish = y;
                        UpdateWords();
                    });
                }
            }
            Button origins = view.findViewById(R.id.button_karakiaOrigins);
            origins.setOnClickListener((x) -> MainActivity.instance.OpenInfoCard(song.origins));
        }
    }

    public void UpdateWords() {
        contents.setText(wordsInEnglish ? song.wordsEnglish : song.wordsMaori);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
