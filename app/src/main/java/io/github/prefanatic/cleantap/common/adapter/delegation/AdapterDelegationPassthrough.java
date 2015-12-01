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

package io.github.prefanatic.cleantap.common.adapter.delegation;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates.AbsAdapterDelegate;

import java.util.List;

/**
 * Created by cody on 11/21/15.
 */
public abstract class AdapterDelegationPassthrough<O, VH extends RecyclerView.ViewHolder> extends AbsAdapterDelegate<List> {
    protected LayoutInflater inflater;

    public AdapterDelegationPassthrough(Activity activity, int viewType) {
        super(viewType);
        inflater = activity.getLayoutInflater();
    }

    @Override
    public final void onBindViewHolder(@NonNull List items, int position, @NonNull RecyclerView.ViewHolder holder) {
        onBindViewHolder((O) items.get(position), (VH) holder);
    }

    public abstract void onBindViewHolder(O item, VH holder);

    @NonNull
    @Override
    public abstract VH onCreateViewHolder(ViewGroup viewGroup);

    @Override
    public final boolean isForViewType(@NonNull List list, int i) {
        return isForViewType(list.get(i));
    }

    public abstract boolean isForViewType(Object item);
}

