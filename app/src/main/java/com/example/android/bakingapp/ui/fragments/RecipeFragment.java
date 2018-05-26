package com.example.android.bakingapp.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.BakingAppApplication;
import com.example.android.bakingapp.Prefs;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.RecipesAdapter;
import com.example.android.bakingapp.api.RecipeApiCallback;
import com.example.android.bakingapp.api.RecipeApiManager;
import com.example.android.bakingapp.data.models.Recipe;
import com.example.android.bakingapp.interfaces.Listeners;
import com.example.android.bakingapp.utilities.NetworkUtils;
import com.example.android.bakingapp.utilities.SpacingItemDecoration;
import com.example.android.bakingapp.widget.RecipeWidgetService;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by beita on 18/05/2018.
 */

public class RecipeFragment extends Fragment {

    private static String RECIPES_KEY = "recipes";
    @BindView(R.id.recipe_list_recycler_view)
    RecyclerView mRecipesRV;
    @BindView(R.id.pull_to_refresh)
    SwipeRefreshLayout mPullToRefresh;
    @BindView(R.id.no_recipes_container)
    ConstraintLayout mNoRecipesContainer;
    private OnRecipeClickListener mListener;
    private Unbinder unbinder;
    private List<Recipe> mRecipes;
    private BakingAppApplication bakingAppApplication;

    /**
     * This will load the recipes when the app launch or after an internet reconnection without pulling to refresh
     */
    private final BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mRecipes == null) {
                loadRecipes();
            }
        }
    };

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_recipe, container, false);
        unbinder = ButterKnife.bind(this, viewRoot);

        mPullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRecipes();
            }
        });

        mNoRecipesContainer.setVisibility(View.VISIBLE);
        setupRecyclerView();

        // Get the IdlingResource instance
        bakingAppApplication = (BakingAppApplication) getActivity().getApplicationContext();

        bakingAppApplication.setIdleState(false);

        if (savedInstanceState != null && savedInstanceState.containsKey(RECIPES_KEY)) {
            mRecipes = savedInstanceState.getParcelableArrayList(RECIPES_KEY);

            mRecipesRV.setAdapter(new RecipesAdapter(getActivity().getApplicationContext(), mRecipes, new Listeners.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mListener.onRecipeSelected(mRecipes.get(position));
                }
            }));
            dataLoadedLayout();
        }

        return viewRoot;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeClickListener) {
            mListener = (OnRecipeClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRecipeClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Logger.d("onDestroyView");
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();

        getActivity().unregisterReceiver(networkChangeReceiver);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mRecipes != null && !mRecipes.isEmpty())
            outState.putParcelableArrayList(RECIPES_KEY, (ArrayList<? extends Parcelable>) mRecipes);
    }

    private void setupRecyclerView() {
        mRecipesRV.setVisibility(View.GONE);
        mRecipesRV.setHasFixedSize(true);

        boolean twoPaneMode = getResources().getBoolean(R.bool.twoPaneMode);
        if (twoPaneMode) {
            mRecipesRV.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 3));
        } else {
            mRecipesRV.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        }

        mRecipesRV.addItemDecoration(new SpacingItemDecoration((int) getResources().getDimension(R.dimen.margin_medium)));
        mRecipesRV.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());
    }

    private void loadRecipes() {

        if (NetworkUtils.isNetworkAvailable(getActivity().getApplicationContext())) {
            mPullToRefresh.setRefreshing(true);

            RecipeApiManager.getInstance().getRecipes(new RecipeApiCallback<List<Recipe>>() {
                @Override
                public void onResponse(final List<Recipe> result) {
                    if (result != null) {
                        mRecipes = result;
                        mRecipesRV.setAdapter(new RecipesAdapter(getActivity().getApplicationContext(), mRecipes, new Listeners.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                mListener.onRecipeSelected(mRecipes.get(position));
                            }
                        }));
                        // Set the default recipe for the widget
                        if (Prefs.loadRecipe(getActivity().getApplicationContext()) == null) {
                            RecipeWidgetService.updateWidget(getActivity(), mRecipes.get(0));
                        }

                    } else {
                        NetworkUtils.createSnackBar(getActivity(), getView(), getString(R.string.failed_to_load), true);
                    }

                    dataLoadedLayout();
                }

                @Override
                public void onCancel() {
                    dataLoadedLayout();
                }

            });
        } else {
            NetworkUtils.createSnackBar(getActivity(), getView(), getString(R.string.no_connection), true);
        }
    }

    private void dataLoadedLayout() {
        boolean loaded = mRecipes != null && mRecipes.size() > 0;
        mPullToRefresh.setRefreshing(false);

        mRecipesRV.setVisibility(loaded ? View.VISIBLE : View.GONE);
        mNoRecipesContainer.setVisibility(loaded ? View.GONE : View.VISIBLE);

        bakingAppApplication.setIdleState(true);

    }

    public interface OnRecipeClickListener {
        void onRecipeSelected(Recipe recipe);
    }
}
