package android.map.longdo.com.htmleditor.fragment;


import android.app.ProgressDialog;
import android.map.longdo.com.htmleditor.utility.FileUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.map.longdo.com.htmleditor.R;
import android.widget.Button;
import android.widget.EditText;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.nio.charset.Charset;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SourceCodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SourceCodeFragment extends Fragment {

    private static final String ARG_PARAM_HTML = "HTML";
    private static final String ARG_PARAM_URL = "URL";
    private static final String FILENAME_SOURCE_CODE = "last_source_code";
    private static final String FILENAME_URL = "last_url";


    private EditText etURL;
    private EditText etSourceCode;
    private ButtonRectangle btRequest;
    private ProgressDialog pdRequesting;

    public static SourceCodeFragment newInstance() {
        return new SourceCodeFragment();
    }

    public SourceCodeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_source_code, container, false);

        initViews(view);
        initListeners();

        return view;

    }

    private void initViews(View view) {
        etURL = (EditText) view.findViewById(R.id.et_url);
        etSourceCode = (EditText) view.findViewById(R.id.et_source_code);
        btRequest = (ButtonRectangle) view.findViewById(R.id.bt_request);

        String lastURL = FileUtils.loadFile(getActivity(),FILENAME_URL,"");
        String lastSourceCode = FileUtils.loadFile(getActivity(),FILENAME_SOURCE_CODE,"");

        etSourceCode.setText(lastSourceCode);
        etURL.setText(lastURL);
    }

    private void initListeners() {
        btRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = etURL.getText().toString();
                requestHTMLFromURL(url, etSourceCode);
            }
        });

        etURL.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                FileUtils.saveFile(getActivity(),FILENAME_URL,etURL.getText().toString());
            }
        });

        etSourceCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                FileUtils.saveFile(getActivity(),FILENAME_SOURCE_CODE,etSourceCode.getText().toString());
            }
        });
    }

    private void requestHTMLFromURL(final String url, final EditText sourceViewer) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                pdRequesting = new ProgressDialog(getActivity());
                pdRequesting.setMessage(getResources().getString(R.string.loading));
                pdRequesting.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"


                final String sourceCode = new String(response, Charset.forName("UTF8"));
                sourceViewer.setText(sourceCode);

                if(pdRequesting!=null && pdRequesting.isShowing()) {
                    pdRequesting.dismiss();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                if(pdRequesting!=null && pdRequesting.isShowing()) {
                    pdRequesting.dismiss();
                }
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }

    public String getSourceCode() {

        return etSourceCode!=null ? etSourceCode.getText().toString() : "";
    }

}
