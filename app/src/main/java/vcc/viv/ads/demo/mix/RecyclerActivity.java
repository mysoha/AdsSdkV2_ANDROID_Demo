package vcc.viv.ads.demo.mix;

import android.content.Context;
import android.content.Intent;
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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vcc.viv.ads.demo.BaseActivity;
import vcc.viv.ads.demo.DummyData;
import vcc.viv.ads.demo.R;
import vcc.viv.ads.demo.databinding.ActivityMixRecyclerBinding;
import vcc.viv.ads.demo.databinding.ItemBasicBinding;
import vcc.viv.ads.transport.VccAds;
import vcc.viv.ads.transport.VccAdsListener;
import vcc.viv.ads.transport.scroll.VccScrollHandler;

public class RecyclerActivity extends BaseActivity implements DummyData {
    /* **********************************************************************
     * Area : Variable - Const
     ********************************************************************** */
    private final String TAG = RecyclerActivity.class.getSimpleName();

    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private ActivityMixRecyclerBinding binding;

    private VccAds vccAds;
    private final String requestId = "1";
    private final List<String> adIds = AD_BANNER_IDS;
    private boolean isPrepared = false;

    /* **********************************************************************
     * Area : Starter
     ********************************************************************** */
    public static void starter(Context context) {
        Intent intent = new Intent(context, RecyclerActivity.class);
        context.startActivity(intent);
    }

    /* **********************************************************************
     * Area : Function - Override
     ********************************************************************** */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = getLayoutInflater();
        binding = ActivityMixRecyclerBinding.inflate(inflater);
        ViewGroup view = (ViewGroup) binding.getRoot();
        setContentView(view);

        List<String> dummyData = new ArrayList<>();
        for (int i = 0; i < adIds.size(); i++) {
            dummyData.add(adIds.get(i));
            for (int j = 0; j < FAKE_ITEM_VIEW_COUNT; j++) {
                dummyData.add(null);
            }
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        VerticalSpaceItemDecoration divider = new VerticalSpaceItemDecoration();
        Adapter adapter = new Adapter(dummyData);
        binding.advertising.setLayoutManager(layoutManager);
        binding.advertising.setAdapter(adapter);
        binding.advertising.addItemDecoration(divider);
        MyScroll scroll = new MyScroll();
        binding.advertising.setOnScrollChangeListener(scroll);

        vccAds = VccAds.getInstance();
        vccAds.onVccAdsListener(TAG, new VccAdsHandler());
        vccAds.adSetupView(TAG, binding.root, scroll);
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
            isPrepared = true;
        }

        @Override
        public void adRequestFail(String tag, String request, String adId) {
            Log.d(TAG, String.format("AD REQUEST - Fail : tag[%s] - requestId[%s] - adId[%s]", tag, request, adId));
        }

        @Override
        public void adRequestSuccess(String tag, String request, String adId, String adType) {
            Log.d(TAG, String.format("AD REQUEST - Success : tag[%s] - requestId[%s] - adId[%s] - adType[%s]", tag, request, adId, adType));
        }
    }

    private class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
        private final Drawable divider;

        public VerticalSpaceItemDecoration() {
            divider = ContextCompat.getDrawable(RecyclerActivity.this, R.drawable.line_divider);
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
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            ItemBasicBinding binding = ItemBasicBinding.inflate(inflater, parent, false);
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String id = ids.get(position);
            holder.binding.replace.removeAllViews();
            if (TextUtils.isEmpty(id)) {
                holder.binding.title.setVisibility(View.VISIBLE);
                holder.binding.title.setText("");

                holder.binding.title.setBackgroundColor(getResources().getColor(R.color.secondaryColor, getTheme()));
                holder.binding.replace.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            } else {
                holder.binding.title.setVisibility(View.VISIBLE);
                holder.binding.title.setText(id);

                holder.binding.title.setBackgroundColor(Color.parseColor("#88000000"));
                vccAds.adAdd(holder.binding.replace, TAG, requestId, id, position);
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

    private static class MyScroll extends VccScrollHandler {
        @Override
        public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            super.onScrollChange(v, scrollX, scrollY, oldScrollX, oldScrollY);
        }
    }
}