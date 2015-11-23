package io.github.prefanatic.cleantap.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.prefanatic.cleantap.R;
import io.github.prefanatic.cleantap.data.dto.Brewery;

public class BreweryGlanceView extends FrameLayout {
    @Bind(R.id.brewery_name) TextView breweryName;
    @Bind(R.id.brewery_location) TextView breweryLocation;
    @Bind(R.id.brewery_icon) ImageView breweryIcon;
    @Bind(R.id.button_location) ImageButton buttonLocation;
    @Bind(R.id.button_web) ImageButton buttonWeb;
    @Bind(R.id.button_facebook) ImageButton buttonFacebook;
    @Bind(R.id.button_twitter) ImageButton buttonTwitter;

    public BreweryGlanceView(Context context) {
        this(context, null);
    }

    public BreweryGlanceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BreweryGlanceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.card_brewery_glance, this, false);
        ButterKnife.bind(this, view);

        addView(view);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setValues(Brewery brewery) {
        breweryName.setText(brewery.brewery_name);
        breweryLocation.setText(brewery.country_name);
        Glide.with(getContext()).load(brewery.brewery_label).into(breweryIcon);

        // TODO: 11/22/2015 Buttons
    }
}
