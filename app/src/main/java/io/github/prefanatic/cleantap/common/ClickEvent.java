package io.github.prefanatic.cleantap.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ClickEvent<V extends RecyclerView.ViewHolder, T> {
    public V viewHolder;
    public T item;

    public ClickEvent(V viewHolder, T item) {
        this.viewHolder = viewHolder;
        this.item = item;
    }
}