package android.map.longdo.com.htmleditor.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.map.longdo.com.htmleditor.R;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class PreviewFragment extends Fragment {

    private WebView wvPreview;

    public static SourceCodeFragment newInstance() {
        return new SourceCodeFragment();
    }

    public PreviewFragment() {
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

    private void initViews(View view) {
        wvPreview = (WebView) view.findViewById(R.id.wv_preview);
        WebSettings webSettings = wvPreview.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    public void setPreview(String html) {
        wvPreview.loadData(html, "text/html; charset=utf-8", "utf-8");
    }


}
