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
import io.github.prefanatic.cleantap.data.dto.Brewery;
import io.github.prefanatic.cleantap.ui.widget.BreweryGlanceView;

public class BreweryGlanceDelegate extends AdapterDelegationPassthrough<Brewery, BreweryGlanceDelegate.ViewHolder> {
    public BreweryGlanceDelegate(Activity activity, int viewType) {
        super(activity, viewType);
    }

    @Override
    public void onBindViewHolder(Brewery item, ViewHolder holder) {
        BreweryGlanceView view = (BreweryGlanceView) holder.itemView;

        view.setValues(item);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(new BreweryGlanceView(viewGroup.getContext()));
    }

    @Override
    public boolean isForViewType(Object item) {
        return item instanceof Brewery;
    }

    static class ViewHolder extends ButterKnifeViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
