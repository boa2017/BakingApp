package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.models.Steps;
import com.example.android.bakingapp.ui.fragments.RecipeStepsDetailFragment;

import java.util.List;

/**
 * Created by beita on 15/05/2018.
 */

public class StepsFragmentAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private List<Steps> mSteps;

    public StepsFragmentAdapter(Context context, List<Steps> steps, FragmentManager fm) {
        super(fm);
        this.mContext = context;
        this.mSteps = steps;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(RecipeStepsDetailFragment.STEP_KEY, mSteps.get(position));
        RecipeStepsDetailFragment fragment = new RecipeStepsDetailFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return String.format(mContext.getString(R.string.step), position);
    }

    @Override
    public int getCount() {
        return mSteps.size();
    }


}
