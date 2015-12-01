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

package io.github.prefanatic.cleantap.data.oauth;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.prefanatic.cleantap.R;
import io.github.prefanatic.cleantap.common.PreferenceKeys;
import io.github.prefanatic.cleantap.data.RxUntappdApi;
import io.github.prefanatic.cleantap.injection.Injector;
import io.github.prefanatic.cleantap.ui.widget.WebViewFix;

public class AuthDialog extends DialogFragment {
    private static final String CLIENT_ID = "EFED9A1C92001BD10DEE9BA371181CBA0B0D831A";
    public static final String REDIRECT_URL = "localhost";

    @Bind(R.id.webview) WebViewFix webView;
    @Inject RxUntappdApi api;
    @Inject SharedPreferences preferences;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Injector.INSTANCE.getApplicationComponent().inject(this);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_oauth, null);
        ButterKnife.bind(this, view);

        webView.loadUrl(String.format("https://untappd.com/oauth/authenticate/?client_id=%s&response_type=token&redirect_url=%s", CLIENT_ID, REDIRECT_URL));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                // TODO: 11/19/2015 Make some nice animation signaling the auth worked.
                if (url.startsWith("https://untappd.com/oauth/localhost#access_token=")) {
                    String authToken = url.replace("https://untappd.com/oauth/localhost#access_token=", "");
                    preferences.edit().putString(PreferenceKeys.AUTH_TOKEN, authToken).apply();

                    dismiss();
                } else if (url.startsWith("https://untappd.com/oauth/authenticate/localhost#access_token=")) {
                    String authToken = url.replace("https://untappd.com/oauth/authenticate/localhost#access_token=", "");
                    preferences.edit().putString(PreferenceKeys.AUTH_TOKEN, authToken).apply();

                    dismiss();
                }
            }
        });

        // https://code.google.com/p/android/issues/detail?id=7189
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        webView.requestFocus(View.FOCUS_DOWN);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

}
