package io.github.prefanatic.cleantap.common;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class RecyclerListAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<T> data = new ArrayList<>();

    public void setDataCopied(List<T> data) {
        this.data.clear();

        Collections.copy(this.data, data);
        notifyDataSetChanged();
    }

    public void setDataReference(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        this.data.add(item);
        notifyItemInserted(this.data.size());
    }

    public void removeItem(T item) {
        int i = this.data.indexOf(item);
        this.data.remove(item);

        notifyItemRemoved(i);
    }

    public void clear() {
        this.data.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
