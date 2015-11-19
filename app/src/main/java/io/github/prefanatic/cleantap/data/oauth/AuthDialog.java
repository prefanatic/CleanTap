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

public class AuthDialog extends DialogFragment {
    private static final String CLIENT_ID = "EFED9A1C92001BD10DEE9BA371181CBA0B0D831A";
    public static final String REDIRECT_URL = "localhost";

    @Bind(R.id.webview) WebView webView;
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

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

}
