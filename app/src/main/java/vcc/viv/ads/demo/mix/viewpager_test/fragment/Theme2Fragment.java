package vcc.viv.ads.demo.mix.viewpager_test.fragment;

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
import vcc.viv.ads.demo.databinding.ActivityBasicBinding;
import vcc.viv.ads.transport.VccAds;
import vcc.viv.ads.transport.VccAdsListener;
import vcc.viv.ads.transport.ontouch.VccOnTouchHandler;

public class Theme2Fragment extends Fragment implements DummyData {
    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private ActivityBasicBinding binding ;
    private final String TAG = Theme2Fragment.class.getSimpleName();
    private static final String KEY_ID = "key:ad_id";
    private VccAds vccAds;
    private final String requestId = "1";
    private final List<String> adIds = new ArrayList<String>() {{
        add(AD_BANNER_ID);
    }};

    private String urlSurveyAd = "https://soha.vn/tan-thuy-hoang-ban-chet-sinh-vat-bi-an-2000-nam-van-khong-ai-biet-cau-tra-loi-duoc-tiet-lo-trong-lang-can-long-20210905084934714.htm";
    private String urlStreamAd = "https://soha.vn/tan-thuy-hoang-chet-nhu-the-nao-he-lo-3-nguyen-nhan-khong-chi-do-thuoc-truong-sinh-20211107234501608.htm";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = ActivityBasicBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vccAds = VccAds.getInstance();
        vccAds.onVccAdsListener(TAG, new VccAdsHandler());
        vccAds.adSetupView(TAG,(ViewGroup) binding.getRoot(), null, new VccOnTouchHandler());
        vccAds.adRequest(TAG, requestId, adIds);
    }

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
