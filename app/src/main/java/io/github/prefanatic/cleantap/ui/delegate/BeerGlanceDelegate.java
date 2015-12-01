/*
 * Copyright 2015 Cody Goldberg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
