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

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.prefanatic.cleantap.R;
import io.github.prefanatic.cleantap.common.PreferenceKeys;
import io.github.prefanatic.cleantap.injection.Injector;

public class SearchFilterDialog extends DialogFragment {
    @Bind(R.id.checkbox_favorite) CheckBox favoriteBox;
    @Bind(R.id.checkbox_recent) CheckBox recentBox;

    @Inject SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.INSTANCE.getApplicationComponent().inject(this);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_search_filter, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        ButterKnife.bind(this, view);
        builder.setView(view)
                .setTitle(R.string.filter_title)
                .setPositiveButton(R.string.filter_ok, (dialog, which) -> save())
                .setNeutralButton(R.string.filter_cancel, (dialog, which) -> dismiss());

        favoriteBox.setChecked(preferences.getBoolean(PreferenceKeys.SEARCH_FAVORITE, true));
        recentBox.setChecked(preferences.getBoolean(PreferenceKeys.SEARCH_RECENT, true));

        return builder.create();
    }

    private void save() {
        preferences.edit()
                .putBoolean(PreferenceKeys.SEARCH_FAVORITE, favoriteBox.isChecked())
                .putBoolean(PreferenceKeys.SEARCH_RECENT, recentBox.isChecked())
                .apply();

        dismiss();
    }
}
