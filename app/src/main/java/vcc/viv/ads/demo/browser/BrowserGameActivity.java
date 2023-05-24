package vcc.viv.ads.demo.browser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.demo.BaseActivity;
import vcc.viv.ads.demo.DummyData;
import vcc.viv.ads.demo.basic.BasicActivity;
import vcc.viv.ads.demo.databinding.ActivityCustomBrowserBinding;
import vcc.viv.ads.transport.VccAds;
import vcc.viv.ads.transport.VccAdsListener;

public class BrowserGameActivity extends BaseActivity implements DummyData {
    /* **********************************************************************
     * Area : Variable - Const
     ********************************************************************** */
    private final String TAG = BasicActivity.class.getSimpleName();

    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private ActivityCustomBrowserBinding binding;

    private VccAds vccAds;
    private final String requestId = "1";
    private final List<String> adIds = new ArrayList<String>() {{
        add(AD_BANNER_ID);
    }};

    /* **********************************************************************
     * Area : Starter
     ********************************************************************** */
    public static void starter(Context context) {
        Intent intent = new Intent(context, BrowserGameActivity.class);
        context.startActivity(intent);
    }

    /* **********************************************************************
     * Area : Function - Override
     ********************************************************************** */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCustomBrowserBinding.inflate(getLayoutInflater());
        ViewGroup view = (ViewGroup) binding.getRoot();
        setContentView(view);

        vccAds = VccAds.getInstance();
        vccAds.onVccAdsListener(TAG, new VccAdsHandler());
        vccAds.adSetupView(TAG, view, null, null);
        vccAds.adRequest(TAG, requestId, adIds);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (vccAds != null) {
            vccAds.onVccAdsListener(TAG, null);
        }
    }

    /* **********************************************************************
     * Area : Function
     ********************************************************************** */

    /* **********************************************************************
     * Area : Inner Class
     ********************************************************************** */
    private class VccAdsHandler extends VccAdsListener {
        @Override
        public void initPrepare() {
        }

        @Override
        public void initSuccess() {
        }

        @Override
        public void adStorePrepared() {
            vccAds.adAdd(binding.advertising, TAG, requestId, adIds.get(0));
        }

        @Override
        public void adRequestFail(String tag, String request, String adId, String msg) {
            Log.d(TAG, String.format("AD REQUEST - Fail : tag[%s] - requestId[%s] - adId[%s] - msg[%s]", tag, request, adId,msg));
        }

        @Override
        public void adRequestSuccess(String tag, String request, String adId, String adType) {
            Log.d(TAG, String.format("AD REQUEST - Success : requestId[%s] - adId[%s] - adType[%s]", tag, request, adId, adType));
        }
    }
}