package vcc.viv.ads.demo.mix.viewpager.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.demo.DummyData;
import vcc.viv.ads.demo.R;
import vcc.viv.ads.demo.databinding.ActivityMixRecyclerBinding;
import vcc.viv.ads.demo.databinding.ItemBasicBinding;
import vcc.viv.ads.transport.VccAds;
import vcc.viv.ads.transport.VccAdsListener;

public class ScrollBannerAdsFragment extends Fragment implements DummyData {
    /* **********************************************************************
     * Area : Variable - Const
     ********************************************************************** */
    private final String TAG = ScrollBannerAdsFragment.class.getSimpleName();

    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private ActivityMixRecyclerBinding binding;
    private int id = 0;
    private final String requestId = "1";
    private String fTag = "";
    private VccAds vccAds;
    private final List<String> adIds = AD_SCROLLABLE_IDS;

    private String urlSurveyAd = "https://soha.vn/tan-thuy-hoang-ban-chet-sinh-vat-bi-an-2000-nam-van-khong-ai-biet-cau-tra-loi-duoc-tiet-lo-trong-lang-can-long-20210905084934714.htm";
    private String urlStreamAd = "https://soha.vn/tan-thuy-hoang-chet-nhu-the-nao-he-lo-3-nguyen-nhan-khong-chi-do-thuoc-truong-sinh-20211107234501608.htm";

    /* **********************************************************************
     * Area : Function - Override
     ********************************************************************** */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityMixRecyclerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fTag = TAG + "-" + id;

        List<String> dummyData = new ArrayList<>();
        for (int i = 0; i < adIds.size(); i++) {
            dummyData.add(adIds.get(i));
            for (int j = 0; j < FAKE_ITEM_VIEW_COUNT; j++) {
                dummyData.add(null);
            }
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        VerticalSpaceItemDecoration divider = new VerticalSpaceItemDecoration();
        Adapter adapter = new Adapter(dummyData);
        binding.advertising.setLayoutManager(layoutManager);
        binding.advertising.setAdapter(adapter);
        binding.advertising.addItemDecoration(divider);

        vccAds = VccAds.getInstance();
        vccAds.onVccAdsListener(fTag, new VccAdsHandler());
        vccAds.adSetupView(fTag, binding.root, null, null);
        vccAds.adRequest(fTag, requestId, adIds, "1", urlSurveyAd, urlSurveyAd, "0");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (vccAds != null) {
            vccAds.onVccAdsListener(fTag, null);
        }
    }

    public void createTag(int id) {
        this.id = id;
    }

    /* **********************************************************************
     * Area : Inner class
     ********************************************************************** */
    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private final List<String> ids;

        public Adapter(List<String> ids) {
            this.ids = ids;
        }

        @Override
        public int getItemCount() {
            try {
                return ids.size();
            } catch (Exception e) {
                return 0;
            }
        }

        @NonNull
        @Override
        public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            ItemBasicBinding binding = ItemBasicBinding.inflate(inflater, parent, false);
            return new Adapter.ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
            String id = ids.get(position);
            holder.binding.replace.removeAllViews();
            if (TextUtils.isEmpty(id)) {
                holder.binding.title.setVisibility(View.VISIBLE);
                holder.binding.title.setText("");
                holder.binding.title.setBackgroundColor(getResources().getColor(R.color.secondaryColor, getActivity().getTheme()));
                holder.binding.replace.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            } else {
                holder.binding.title.setVisibility(View.VISIBLE);
                holder.binding.title.setText(id);
                holder.binding.title.setBackgroundColor(Color.parseColor("#88000000"));
                vccAds.adAdd(holder.binding.replace, fTag, requestId, id, position);
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final ItemBasicBinding binding;

            public ViewHolder(ItemBasicBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }

    private class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
        private final Drawable divider;

        public VerticalSpaceItemDecoration() {
            divider = ContextCompat.getDrawable(getContext(), R.drawable.line_divider);
        }

        @Override
        public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + divider.getIntrinsicHeight();

                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            }
        }
    }

    private class VccAdsHandler extends VccAdsListener {

        @Override
        public void initSuccess() {
        }

        @Override
        public void adStorePrepared() {
        }

        @Override
        public void adRequestFail(String tag, String requestId, String adId, String msg) {
            Log.d(fTag, String.format("AD REQUEST - Fail : tag[%s] - requestId[%s] - adId[%s] - msg[%s]", tag, requestId, adId, msg));
        }

        @Override
        public void adRequestSuccess(String tag, String requestId, String adId, String adType) {
            Log.d(fTag, String.format("AD REQUEST - Success : tag[%s] - requestId[%s] - adId[%s] - adType[%s]", tag, requestId, adId, adType));
        }
    }
}