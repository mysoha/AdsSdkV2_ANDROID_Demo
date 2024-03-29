package vcc.viv.ads.demo.mix;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import vcc.viv.ads.demo.BaseActivity;
import vcc.viv.ads.demo.DummyData;
import vcc.viv.ads.demo.R;
import vcc.viv.ads.demo.databinding.ActivityMixScrollBinding;
import vcc.viv.ads.demo.databinding.ItemBasicBinding;
import vcc.viv.ads.demo.utility.Utility;
import vcc.viv.ads.transport.VccAds;
import vcc.viv.ads.transport.VccAdsListener;
import vcc.viv.ads.transport.ontouch.VccOnTouchHandler;
import vcc.viv.ads.transport.scroll.VccScrollHandler;

public class ScrollActivity extends BaseActivity implements DummyData {
    /* **********************************************************************
     * Area : Variable - Const
     ********************************************************************** */
    private final String TAG = ScrollActivity.class.getSimpleName();

    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private ActivityMixScrollBinding binding;

    private VccAds vccAds;
    private final String requestId = "1";
    private final List<String> adIds = AD_BANNER_IDS;

    /* **********************************************************************
     * Area : Starter
     ********************************************************************** */
    public static void starter(Context context) {
        Intent intent = new Intent(context, ScrollActivity.class);
        context.startActivity(intent);
    }

    /* **********************************************************************
     * Area : Function - Override
     ********************************************************************** */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = getLayoutInflater();
        binding = ActivityMixScrollBinding.inflate(inflater);
        ViewGroup view = (ViewGroup) binding.getRoot();
        setContentView(view);

        MyScroll scroll = new MyScroll();
        binding.scroll.setOnScrollChangeListener(scroll);

        MyTouch onTouch = new MyTouch();
        binding.scroll.setOnTouchListener(onTouch);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int) Utility.dpToPx(this, 1);
        for (int i = 0; i < adIds.size(); i++) {
            try {
                String id = adIds.get(i);
                ItemBasicBinding itemBinding = ItemBasicBinding.inflate(inflater);
                itemBinding.replace.setId(Integer.parseInt(id));
                itemBinding.title.setText(id);
                binding.advertising.addView(itemBinding.getRoot(), layoutParams);

                for (int j = 0; j < FAKE_ITEM_VIEW_COUNT; j++) {
                    addLineDivider();
                    addFakeView();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        vccAds = VccAds.getInstance();
        vccAds.onVccAdsListener(TAG, new VccAdsHandler());
        vccAds.adSetupView(TAG, binding.root, scroll, onTouch);
//        vccAds.setExtraInfo("0", "1", "https://kenh14.vn/bi-mat-trong-lang-mo-tan-thuy-hoang-hoa-ra-khong-the-khai-quat-la-do-lop-tuong-dac-biet-20211113111052856.chn", "https://app.kenh14.vn/home");
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
    private void addLineDivider() {
        LinearLayout.LayoutParams viewLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Utility.dpToPx(this, 1));
        View fake = new View(this);
        fake.setBackgroundColor(getResources().getColor(R.color.white, getTheme()));
        binding.advertising.addView(fake, viewLp);
    }

    private void addFakeView() {
        LinearLayout.LayoutParams viewLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Utility.dpToPx(this, 64));
        View fake = new View(this);
        fake.setBackgroundColor(getResources().getColor(R.color.secondaryColor, getTheme()));
        binding.advertising.addView(fake, viewLp);
    }

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
            if (adIds == null) return;
            for (int i = 0; i < adIds.size(); i++) {
                try {
                    String id = adIds.get(i);
                    ViewGroup layout = binding.getRoot().findViewById(Integer.parseInt(id));
                    vccAds.adAdd(layout, TAG, requestId, id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void adRequestFail(String tag, String request, String adId, String msg) {
            Log.d(TAG, String.format("AD REQUEST - Fail : tag[%s] - requestId[%s] - adId[%s] - msg[%s]", tag, request, adId, msg));
        }

        @Override
        public void adRequestSuccess(String tag, String request, String adId, String adType) {
            Log.d(TAG, String.format("AD REQUEST - Success : tag[%s] - requestId[%s] - adId[%s] - adType[%s]", tag, request, adId, adType));
        }
    }

    private static class MyScroll extends VccScrollHandler {
        @Override
        public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            super.onScrollChange(v, scrollX, scrollY, oldScrollX, oldScrollY);
        }
    }

    private static class MyTouch extends VccOnTouchHandler {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return super.onTouch(v, event);
        }
    }
}
