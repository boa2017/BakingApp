package com.example.android.bakingapp.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by beita on 17/05/2018.
 */

public class StepsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.step_order_text)
    public TextView mTvStepOrder;

    @BindView(R.id.list_step_description)
    public TextView mTvStepName;

    public StepsViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

}
