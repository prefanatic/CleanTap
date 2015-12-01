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

package io.github.prefanatic.cleantap.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.prefanatic.cleantap.R;
import io.github.prefanatic.cleantap.common.adapter.delegation.Title;

public class TitleRecyclerListAdapter extends RecyclerListAdapter implements StickyRecyclerHeadersAdapter<TitleRecyclerListAdapter.ViewHolder> {
    public List<Title> headers = new ArrayList<>();
    public Map<Object, Integer> headerMap = new HashMap<>();

    public TitleRecyclerListAdapter() {
    }

    public void addItemUnderHeader(int id, Object item) {
        addItem(item);
        headerMap.put(item, id);
    }

    public void addItemUnderHeader(int id, int index, Object item) {
        addItem(item, index);
        headerMap.put(item, id);
    }

    @Override
    public void removeItem(Object item) {
        super.removeItem(item);
        headerMap.remove(item);
    }

    public void addHeader(long id, String title) {
        headers.add(new Title(id, title));
    }

    @Override
    public long getHeaderId(int position) {
        Integer i = headerMap.get(items.get(position));

        /*
        Timber.d("In Items");
        for (Object o : items) {
            Timber.d("%s", o);
        }

        Timber.d("In HeaderMap");
        for (Object o : headerMap.keySet()) {
            Timber.d("%s", o);
        }

        Timber.d("Looking for position %d (%s) is %d", position, items.get(position), i);
        */

        return i;
    }

    @Override
    public void onBindHeaderViewHolder(ViewHolder holder, int position) {
        ((TextView) holder.itemView).setText(headers.get((int) getHeaderId(position)).title);
    }

    @Override
    public ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_title, parent, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
