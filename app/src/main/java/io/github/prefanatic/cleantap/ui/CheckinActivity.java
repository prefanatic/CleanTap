package io.github.prefanatic.cleantap.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.prefanatic.cleantap.R;
import io.github.prefanatic.cleantap.ui.animation.InfoAndCheckinAnimation;

public class CheckinActivity extends Activity {
    @Bind(R.id.container) ViewGroup container;
    @Bind(R.id.seekbar) DiscreteSeekBar seekBar;
    @Bind(R.id.confirm) TextView confirm;
    @Bind(R.id.review) EditText reviewBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        ButterKnife.bind(this);

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
        dismiss(null);
    }

    public void dismiss(View view) {
        setResult(Activity.RESULT_CANCELED);
        finishAfterTransition();
    }

    @Override
    public void onBackPressed() {
        dismiss(null);
    }
}
