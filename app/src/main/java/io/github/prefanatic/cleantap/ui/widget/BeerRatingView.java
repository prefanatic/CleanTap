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

package io.github.prefanatic.cleantap.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.prefanatic.cleantap.R;
import io.github.prefanatic.cleantap.data.dto.BeerExtended;
import io.github.prefanatic.cleantap.util.TextFormatUtil;

/**
 * Created by cody on 11/21/15.
 */
public class BeerRatingView extends FrameLayout {
    @Bind(R.id.rating) TextView rating;
    @Bind(R.id.count) TextView ratingCount;

    public BeerRatingView(Context context) {
        this(context, null);
    }

    public BeerRatingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BeerRatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.card_beer_rating, this, false);
        ButterKnife.bind(this, view);

        addView(view);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setValues(BeerExtended beer) {
        rating.setText(String.format("%.2f", beer.rating_score));
        ratingCount.setText(TextFormatUtil.LongToFormattedString(beer.stats.total_count));
    }
}
