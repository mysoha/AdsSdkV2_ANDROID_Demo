package vcc.viv.ads.demo.basic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.demo.DummyData;
import vcc.viv.ads.demo.databinding.ActivityBasicBinding;
import vcc.viv.ads.transport.VccAds;
import vcc.viv.ads.transport.VccAdsListener;
import vcc.viv.ads.transport.ontouch.VccOnTouchHandler;

public class BasicActivity extends AppCompatActivity implements DummyData {
    /* **********************************************************************
     * Area : Variable - Const
     ********************************************************************** */
    private final String TAG = BasicActivity.class.getSimpleName();
    private static final String KEY_ID = "key:ad_id";

    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private ActivityBasicBinding binding;

    private VccAds vccAds;
    private final String requestId = "1";
    private final List<String> adIds = new ArrayList<String>() {{
    }};

    /* **********************************************************************
     * Area : Starter
     ********************************************************************** */
    public static void starter(Context context, String id) {
        Intent intent = new Intent(context, BasicActivity.class);
        intent.putExtra(KEY_ID, id);
        context.startActivity(intent);
    }

    /* **********************************************************************
     * Area : Function - Override
     ********************************************************************** */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBasicBinding.inflate(getLayoutInflater());
        ViewGroup view = (ViewGroup) binding.getRoot();
        setContentView(view);

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString(KEY_ID, AD_BANNER_ID);
        adIds.add(id);

        vccAds = VccAds.getInstance();
        vccAds.onVccAdsListener(TAG, new VccAdsHandler());
        vccAds.adSetupView(TAG, binding.root, null, new VccOnTouchHandler());
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
        public void adRequestFail(String tag, String request, String adId) {
            Log.d(TAG, String.format("AD REQUEST - Fail : tag[%s] - requestId[%s] - adId[%s]", tag, request, adId));
        }

        @Override
        public void adRequestSuccess(String tag, String request, String adId, String adType) {
            Log.d(TAG, String.format("AD REQUEST - Success : requestId[%s] - adId[%s] - adType[%s]", tag, request, adId, adType));
        }

        @Override
        public void closeAd(String tag, String requestId, String adId) {
            super.closeAd(tag, requestId, adId);
            Log.d(TAG, String.format("AD CLOSE - Success : requestId[%s] - adId[%s] - tag[%s]", requestId, adId, tag));
            if ("WelcomeActivity".equals(tag)) onBackPressed();
        }
    }
}
