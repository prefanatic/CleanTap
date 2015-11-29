package io.github.prefanatic.cleantap.ui.delegate;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import io.github.prefanatic.cleantap.common.ButterKnifeViewHolder;
import io.github.prefanatic.cleantap.common.adapter.delegation.AdapterDelegationPassthrough;
import io.github.prefanatic.cleantap.data.dto.BeerStatsDto;
import io.github.prefanatic.cleantap.ui.widget.BeerGlanceView;

/**
 * Created by cody on 11/21/15.
 */
public class BeerGlanceDelegate extends AdapterDelegationPassthrough<BeerStatsDto, BeerGlanceDelegate.ViewHolder> {

    public BeerGlanceDelegate(Activity activity, int viewType) {
        super(activity, viewType);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(new BeerGlanceView(viewGroup.getContext()));
    }

    @Override
    public void onBindViewHolder(BeerStatsDto item, ViewHolder holder) {
        BeerGlanceView view = (BeerGlanceView) holder.itemView;

        view.setValues(item);
    }

    @Override
    public boolean isForViewType(Object item) {
        return item instanceof BeerStatsDto;
    }

    static class ViewHolder extends ButterKnifeViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
