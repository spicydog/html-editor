package org.spicydog.android.htmleditor.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.spicydog.android.htmleditor.R;
import android.webkit.WebSettings;
import android.webkit.WebView;

@SuppressLint("ValidFragment")
public class PreviewFragment extends Fragment {

    Context mContext;

    private WebView wvPreview;

    public PreviewFragment(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_preview, container, false);
        initViews(view);
        return view;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initViews(View view) {
        wvPreview = (WebView) view.findViewById(R.id.wv_preview);
        WebSettings webSettings = wvPreview.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    public void setPreview(String html) {
        wvPreview.loadData(html, "text/html; charset=utf-8", "utf-8");
    }


}
