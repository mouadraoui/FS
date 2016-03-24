package com.example.mouadr.fs;

/**
 * Created by mouadr on 19/03/2016.
 */
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;



import butterknife.*;


public class FtourFragment extends Fragment {
    @Bind(R.id.videoView)
    VideoView ve;

    public FtourFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_ftour, container, false);
        ButterKnife.bind(this,rootView);

        MediaController mediaControllerr = new MediaController(getActivity());
        //mediaControllerr.setMediaPlayer(mediaController);
        Uri uri = Uri.parse("android.resource://" +"com.example.mouadr.fs" + "/" + R.raw.vedio);

        mediaControllerr.setAnchorView(ve);
        ve.setMediaController(mediaControllerr);
        ve.setVideoURI(uri);

        ve.start();
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}