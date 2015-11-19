package io.github.prefanatic.cleantap.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

public class ButterKnifeViewHolder extends RecyclerView.ViewHolder {
    public ButterKnifeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
