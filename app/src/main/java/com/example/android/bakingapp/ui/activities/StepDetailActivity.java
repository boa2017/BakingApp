package com.example.android.bakingapp.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.StepsFragmentAdapter;
import com.example.android.bakingapp.data.models.Recipe;
import com.example.android.bakingapp.utilities.NetworkUtils;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by beita on 17/05/2018.
 */

public class StepDetailActivity extends AppCompatActivity {

    public static final String RECIPE_KEY = "recipe_key";
    public static final String STEP_SELECTED_KEY = "step_key";

    @BindView(R.id.recipe_steps_tab_layout)
    TabLayout mRecipeStepTL;
    @BindView(R.id.recipe_steps_viewpager)
    ViewPager mRecipeStepVP;
    @BindView(android.R.id.content)
    View mParentLayout;

    private Recipe mRecipe;
    private int mStepSelectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_steps_detail);

        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(RECIPE_KEY) && bundle.containsKey(STEP_SELECTED_KEY)) {
            mRecipe = bundle.getParcelable(RECIPE_KEY);
            mStepSelectedPosition = bundle.getInt(STEP_SELECTED_KEY);
        } else {
            NetworkUtils.createSnackBar(this, mParentLayout, getString(R.string.failed_to_load), true);
            finish();
        }
        // Show the Up button in the action bar.
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mRecipe.getName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        StepsFragmentAdapter adapter = new StepsFragmentAdapter(getApplicationContext(), mRecipe.getSteps(), getSupportFragmentManager());
        mRecipeStepVP.setAdapter(adapter);
        mRecipeStepTL.setupWithViewPager(mRecipeStepVP);
        mRecipeStepVP.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (actionBar != null) {
                    actionBar.setTitle(mRecipe.getSteps().get(position).getShortDescription());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mRecipeStepVP.setCurrentItem(mStepSelectedPosition);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d("onDestroy");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this activity,
            // the Up button is shown.
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
