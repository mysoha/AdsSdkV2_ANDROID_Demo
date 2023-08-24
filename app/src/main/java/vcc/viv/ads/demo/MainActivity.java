package vcc.viv.ads.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import vcc.viv.ads.demo.basic.BasicActivity;
import vcc.viv.ads.demo.databinding.ActivityMainBinding;
import vcc.viv.ads.demo.fake.FakeActivity;
import vcc.viv.ads.demo.mix.ListActivity;
import vcc.viv.ads.demo.mix.RecyclerActivity;
import vcc.viv.ads.demo.mix.RecyclerHorizontalActivity;
import vcc.viv.ads.demo.mix.ScrollActivity;
import vcc.viv.ads.demo.mix.VisibilityActivity;
import vcc.viv.ads.demo.mix.viewpager.ViewPagerActivity;
import vcc.viv.ads.demo.mix.viewpager_test.ViewPagerTestActivity;
import vcc.viv.ads.demo.synthetic.FormActivity;
import vcc.viv.ads.transport.VccAds;
import vcc.viv.ads.transport.VccAdsListener;
import vcc.viv.ads.transport.lifecycle.VccLifeCycleObserver;

public class MainActivity extends BaseActivity implements DummyData {
    /* **********************************************************************
     * Area : Variable - Const
     ********************************************************************** */
    private final String TAG = MainActivity.class.getSimpleName();

    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private boolean doubleBackToExitPressedOnce = false;

    private ActivityMainBinding binding;
    private Snackbar snackbar;
    public static String MODE_FILE_NAME = "Mode";
    public static String MODE_KEY = "night";

    private VccAds vccAds;
    private Handler handler;

    /* **********************************************************************
     * Area : Function - Override
     ********************************************************************** */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        ViewGroup view = (ViewGroup) binding.getRoot();
        SharedPreferences modePreferences = getSharedPreferences(MODE_FILE_NAME, Context.MODE_PRIVATE);
        boolean nightMode = false;
        if(modePreferences != null) {
            nightMode = modePreferences.getBoolean(MODE_KEY, false);
        }
        if (!nightMode) {
            setTheme(R.style.Theme_LightMode);
        } else {
            setTheme(R.style.Theme_DarkMode);
        }
        setContentView(view);

        handler = new Handler(getMainLooper());
        snackbar = Snackbar.make(binding.getRoot(), "", Snackbar.LENGTH_INDEFINITE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        MainAdapter adapter = new MainAdapter((position, data) -> {
            if (data == null) return;
            else if (data.type == null) return;
            else if (data.viewType == null) return;

            switch (data.type) {
                case BASIC:
                    basicHandle(data);
                    break;
                case MIX:
                    basicMix(data);
                    break;
                case SYNTHETIC:
                    syntheticHandle(data);
                    break;
                case CUSTOM_BROWSER:
                    browserHandle(data);
                case ANDROID:
                    androidHandle(data);
                    break;
                case WEB_FORMAT:
                    webFormatHandle(data);
                    break;
                case NONE:
                default:
                    Log.d(TAG, "nothing to do");
                    break;
            }
        });
        binding.actions.setAdapter(adapter);
        binding.actions.setLayoutManager(layoutManager);

        initSdk();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Snackbar.make(binding.getRoot(), "click to back", Snackbar.LENGTH_SHORT).show();
        handler.postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (vccAds != null) {
            vccAds.onVccAdsListener(TAG, null);
            vccAds.destroy();
        }
    }

    /* **********************************************************************
     * Area : Function
     ********************************************************************** */
    private void initSdk() {
        VccLifeCycleObserver observer = ((MyApplication) getApplication()).getObserver();

        vccAds = VccAds.getInstance();
        vccAds.setLifeCycleObserver(observer);
        vccAds.onVccAdsListener(TAG, new VccAdsHandler());
        vccAds.init(this, APP_ID, APP_NAME, APP_VERSION);
    }

    private void basicHandle(MainAdapter.Data data) {
        switch (data.info) {
            case R.string.basic_banner:
                if (!BuildConfig.BANNER) {
                    Snackbar.make(binding.getRoot(), "format not supported", Snackbar.LENGTH_SHORT).show();
                }
                BasicActivity.starter(this, DummyData.AD_BANNER_ID);
                break;
            case R.string.basic_popup:
                if (!BuildConfig.POPUP) {
                    Snackbar.make(binding.getRoot(), "format not supported", Snackbar.LENGTH_SHORT).show();
                    break;
                }
                BasicActivity.starter(this, DummyData.AD_POPUP_ID);
                break;
            case R.string.basic_in_page_default:
                if (!BuildConfig.INPAGE) {
                    Snackbar.make(binding.getRoot(), "format not supported", Snackbar.LENGTH_SHORT).show();
                    break;
                }
                BasicActivity.starter(this, DummyData.AD_IN_PAGE_ID);
                break;
            case R.string.basic_in_page_non:
                if (!BuildConfig.INPAGE) {
                    Snackbar.make(binding.getRoot(), "format not supported", Snackbar.LENGTH_SHORT).show();
                    break;
                }
                BasicActivity.starter(this, DummyData.AD_NON_IN_PAGE_ID);
                break;
            case R.string.basic_catfish:
                if (!BuildConfig.CATFISH) {
                    Snackbar.make(binding.getRoot(), "format not supported", Snackbar.LENGTH_SHORT).show();
                    break;
                }
                BasicActivity.starter(this, DummyData.AD_CATFISH_ID);
                break;
            case R.string.basic_welcome:
                if (!BuildConfig.WELCOME) {
                    Snackbar.make(binding.getRoot(), "format not supported", Snackbar.LENGTH_SHORT).show();
                    break;
                }
                BasicActivity.starter(this, DummyData.AD_WELCOME_ID);
                break;
            case R.string.basic_native_home:
                BasicActivity.starter(this, DummyData.AD_NATIVE_HOME_ID);
                break;
            case R.string.basic_native_detail:
                BasicActivity.starter(this, DummyData.AD_NATIVE_DETAIL_ID);
            default:
                Log.d(TAG, "basicHandle invalid type");
                break;
        }
    }

    private void basicMix(MainAdapter.Data data) {
        switch (data.info) {
            case R.string.mix_scrollview:
                ScrollActivity.starter(this);
                break;
            case R.string.mix_listview:
                ListActivity.starter(this);
                break;
            case R.string.mix_visibility:
                VisibilityActivity.starter(this);
                break;
            case R.string.mix_recyclerview:
                RecyclerActivity.starter(this);
                break;
            case R.string.mix_recyclerviewhorizontal:
                RecyclerHorizontalActivity.starter(this);
                break;
            case R.string.mix_view_pager:
                ViewPagerActivity.starter(this);
                break;
            case R.string.mix_view_pager_test:
                ViewPagerTestActivity.starter(this);
                break;
            default:
                Log.d(TAG, "basicHandle invalid type");
                break;
        }
    }

    private void browserHandle(MainAdapter.Data data) {
        switch (data.info) {
            case R.string.browser_form:
            case R.string.browser_game:
            case R.string.browser_ecommerce:
                Snackbar.make(binding.getRoot(), "format not supported", Snackbar.LENGTH_SHORT).show();
                return;
            case R.string.browser_livestream:
                Snackbar.make(binding.getRoot(), "format not supported", Snackbar.LENGTH_SHORT).show();
//                LiveStreamActivity.starter(this);
                break;
            default:
                Log.d(TAG, "basicHandle invalid type");
                break;
        }
    }

    private void syntheticHandle(MainAdapter.Data data) {
        switch (data.info) {
            case R.string.synthetic_form:
                FormActivity.starter(this);
                break;
            default:
                Log.d(TAG, "basicHandle invalid type");
                break;
        }
    }

    private void androidHandle(MainAdapter.Data data) {
    }

    private void webFormatHandle(MainAdapter.Data data) {
        String format = data.extra;
        if (TextUtils.isEmpty(format)) {
            Snackbar.make(binding.getRoot(), "format not supported", Snackbar.LENGTH_SHORT).show();
            return;
        }
        FakeActivity.starter(this, format);
    }

    /* **********************************************************************
     * Area : Inner Class
     ********************************************************************** */
    private class VccAdsHandler extends VccAdsListener {
        @Override
        public void initPrepare() {
            Log.i(TAG, "init prepare");
            snackbar.setText("Initializing ... ");
            snackbar.show();
        }

        @Override
        public void initSuccess() {
            Log.i(TAG, "init success");
            vccAds.setDeviceId(DEVICE_ID);
            vccAds.initWelcome(R.drawable.ic_logo, R.drawable.ic_title_logo,R.color.purple_200,R.color.blue_200,true, 3000, 1000, 100);
            snackbar.dismiss();
        }

        @Override
        public void adStorePrepared() {
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