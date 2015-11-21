package io.github.prefanatic.cleantap.common.adapter.delegation;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates.AbsAdapterDelegate;

import java.util.List;

import io.github.prefanatic.cleantap.common.ClickEvent;
import io.github.prefanatic.cleantap.data.dto.BeerStats;
import rx.Observable;
import rx.subjects.PublishSubject;

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

