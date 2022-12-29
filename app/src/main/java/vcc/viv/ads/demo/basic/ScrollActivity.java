package vcc.viv.ads.demo.basic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import vcc.viv.ads.demo.BaseActivity;
import vcc.viv.ads.demo.DummyData;
import vcc.viv.ads.demo.R;
import vcc.viv.ads.demo.Utility;
import vcc.viv.ads.demo.databinding.ActivityBasicScrollBinding;
import vcc.viv.ads.demo.databinding.ItemBasicBinding;
import vcc.viv.ads.transport.VccAds;
import vcc.viv.ads.transport.VccAdsListener;
import vcc.viv.ads.transport.scroll.VccScrollHandler;

public class ScrollActivity extends BaseActivity implements DummyData {
    /* **********************************************************************
     * Area : Variable - Const
     ********************************************************************** */
    private final String TAG = ScrollActivity.class.getSimpleName();

    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private ActivityBasicScrollBinding binding;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = getLayoutInflater();
        binding = ActivityBasicScrollBinding.inflate(inflater);
        ViewGroup view = (ViewGroup) binding.getRoot();
        setContentView(view);

        MyScroll scroll = new MyScroll();
        binding.scroll.setOnScrollChangeListener(scroll);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int) Utility.dpToPx(this, 1);
        for (int i = 0; i < adIds.size(); i++) {
            try {
                String id = adIds.get(i);
                ItemBasicBinding itemBinding = ItemBasicBinding.inflate(inflater);
                itemBinding.replace.setId(Integer.parseInt(id));
                itemBinding.title.setText(id);
                binding.advertising.addView(itemBinding.getRoot(), layoutParams);

                for (int j = 0; j < 7; j++) {
                    addFakeView();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        vccAds = VccAds.getInstance();
        vccAds.onVccAdsListener(TAG, new VccAdsHandler());
        vccAds.adSetupView(TAG, view, scroll);
        vccAds.adRequest(TAG, requestId, adIds);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (vccAds != null) {
            vccAds.onVccAdsListener(TAG, null);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Snackbar.make(binding.getRoot(), R.string.back_press, Snackbar.LENGTH_SHORT).show();
    }

    /* **********************************************************************
     * Area : Function
     ********************************************************************** */
    private void addFakeView() {
        LinearLayout.LayoutParams viewLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) Utility.dpToPx(this, 64));
        viewLp.topMargin = (int) Utility.dpToPx(this, 1);
        View fake = new View(this);
        fake.setBackgroundColor(getResources().getColor(R.color.secondaryColor));
        binding.advertising.addView(fake, viewLp);
    }

    /* **********************************************************************
     * Area : Inner Class
     ********************************************************************** */
    private class VccAdsHandler implements VccAdsListener {
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
        public void adRequestFail() {
        }

        @Override
        public void closeActivity() {
        }
    }

    private class MyScroll extends VccScrollHandler {
        @Override
        public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            super.onScrollChange(v, scrollX, scrollY, oldScrollX, oldScrollY);
        }
    }
}
