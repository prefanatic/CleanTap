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
