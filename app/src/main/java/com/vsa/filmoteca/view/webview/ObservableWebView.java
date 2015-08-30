package com.vsa.filmoteca.view.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by seldon on 14/03/15.
 */
public class ObservableWebView extends WebView
{
    private OnScrollChangedCallback mOnScrollChangedCallback;

    private OnContentLoadedListener mOnContentLoadedListener;

    public ObservableWebView(final Context context)
    {
        super(context);
        setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (mOnContentLoadedListener != null)
                    mOnContentLoadedListener.onContentLoaded();
            }
        });
    }

    public ObservableWebView(final Context context, final AttributeSet attrs)
    {
        super(context, attrs);
        setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (mOnContentLoadedListener != null)
                    mOnContentLoadedListener.onContentLoaded();
            }
        });
    }

    public ObservableWebView(final Context context, final AttributeSet attrs, final int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(final int l, final int t, final int oldl, final int oldt)
    {
        super.onScrollChanged(l, t, oldl, oldt);
        if(mOnScrollChangedCallback != null) mOnScrollChangedCallback.onScroll(l, t);
    }

    public OnScrollChangedCallback getOnScrollChangedCallback()
    {
        return mOnScrollChangedCallback;
    }

    public void setOnScrollChangedCallback(final OnScrollChangedCallback onScrollChangedCallback)
    {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    /**
     * Impliment in the activity/fragment/view that you want to listen to the webview
     */
    public static interface OnScrollChangedCallback
    {
        public void onScroll(int l, int t);
    }

    public void setOnContentLoadedListener (OnContentLoadedListener onContentLoadedListener) {
        mOnContentLoadedListener = onContentLoadedListener;
    }

    public interface OnContentLoadedListener {
        public void onContentLoaded();
    }
}
