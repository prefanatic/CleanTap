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

package io.github.prefanatic.cleantap.common;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewUpdateEvent<T> {
    private List<T> added;
    private List<T> removed;
    private List<T> set;
    private List<T> cache;

    // TODO: 11/30/2015 Do we need updated?

    public RecyclerViewUpdateEvent() {
        added = new ArrayList<>();
        removed = new ArrayList<>();
        set = new ArrayList<>();
        cache = new ArrayList<>();
    }

    public void addToCache(T item) {
        cache.add(item);
    }

    public void updateFromCache() {
        update(cache);
        cache.clear();
    }

    public void update(List<T> items) {
        added.clear();
        removed.clear();

        for (T item : items)
            if (!set.contains(item))
                add(item);
        for (T item : set)
            if (!items.contains(item))
                remove(item);

        set.clear();
        set.addAll(items);
    }

    public void add(T item) {
        added.add(item);
    }

    public void remove(T item) {
        removed.add(item);
    }

    public List<T> getAdded() {
        return added;
    }

    public List<T> getRemoved() {
        return removed;
    }

    public List<T> getSet() {
        return set;
    }

    @Override
    public String toString() {
        return String.format("Set (%d) - adding (%d) - removing (%d)", set.size(), added.size(), removed.size());
    }
}
