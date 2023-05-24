package vcc.viv.ads.demo.mix;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.demo.BaseActivity;
import vcc.viv.ads.demo.DummyData;
import vcc.viv.ads.demo.R;
import vcc.viv.ads.demo.databinding.ActivityHideBinding;
import vcc.viv.ads.demo.databinding.ActivityMixListBinding;
import vcc.viv.ads.demo.databinding.ItemBasicBinding;
import vcc.viv.ads.transport.VccAds;
import vcc.viv.ads.transport.VccAdsListener;
import vcc.viv.ads.transport.ontouch.VccOnTouchHandler;
import vcc.viv.ads.transport.scroll.VccScrollHandler;

public class VisibilityActivity extends BaseActivity implements DummyData {
    /* **********************************************************************
     * Area : Variable - Const
     ********************************************************************** */
    private final String TAG = VisibilityActivity.class.getSimpleName();

    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private ActivityHideBinding binding;

    private VccAds vccAds;
    private final String requestId = "1";
    private final List<String> adIds = AD_BANNER_IDS;
    private boolean isPrepared = false;

    /* **********************************************************************
     * Area : Starter
     ********************************************************************** */
    public static void starter(Context context) {
        Intent intent = new Intent(context, VisibilityActivity.class);
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
        binding = ActivityHideBinding.inflate(inflater);
        ViewGroup view = (ViewGroup) binding.getRoot();
        setContentView(view);

        List<String> dummyData = new ArrayList<>();
        for (int i = 0; i < adIds.size(); i++) {
            dummyData.add(adIds.get(i));
            for (int j = 0; j < FAKE_ITEM_VIEW_COUNT; j++) {
                dummyData.add(null);
            }
        }

        Adapter adapter = new Adapter(dummyData);
        binding.advertising.setAdapter(adapter);
        MyScroll scroll = new MyScroll();
        binding.advertising.setOnScrollChangeListener(scroll);

        MyTouch onTouch = new MyTouch();
        binding.advertising.setOnTouchListener(onTouch);

        vccAds = VccAds.getInstance();
        vccAds.onVccAdsListener(TAG, new VccAdsHandler());
        vccAds.adSetupView(TAG, binding.root, scroll, onTouch);
//        vccAds.setExtraInfo("0", "1","https://kenh14.vn/bi-mat-trong-lang-mo-tan-thuy-hoang-hoa-ra-khong-the-khai-quat-la-do-lop-tuong-dac-biet-20211113111052856.chn","https://app.kenh14.vn/home");
        vccAds.adRequest(TAG, requestId, adIds);

        binding.visible.setOnClickListener(v -> {
            vccAds.setVisibility(TAG,requestId,AD_IN_PAGE_ID,true);
        });
        binding.invisible.setOnClickListener(v -> {
            vccAds.setVisibility(TAG,requestId,AD_IN_PAGE_ID,false);
        });

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
            isPrepared = true;
        }

        @Override
        public void adRequestFail(String tag, String request, String adId, String msg) {
            Log.d(TAG, String.format("AD REQUEST - Fail : tag[%s] - requestId[%s] - adId[%s] - msg[%s]", tag, request, adId, msg));
        }

        @Override
        public void adRequestSuccess(String tag, String request, String adId, String adType) {
            Log.d(TAG, String.format("AD REQUEST - Success : tag[%s] - requestId[%s] - adId[%s] - adType[%s]", tag, request, adId, adType));

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                vccAds.setVisibility(tag, requestId, adId, false);
            },3000);
        }
    }

    private class Adapter extends BaseAdapter {
        private final List<String> ids;

        public Adapter(List<String> ids) {
            this.ids = ids;
        }

        @Override
        public int getCount() {
            try {
                return ids.size();
            } catch (Exception e) {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return this.ids.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                Context context = parent.getContext();
                LayoutInflater inflater = LayoutInflater.from(context);
                ItemBasicBinding binding = ItemBasicBinding.inflate(inflater, parent, false);
                convertView = binding.getRoot();
            }

            String id = ids.get(position);
            ConstraintLayout replace = convertView.findViewById(R.id.replace);
            TextView title = convertView.findViewById(R.id.title);

            replace.removeAllViews();
            if (TextUtils.isEmpty(id)) {
                title.setText("");
                title.setBackgroundColor(getResources().getColor(R.color.secondaryColor, getTheme()));

                replace.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            } else {
                title.setText(id);
                title.setBackgroundColor(Color.parseColor("#88000000"));

                vccAds.adAdd(replace, TAG, requestId, id, position);
            }

            return convertView;
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
