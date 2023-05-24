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
import android.view.MotionEvent;
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
import vcc.viv.ads.demo.databinding.ItemAdBinding;
import vcc.viv.ads.demo.databinding.ItemBasicBinding;
import vcc.viv.ads.transport.VccAds;
import vcc.viv.ads.transport.VccAdsListener;
import vcc.viv.ads.transport.ontouch.VccOnTouchHandler;

public class ViewPagerRecyclerFragment extends Fragment implements DummyData {
    /* **********************************************************************
     * Area : Variable - Const
     ********************************************************************** */
    private final String TAG = ViewPagerRecyclerFragment.class.getSimpleName();

    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private ActivityMixRecyclerBinding binding;
    private final ViewGroup sameDirectionView;
    private int id = 0;
    private final String requestId = "1";
    private String fTag = "";
    private VccAds vccAds;
    private final List<String> adIds = AD_BANNER_IDS;

    /* **********************************************************************
     * Area : Constructor
     ********************************************************************** */
    public ViewPagerRecyclerFragment(ViewGroup sameDirectionView) {
        this.sameDirectionView = sameDirectionView;
    }

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
        binding.title.setVisibility(View.VISIBLE);
        binding.title.setText("RecyclerView");
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

        MyTouch touch = new MyTouch();
        binding.advertising.setOnTouchListener(touch);

        vccAds = VccAds.getInstance();
        vccAds.onVccAdsListener(fTag, new VccAdsHandler());
        vccAds.adSetupView(fTag, binding.root, null, touch, sameDirectionView);
        vccAds.adRequest(fTag, requestId, adIds, "1", "http://kenh14.vn/bi-mat-trong-lang-mo-tan-thuy-hoang-hoa-ra-khong-the-khai-quat-la-do-lop-tuong-dac-biet-20211113111052856.chn", "https://app.kenh14.vn/home", "0");
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
    private class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            if (viewType == ViewType.ADS.ordinal()) {
                return new AdViewHolder(ItemAdBinding.inflate(inflater, parent, false));
            }
            return new NormalViewHolder(ItemBasicBinding.inflate(inflater, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            String id = ids.get(position);
            if (TextUtils.isEmpty(id)) {
                NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
                normalViewHolder.binding.replace.removeAllViews();
                normalViewHolder.binding.title.setVisibility(View.VISIBLE);
                normalViewHolder.binding.title.setText("");
                normalViewHolder.binding.title.setBackgroundColor(getResources().getColor(R.color.secondaryColor, getActivity().getTheme()));
                normalViewHolder.binding.replace.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            } else {
                AdViewHolder adViewHolder = (AdViewHolder) holder;
                adViewHolder.binding.replace.removeAllViews();
                adViewHolder.binding.title.setVisibility(View.VISIBLE);
                adViewHolder.binding.title.setText(id);
                adViewHolder.binding.title.setBackgroundColor(Color.parseColor("#88000000"));
                vccAds.adAdd(adViewHolder.binding.replace, fTag, requestId, id, position);
            }
        }

        @Override
        public int getItemViewType(int position) {
            String id = ids.get(position);
            if (TextUtils.isEmpty(id)) {
                return ViewType.NORMAL.ordinal();
            } else {
                return ViewType.ADS.ordinal();
            }
        }

        public class NormalViewHolder extends RecyclerView.ViewHolder {
            private final ItemBasicBinding binding;

            public NormalViewHolder(ItemBasicBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }

        public class AdViewHolder extends RecyclerView.ViewHolder {
            private final ItemAdBinding binding;

            public AdViewHolder(ItemAdBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }

    enum ViewType {
        NORMAL,
        ADS
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

    private class MyTouch extends VccOnTouchHandler {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return super.onTouch(v, event);
        }
    }
}