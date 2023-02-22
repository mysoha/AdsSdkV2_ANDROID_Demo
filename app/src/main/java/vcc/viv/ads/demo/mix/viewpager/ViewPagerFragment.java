package vcc.viv.ads.demo.mix.viewpager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.demo.DummyData;
import vcc.viv.ads.demo.databinding.ItemMixViewpagerBinding;
import vcc.viv.ads.transport.VccAds;
import vcc.viv.ads.transport.VccAdsListener;

public class ViewPagerFragment extends Fragment implements DummyData {
    /* **********************************************************************
     * Area : Variable - Const
     ********************************************************************** */
    private final String TAG = ViewPagerFragment.class.getSimpleName();

    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private ItemMixViewpagerBinding binding;
    private int id = 0;
    private String requestId = "1";
    private VccAds vccAds;
    private final List<String> adIds = new ArrayList<String>() {{
    }};

    /* **********************************************************************
     * Area : Function Override
     ********************************************************************** */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ItemMixViewpagerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int position = id % AD_BANNER_IDS.size();
        String adId = AD_BANNER_IDS.get(position);
        adIds.add(adId);
        binding.title.setText(adId);

        String tag = TAG + "_" + id;
        vccAds = VccAds.getInstance();
        vccAds.adSetupView(tag, binding.root, null);
        vccAds.onVccAdsListener(tag, new VccAdsHandler());
//        vccAds.setExtraInfo("0", "1", "https://kenh14.vn/bi-mat-trong-lang-mo-tan-thuy-hoang-hoa-ra-khong-the-khai-quat-la-do-lop-tuong-dac-biet-20211113111052856.chn", "https://app.kenh14.vn/home");
        vccAds.adRequest(tag, requestId, adIds);
    }

    public void createTag(int id) {
        this.id = id;
    }

    /* **********************************************************************
     * Area : Inner class
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
            String tag = TAG + "_" + id;
            vccAds.adAdd(binding.root, tag, requestId, adIds.get(0));
        }

        @Override
        public void adRequestFail(String tag, String request, String adId) {
            Log.d(TAG, String.format("AD REQUEST - Fail : tag[%s] - requestId[%s] - adId[%s]", tag, request, adId));
        }

        @Override
        public void adRequestSuccess(String tag, String request, String adId, String adType) {
            Log.d(TAG, String.format("AD REQUEST - Success : requestId[%s] - adId[%s] - adType[%s]", tag, request, adId, adType));
        }
    }
}
