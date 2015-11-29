package io.github.prefanatic.cleantap.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
    @Bind(R.id.container) ViewGroup container;
    @Bind(R.id.seekbar) DiscreteSeekBar seekBar;
    @Bind(R.id.confirm) TextView confirm;
    @Bind(R.id.review) EditText reviewBox;

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
                return String.format("%.2f", (float) value / 4);
            }

            @Override
            public boolean useStringTransform() {
                return true;
            }
        });
    }

    @OnClick(R.id.confirm)
    public void confirmed() {
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
