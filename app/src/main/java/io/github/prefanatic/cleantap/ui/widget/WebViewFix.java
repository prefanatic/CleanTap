package io.github.prefanatic.cleantap.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.webkit.WebView;

public class WebViewFix extends WebView {
    private boolean layoutChanged = false;

    public WebViewFix(Context context) {
        super(context);
    }

    public WebViewFix(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WebViewFix(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!layoutChanged) {
            super.onLayout(changed, l, t, r, b);
            layoutChanged = true;
        }
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(true, direction, previouslyFocusedRect);
    }

    @Override
    public boolean onCheckIsTextEditor() {
        return true;
    }
}
