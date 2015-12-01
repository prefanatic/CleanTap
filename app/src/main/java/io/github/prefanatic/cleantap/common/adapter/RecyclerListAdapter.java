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

import com.hannesdorfmann.adapterdelegates.AbsDelegationAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.prefanatic.cleantap.common.ClickEvent;
import rx.Observable;
import rx.subjects.PublishSubject;

public class RecyclerListAdapter extends AbsDelegationAdapter<List> {
    protected PublishSubject<ClickEvent<? extends RecyclerView.ViewHolder, ?>> subject;

    public RecyclerListAdapter() {
        super();

        subject = PublishSubject.create();
        items = new ArrayList<>();
    }

    public Observable<ClickEvent<? extends RecyclerView.ViewHolder, ?>> clickEvent() {
        return subject.asObservable();
    }

    public void setDataCopied(List data) {
        this.items.clear();

        Collections.copy(this.items, data);
        notifyDataSetChanged();
    }

    public void setDataReference(List data) {
        this.items = data;
        notifyDataSetChanged();
    }

    public void addItem(Object item) {
        this.items.add(item);
        notifyItemInserted(this.items.size());
    }

    public void addItem(Object item, int index) {
        this.items.add(index, item);
        notifyItemInserted(index);
    }

    public void removeItem(Object item) {
        int i = this.items.indexOf(item);
        this.items.remove(item);

        notifyItemRemoved(i);
    }

    public void clear() {
        this.items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
