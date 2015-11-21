package io.github.prefanatic.cleantap.ui.delegate;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import io.github.prefanatic.cleantap.common.ButterKnifeViewHolder;
import io.github.prefanatic.cleantap.common.adapter.delegation.AdapterDelegationPassthrough;
import io.github.prefanatic.cleantap.data.dto.BeerExtended;
import io.github.prefanatic.cleantap.ui.widget.BeerRatingView;

/**
 * Created by cody on 11/21/15.
 */
public class BeerRatingDelegate extends AdapterDelegationPassthrough<BeerExtended, BeerRatingDelegate.ViewHolder> {

    public BeerRatingDelegate(Activity activity, int viewType) {
        super(activity, viewType);
    }

    @Override
    public void onBindViewHolder(BeerExtended item, ViewHolder holder) {
        BeerRatingView view = (BeerRatingView) holder.itemView;

        view.setValues(item);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(new BeerRatingView(viewGroup.getContext()));
    }

    @Override
    public boolean isForViewType(Object item) {
        return item instanceof BeerExtended;
    }

    public static class ViewHolder extends ButterKnifeViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
