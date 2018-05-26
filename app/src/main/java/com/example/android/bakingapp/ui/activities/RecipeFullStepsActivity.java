package com.example.android.bakingapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.SingleRecipeAdapter;
import com.example.android.bakingapp.data.models.Recipe;
import com.example.android.bakingapp.interfaces.Listeners;
import com.example.android.bakingapp.ui.fragments.RecipeStepsDetailFragment;
import com.example.android.bakingapp.utilities.NetworkUtils;
import com.example.android.bakingapp.utilities.SpacingItemDecoration;
import com.example.android.bakingapp.widget.RecipeWidgetService;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by beita on 17/05/2018.
 */

public class RecipeFullStepsActivity extends AppCompatActivity implements OnQueryTextListener {
    public static final String RECIPE_KEY = "recipe_k";

    @BindView(R.id.recipe_step_list)
    RecyclerView mRecyclerView;

    @BindView(android.R.id.content)
    View mParentLayout;

    private boolean mTwoPane;

    private Recipe mRecipe;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(RECIPE_KEY)) {
            mRecipe = bundle.getParcelable(RECIPE_KEY);
        } else {
            NetworkUtils.createSnackBar(this, mParentLayout, getString(R.string.failed_to_load), true);
            finish();
        }


        setContentView(R.layout.activity_recipe_full_steps);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mRecipe.getName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mTwoPane = getResources().getBoolean(R.bool.twoPaneMode);
        if (mTwoPane) {

            if (savedInstanceState == null && !mRecipe.getSteps().isEmpty()) {
                showStep(0);
            }
        }

        setupRecyclerView();

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

    private void setupRecyclerView() {
        mRecyclerView.addItemDecoration(new SpacingItemDecoration((int) getResources().getDimension(R.dimen.margin_medium)));
        mRecyclerView.setAdapter(new SingleRecipeAdapter(mRecipe, new Listeners.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showStep(position);
            }
        }));
    }

    private void showStep(int position) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(RecipeStepsDetailFragment.STEP_KEY, mRecipe.getSteps().get(position));
            RecipeStepsDetailFragment fragment = new RecipeStepsDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.steps_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(StepDetailActivity.RECIPE_KEY, mRecipe);
            intent.putExtra(StepDetailActivity.STEP_SELECTED_KEY, position);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_recipe_info, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setQueryHint("Search…");
        mSearchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        Toast.makeText(this, newText, Toast.LENGTH_SHORT).show();

        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String text) {

        Toast.makeText(this, "Searching for " + text, Toast.LENGTH_LONG).show();

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send_to_widget:
                RecipeWidgetService.updateWidget(this, mRecipe);
                NetworkUtils.createSnackBar(this, mParentLayout, String.format(getString(R.string.sent_to_widget), mRecipe.getName()), false);

                return true;

            case R.id.share:
                Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                        .setType("text/plain")
                        .setSubject("Build your own Baking App")
                        .setText("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json")
                        .getIntent();
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                startActivity(shareIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
