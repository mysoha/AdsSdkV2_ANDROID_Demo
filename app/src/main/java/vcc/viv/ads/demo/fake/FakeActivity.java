package vcc.viv.ads.demo.fake;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import vcc.viv.ads.demo.DummyData;
import vcc.viv.ads.demo.databinding.ActivityFakeBinding;
import vcc.viv.ads.demo.utility.Utility;
import vcc.viv.ads.transport.VccAds;
import vcc.viv.ads.transport.VccAdsListener;

public class FakeActivity extends AppCompatActivity implements DummyData {
    /* **********************************************************************
     * Area : Variable - Const
     ********************************************************************** */
    private final String TAG = FakeActivity.class.getSimpleName();
    private final String URL = "https://adi.admicro.vn/adt/tvc/files/html/sdkapp/medium.html?format=%s";
    private static final String KEY_FORMAT = "key:format";

    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private ActivityFakeBinding binding;
    private VccAds vccAds;

    /* **********************************************************************
     * Area : Starter
     ********************************************************************** */
    public static void starter(Context context, String format) {
        Intent intent = new Intent(context, FakeActivity.class);
        intent.putExtra(KEY_FORMAT, format);
        context.startActivity(intent);
    }

    /* **********************************************************************
     * Area : Function - Override
     ********************************************************************** */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFakeBinding.inflate(getLayoutInflater());
        ViewGroup view = (ViewGroup) binding.getRoot();
        setContentView(view);

        vccAds = VccAds.getInstance();
        vccAds.onVccAdsListener(TAG, new VccAdsListener() {
            @Override
            public void initSuccess() {
            }

            @Override
            public void adStorePrepared() {

            }

            @Override
            public void adRequestFail(String s, String s1, String s2) {

            }

            @Override
            public void adRequestSuccess(String s, String s1, String s2, String s3) {

            }
        });
        vccAds.adSetupView(TAG, binding.advertising, null);

        Bundle bundle = getIntent().getExtras();
        String format = bundle.getString(KEY_FORMAT, "");
        if (TextUtils.isEmpty(format)) {
            finish();
            return;
        }

        WebSettings settings = binding.web.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAllowFileAccess(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            binding.web.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            binding.web.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        binding.web.setVerticalScrollBarEnabled(false);
        binding.web.setHorizontalScrollBarEnabled(false);
        binding.web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (request != null && request.getUrl() != null) {
                        String url = request.getUrl().toString();
                        redirect(url);
                    }
                }
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!TextUtils.isEmpty(url)) {
                    redirect(url);
                }
                return true;
            }
        });

        binding.web.addJavascriptInterface(new WebAppInterface(), "JavaScriptInterfaceSDK");
        binding.web.loadUrl(String.format(URL, format));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /* **********************************************************************
     * Area : Function
     ********************************************************************** */
    private void redirect(String url) {
        if (Utility.isDynamicLink(url)) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            vccAds.openBrowser(url, 0, "");
        }

    }

    /* **********************************************************************
     * Area : Inner Class
     ********************************************************************** */
    public class WebAppInterface {
        public WebAppInterface() {
        }

        @JavascriptInterface
        public void sdkAction(String message) throws JSONException {
            JSONObject data = new JSONObject(message);
            String landingType = data.optString("landingtype", "");
            String landing = data.optString("landing", "");
            String deep = data.optString("deeplink", "");
            String brandLogo = data.optString("brandlogo", "");
            if (TextUtils.isEmpty(deep) || TextUtils.isEmpty(landing)) return;
            JSONObject deepLinkJson = new JSONObject(deep);
            JSONObject landingJson = new JSONObject(landing);
            String deepLink = deepLinkJson.optString("link", "");
            String landLink = landingJson.optString("link", "");
            String url = TextUtils.isEmpty(deepLink) ? landLink : deepLink;

            vccAds.openBrowser(url, Integer.parseInt(landingType), brandLogo);
        }
    }
}
