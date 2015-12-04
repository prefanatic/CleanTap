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

package io.github.prefanatic.cleantap.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.prefanatic.cleantap.R;
import io.github.prefanatic.cleantap.data.RxUntappdApi;
import io.github.prefanatic.cleantap.data.dto.BeerDto;
import io.github.prefanatic.cleantap.injection.Injector;
import io.github.prefanatic.cleantap.ui.animation.InfoAndCheckinAnimation;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CheckinActivity extends Activity {
    @Bind(R.id.frame) FrameLayout frame;
    @Bind(R.id.container) ViewGroup container;
    @Bind(R.id.seekbar) DiscreteSeekBar seekBar;
    @Bind(R.id.confirm) TextView confirm;
    @Bind(R.id.review) EditText reviewBox;
    @Bind(R.id.rating) TextView rating;

    @Inject RxUntappdApi api;

    private BeerDto beerDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        ButterKnife.bind(this);
        Injector.INSTANCE.getApplicationComponent().inject(this);

        beerDto = ((BeerDto) getIntent().getSerializableExtra("beer"));

        InfoAndCheckinAnimation.setupSharedElementTransitionsFab(this, container, getResources().getDimensionPixelSize(R.dimen.dialog_corners));

        seekBar.setNumericTransformer(new DiscreteSeekBar.NumericTransformer() {
            @Override
            public int transform(int value) {
                return 0;
            }

            @Override
            public String transformToString(int value) {
                String val = String.format("%.2f", (float) value / 4);
                rating.setText(val);

                return val;
            }

            @Override
            public boolean useStringTransform() {
                return true;
            }
        });
    }

    @OnClick(R.id.confirm)
    public void confirmed() {
        if (reviewBox.length() > 140) return;

        api.checkinBeer(beerDto.bid, reviewBox.getText().toString(), (float) seekBar.getProgress() / 4, null, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    dismiss(null);
                });
    }

    public void dismiss(View view) {
        setResult(Activity.RESULT_CANCELED);
        finishAfterTransition();
    }

    @Override
    protected void onDestroy() {
        seekBar.setNumericTransformer(null);
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        dismiss(null);
    }
}
