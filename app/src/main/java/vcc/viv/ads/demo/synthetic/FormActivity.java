package vcc.viv.ads.demo.synthetic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import vcc.viv.ads.demo.BaseActivity;
import vcc.viv.ads.demo.DummyData;
import vcc.viv.ads.demo.R;
import vcc.viv.ads.demo.Utility;
import vcc.viv.ads.demo.databinding.ActivityFormBinding;
import vcc.viv.ads.transport.VccAds;
import vcc.viv.ads.transport.VccAdsListener;

public class FormActivity extends BaseActivity implements DummyData {
    /* **********************************************************************
     * Area : Variable - Const
     ********************************************************************** */
    protected static final String TAG = FormActivity.class.getSimpleName();
    protected static final String requestId = "1";
    private static final String[] suggestAdsId = {"13450", "13462", "5225"};

    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private ActivityFormBinding binding;
    private FormAdapter adapter;
    private TagAdapter tagAdapter;
    private BottomSheetBehavior bottomSheetBehavior;
    private VccAds vccAds;
    private Handler handler;

    /* **********************************************************************
     * Area : Starter
     ********************************************************************** */
    public static void starter(Context context) {
        Intent intent = new Intent(context, FormActivity.class);
        context.startActivity(intent);
    }

    /* **********************************************************************
     * Area : Function - Override
     ********************************************************************** */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler(Looper.getMainLooper());

        binding = ActivityFormBinding.inflate(getLayoutInflater());
        ViewGroup view = (ViewGroup) binding.getRoot();
        setContentView(view);

        vccAds = VccAds.getInstance();

        initView();
        adsRequestHandle();
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
     * Area : Function - Private
     ********************************************************************** */
    private void initView() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.bottomSheetLayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED || newState == BottomSheetBehavior.STATE_HIDDEN) {
                    binding.middleLayout.setVisibility(View.GONE);
                    closeKeyboard();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        binding.moreButton.setOnClickListener(view -> {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED || bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                binding.middleLayout.setVisibility(View.VISIBLE);
            }
        });

        binding.middleLayout.setOnClickListener(view -> {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                binding.middleLayout.setVisibility(View.GONE);
            }
        });

        adapter = new FormAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplication(), LinearLayoutManager.VERTICAL, false);
        int space = (int) Utility.dpToPx(this, 1);
        VerticalSpaceItemDecoration decoration = new VerticalSpaceItemDecoration(space);
        binding.advertising.setAdapter(adapter);
        binding.advertising.setLayoutManager(layoutManager);
        binding.advertising.addItemDecoration(decoration);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, suggestAdsId);
        binding.bottomSheet.adIdsInput.setThreshold(1);
        binding.bottomSheet.adIdsInput.setAdapter(arrayAdapter);
    }

    private void adsRequestHandle() {
        tagAdapter = new TagAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.bottomSheet.adIds.setAdapter(tagAdapter);
        binding.bottomSheet.adIds.setLayoutManager(layoutManager);

        binding.bottomSheet.adIdsInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(" ")) {
                    String adsId = s.toString().trim();
                    if (!TextUtils.isEmpty(adsId)) {
                        if (tagAdapter.isContain(adsId)) {
                            Snackbar.make(binding.root, getResources().getString(R.string.notice_duplicate_ads_id), BaseTransientBottomBar.LENGTH_SHORT).show();
                        } else {
                            tagAdapter.addAdsId(adsId);
                            binding.bottomSheet.adIds.scrollToPosition(tagAdapter.getItemCount() - 1);
                        }
                    }
                    binding.bottomSheet.adIdsInput.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.bottomSheet.adIdsInput.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getAction() == KeyEvent.KEYCODE_ENTER) {
                String adsId = binding.bottomSheet.adIdsInput.getText().toString().trim();
                if (!TextUtils.isEmpty(adsId)) {
                    if (tagAdapter.isContain(adsId)) {
                        Snackbar.make(binding.root, getResources().getString(R.string.notice_duplicate_ads_id), BaseTransientBottomBar.LENGTH_SHORT).show();
                    } else {
                        tagAdapter.addAdsId(adsId);
                        binding.bottomSheet.adIdsInput.setText("");
                    }
                } else {
                    if (tagAdapter.getItemCount() == 0) {
                        Snackbar.make(binding.root, getResources().getString(R.string.notice_ads_id_empty), BaseTransientBottomBar.LENGTH_SHORT).show();
                    } else {
                        return true;
                    }
                }
                return true;
            }
            return false;
        });

        binding.bottomSheet.load.setOnClickListener(view -> {
            if (tagAdapter.getData() == null) return;
            else if (tagAdapter.getItemCount() == 0 && binding.bottomSheet.adIdsInput.getText().toString().isEmpty()) {
                adapter.setData(tagAdapter.getData());
                Snackbar.make(binding.root, getResources().getString(R.string.notice_ads_id_empty), BaseTransientBottomBar.LENGTH_SHORT).show();
            } else {
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(() -> {
                    String adsId = binding.bottomSheet.adIdsInput.getText().toString().trim();
                    if (!TextUtils.isEmpty(adsId)) {
                        if (tagAdapter.isContain(adsId)) {
                            Snackbar.make(binding.root, getResources().getString(R.string.notice_duplicate_ads_id), BaseTransientBottomBar.LENGTH_SHORT).show();
                        } else {
                            tagAdapter.addAdsId(adsId);
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        }
                    }
                    binding.bottomSheet.adIdsInput.setText("");
                    requestAds();
                    closeKeyboard();
                }, 400);
            }
        });
    }

    private void requestAds() {
        List<String> adsIdList = tagAdapter.getData();
        adapter.setData(adsIdList);
        vccAds.onVccAdsListener(TAG, new VccAdsHandler());
        vccAds.adSetupView(TAG, binding.root, null);
        vccAds.adRequest(TAG, requestId, adsIdList);
    }

    private void closeKeyboard() {
        View currentFocusedView = this.getCurrentFocus();
        if (currentFocusedView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), 0);
        }
    }

    /* **********************************************************************
     * Area : Inner Class
     ********************************************************************** */
    private class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
        private final int verticalSpaceHeight;

        public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = verticalSpaceHeight;
        }
    }

    private class VccAdsHandler implements VccAdsListener {
        @Override
        public void initPrepare() {
        }

        @Override
        public void initSuccess() {
        }

        @Override
        public void adStorePrepared() {
        }

        @Override
        public void adRequestFail() {
            Snackbar.make(binding.root, getResources().getString(R.string.notice_ads_id_not_exist), BaseTransientBottomBar.LENGTH_SHORT).show();
        }

        @Override
        public void closeActivity() {
        }
    }
}
