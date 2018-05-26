package com.example.android.bakingapp.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.models.Steps;
import com.example.android.bakingapp.utilities.GlideApp;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by beita on 18/05/2018.
 */

public class RecipeStepsDetailFragment extends Fragment {

    public static final String STEP_KEY = "step_k";
    private static final String POSITION_KEY = "pos_k";
    private static final String PLAY_WHEN_READY_KEY = "play_when_ready_k";

    @BindView(R.id.steps_container)
    CardView mStepsContainer;


    @BindView(R.id.recipe_step_video)
    SimpleExoPlayerView mExoPlayerView;
    @BindView(R.id.recipe_step_image)
    ImageView mStepImage;
    @BindView(R.id.recipe_step_desc)
    TextView mStepDesc;

    private SimpleExoPlayer mExoPlayer;
    private Steps mStep;
    private Unbinder unbinder;

    private long mCurrentPosition = 0;
    private boolean mPlayWhenReady = true;

    public RecipeStepsDetailFragment() {
    }
    // Required empty public constructor

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(STEP_KEY)) {
            mStep = getArguments().getParcelable(STEP_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.recipe_step_full, container, false);

        if (savedInstanceState != null && savedInstanceState.containsKey(POSITION_KEY)) {
            mCurrentPosition = savedInstanceState.getLong(POSITION_KEY);
            mPlayWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY_KEY);
        }

        unbinder = ButterKnife.bind(this, rootView);

        mStepDesc.setText(mStep.getDescription());

        // Show thumbnail if the url exists
        if (!mStep.getThumbnailURL().isEmpty()) {
            GlideApp.with(this)
                    .load(mStep.getThumbnailURL())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.receta)
                    .into(mStepImage);
            mStepImage.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(mStep.getVideoURL()))
            startPlayer(Uri.parse(mStep.getVideoURL()));
        else {
            mStepsContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        launchPlayer();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Logger.d("onDestroyView");
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong(POSITION_KEY, mCurrentPosition);
        outState.putBoolean(PLAY_WHEN_READY_KEY, mPlayWhenReady);
    }

    private void startPlayer(Uri mediaUri) {
        if (mExoPlayer == null) {

            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

            // Create an instance of the ExoPlayer.
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

            // Bind the player to the view.
            mExoPlayerView.setPlayer(mExoPlayer);

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), getString(R.string.app_name)), bandwidthMeter);
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(mediaUri);
            mExoPlayer.prepare(videoSource);

            // onRestore
            if (mCurrentPosition != 0)
                mExoPlayer.seekTo(mCurrentPosition);

            mExoPlayer.setPlayWhenReady(mPlayWhenReady);
            mExoPlayerView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Launch ExoPlayer.
     */
    private void launchPlayer() {
        if (mExoPlayer != null) {
            mPlayWhenReady = mExoPlayer.getPlayWhenReady();
            mCurrentPosition = mExoPlayer.getCurrentPosition();

            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }
}
