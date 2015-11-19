package io.github.prefanatic.cleantap.common;

import android.view.View;

public class ClickEvent<T> {
    public View view;
    public T item;

    public ClickEvent(View view, T item) {
        this.view = view;
        this.item = item;
    }
}