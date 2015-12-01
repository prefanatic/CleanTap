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

package io.github.prefanatic.cleantap.ui.animation;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.transition.ArcMotion;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import io.github.prefanatic.cleantap.R;
import io.github.prefanatic.cleantap.ui.transition.MorphDialogToFab;
import io.github.prefanatic.cleantap.ui.transition.MorphFabToDialog;

public class InfoAndCheckinAnimation {
    public static void setupSharedElementTransitionsFab(@NonNull Activity activity,
                                                        @Nullable View target,
                                                        int dialogCornerRadius) {
        ArcMotion arcMotion = new ArcMotion();
        arcMotion.setMinimumHorizontalAngle(50f);
        arcMotion.setMinimumVerticalAngle(50f);
        int color = ContextCompat.getColor(activity, R.color.colorAccent);
        Interpolator easeInOut =
                AnimationUtils.loadInterpolator(activity, android.R.interpolator.fast_out_slow_in);
        MorphFabToDialog sharedEnter = new MorphFabToDialog(color, dialogCornerRadius);
        sharedEnter.setPathMotion(arcMotion);
        sharedEnter.setInterpolator(easeInOut);
        MorphDialogToFab sharedReturn = new MorphDialogToFab(color);
        sharedReturn.setPathMotion(arcMotion);
        sharedReturn.setInterpolator(easeInOut);
        if (target != null) {
            sharedEnter.addTarget(target);
            sharedReturn.addTarget(target);
        }
        activity.getWindow().setSharedElementEnterTransition(sharedEnter);
        activity.getWindow().setSharedElementReturnTransition(sharedReturn);
    }

    private static boolean fabIsAnimating = false;
    public static void hideFab(FloatingActionButton fab) {
        if (fabIsAnimating) return;

        fab.animate()
                .translationY(fab.getHeight() + fab.getPaddingBottom() + fab.getPaddingTop())
                .withEndAction(() -> fabIsAnimating = false)
                .start();
        fabIsAnimating = true;
    }

    public static void showFab(FloatingActionButton fab) {
        if (fabIsAnimating) return;

        fab.animate()
                .translationY(0)
                .withEndAction(() -> fabIsAnimating = false)
                .start();
        fabIsAnimating = true;
    }
}
