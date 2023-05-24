package vcc.viv.ads.demo.mix.viewpager.fragment;

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

//        int position = id % AD_BANNER_IDS.size();
//        String adId = AD_BANNER_IDS.get(position);
        String adId = AD_IN_PAGE_ID;
        adIds.add(adId);
        binding.title.setText(adId);

        String tag = TAG + "_" + id;
        vccAds = VccAds.getInstance();
        vccAds.adSetupView(tag, binding.root, null);
        vccAds.setDeviceId("99e11d3dcbcbce7d");
        vccAds.onVccAdsListener(tag, new VccAdsHandler());
        List<String> urls = new ArrayList<>();
        urls.add("https://autopro.com.vn/toi-mang-mitsubishi-xpander-cross-len-deo-nui-de-tra-loi-thac-mac-cua-doc-gia-xe-yeu-mam-lop-nho-va-thua-cong-nghe-177230406063817765.chn#utm_source=autopro&utm_campaign=autopro&utm_medium=autopro&pgclid=ehzA7/3lbDSqeTVijpkFEkF6yL375js3+iphMdrJWBQSEs+/");
        urls.add("https://autopro.com.vn/honda-cr-v-2023-lan-dau-lo-dien-tai-viet-nam-du-kien-ra-mat-cuoi-nam-nay-bom-tan-dau-tucson-va-cx-5-177230405200833486.chn#utm_source=autopro&utm_campaign=autopro&utm_medium=autopro&pgclid=ehzA7_3lbDSqeTVijpkFEkF6yL375js3-ipiM9zKWBYWEcG8");
        urls.add("https://autopro.com.vn/boc-duoc-bien-tu-quy-8-chu-vinfast-lux-a20-de-phu-bui-gan-1-nam-khong-di-roi-ban-gia-12-ty-dong-ngang-camry-moi-177230406201442086.chn#utm_source=autopro&utm_campaign=autopro&utm_medium=autopro&pgclid=ehzA7_3lbDSqeTVijpkFEkF6yL375js3-iphM9zLVBEXFcG8");
        urls.add("https://autopro.com.vn/dam-cuoi-trieu-do-o-an-giang-hoi-mon-23-ty-tien-mat-tang-nha-70-ty-nhung-mon-qua-cuoi-cung-moi-gia-tri-nhat-177230406094831696.chn#utm_source=autopro&utm_campaign=autopro&utm_medium=autopro&pgclid=ehzA7_3lbDSqeTVijpkFEkF6yL375js3-iphMdXOWBYUE8C8");

        int index = id % urls.size();
        String url = urls.get(index);
        vccAds.adRequest(tag, requestId, adIds,"1",url,url,"0");
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
        public void adRequestFail(String tag, String request, String adId, String msg) {
            Log.d(TAG, String.format("AD REQUEST - Fail : tag[%s] - requestId[%s] - adId[%s] - msg[%s]", tag, request, adId, msg));
        }

        @Override
        public void adRequestSuccess(String tag, String request, String adId, String adType) {
            Log.d(TAG, String.format("AD REQUEST - Success : requestId[%s] - adId[%s] - adType[%s]", tag, request, adId, adType));
        }
    }
}
