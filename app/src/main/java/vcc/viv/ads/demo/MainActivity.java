package vcc.viv.ads.demo;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;

import vcc.viv.ads.demo.basic.BasicActivity;
import vcc.viv.ads.demo.basic.ListActivity;
import vcc.viv.ads.demo.basic.RecyclerActivity;
import vcc.viv.ads.demo.basic.ScrollActivity;
import vcc.viv.ads.demo.basic.viewpager.ViewPagerActivity;
import vcc.viv.ads.demo.browser.LiveStreamActivity;
import vcc.viv.ads.demo.databinding.ActivityMainBinding;
import vcc.viv.ads.demo.fake.FakeActivity;
import vcc.viv.ads.demo.synthetic.FormActivity;
import vcc.viv.ads.transport.VccAds;
import vcc.viv.ads.transport.VccAdsListener;

public class MainActivity extends AppCompatActivity implements DummyData {
    /* **********************************************************************
     * Area : Variable - Const
     ********************************************************************** */
    private final String TAG = MainActivity.class.getSimpleName();

    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private ActivityMainBinding binding;
    private Snackbar snackbar;

    private boolean doubleBackToExitPressedOnce = false;

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
    protected void onDestroy() {
        super.onDestroy();
        if (vccAds != null) {
            vccAds.onVccAdsListener(TAG, null);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Snackbar.make(binding.getRoot(), "Click exit the app", Snackbar.LENGTH_SHORT).show();

        new Handler(getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    /* **********************************************************************
     * Area : Function
     ********************************************************************** */
    private void initSdk() {
        vccAds = VccAds.getInstance();
        vccAds.onVccAdsListener(TAG, new VccAdsHandler());
        vccAds.init(this, APP_ID, APP_NAME, APP_VERSION);
    }

    private void basicHandle(MainAdapter.Data data) {
        switch (data.info) {
            case R.string.basic_one_no_scroll:
                BasicActivity.starter(this);
                break;
            case R.string.basic_scrollview:
                ScrollActivity.starter(this);
                break;
            case R.string.basic_listview:
                ListActivity.starter(this);
                break;
            case R.string.basic_recyclerview:
                RecyclerActivity.starter(this);
                break;
            case R.string.basic_viewpager:
                ViewPagerActivity.starter(this);
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
                LiveStreamActivity.starter(this);
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
    private class VccAdsHandler implements VccAdsListener {
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
            snackbar.dismiss();
        }

        @Override
        public void adStorePrepared() {
        }

        @Override
        public void adRequestFail() {
        }

        @Override
        public void adRequestSuccess() {

        }

        @Override
        public void closeActivity(String s, String s1, String s2) {

        }
    }
}