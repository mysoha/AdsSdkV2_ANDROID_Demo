package vcc.viv.ads.demo.mix.viewpager_test.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import vcc.viv.ads.demo.DummyData;
import vcc.viv.ads.demo.R;
import vcc.viv.ads.demo.databinding.ActivityMixScrollBinding;
import vcc.viv.ads.demo.databinding.ItemBasicBinding;
import vcc.viv.ads.demo.utility.Utility;
import vcc.viv.ads.transport.VccAds;
import vcc.viv.ads.transport.VccAdsListener;
import vcc.viv.ads.transport.ontouch.VccOnTouchHandler;
import vcc.viv.ads.transport.scroll.VccScrollHandler;

public class ViewPagerScrollViewTestFragment extends Fragment implements DummyData {
    /* **********************************************************************
     * Area : Variable - Const
     ********************************************************************** */
    private final String TAG = ViewPagerScrollViewTestFragment.class.getSimpleName();
    private final String requestId = "1";
    private String fTag = "";
    private int id = 0;
    private final List<String> adIds = AD_BANNER_IDS;
    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private ActivityMixScrollBinding binding;
    private ViewGroup sameDirectionView;
    private VccAds vccAds;

    public ViewPagerScrollViewTestFragment() {}

    public ViewPagerScrollViewTestFragment(ViewGroup sameDirectionView) {
        this.sameDirectionView = sameDirectionView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityMixScrollBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fTag = TAG + "-" + id;

        binding.title.setVisibility(View.VISIBLE);
        binding.title.setText("Scrollview");

        MyScroll scroll = new MyScroll();
        binding.scroll.setOnScrollChangeListener(scroll);

        MyTouch touch = new MyTouch();
        binding.scroll.setOnTouchListener(touch);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int) Utility.dpToPx(getActivity(), 1);
        for (int i = 0; i < adIds.size(); i++) {
            try {
                String id = adIds.get(i);
                ItemBasicBinding itemBinding = ItemBasicBinding.inflate(getLayoutInflater());
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
        vccAds.onVccAdsListener(fTag, new VccAdsHandler());
        vccAds.adSetupView(fTag, binding.root, null, touch, sameDirectionView);
        vccAds.adRequest(fTag, requestId, adIds, "1", "http://kenh14.vn/bi-mat-trong-lang-mo-tan-thuy-hoang-hoa-ra-khong-the-khai-quat-la-do-lop-tuong-dac-biet-20211113111052856.chn", "https://app.kenh14.vn/home", "0");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (vccAds != null) {
            vccAds.onVccAdsListener(fTag, null);
        }
    }

    public void createTag(int id) {
        this.id = id;
    }

    /* **********************************************************************
     * Area : Function
     ********************************************************************** */
    private void addLineDivider() {
        LinearLayout.LayoutParams viewLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Utility.dpToPx(getActivity(), 1));
        View fake = new View(getActivity());
        fake.setBackgroundColor(getResources().getColor(R.color.white, getActivity().getTheme()));
        binding.advertising.addView(fake, viewLp);
    }

    private void addFakeView() {
        LinearLayout.LayoutParams viewLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Utility.dpToPx(getActivity(), 64));
        View fake = new View(getActivity());
        fake.setBackgroundColor(getResources().getColor(R.color.secondaryColor, getActivity().getTheme()));
        binding.advertising.addView(fake, viewLp);
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
                    vccAds.adAdd(layout, fTag, requestId, id);
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
}