package vcc.viv.ads.demo.mix.viewpager_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import vcc.viv.ads.demo.BaseActivity;
import vcc.viv.ads.demo.R;
import vcc.viv.ads.demo.databinding.ActivityMixViewpagerBinding;

public class ViewPagerTestActivity extends BaseActivity {
    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private ActivityMixViewpagerBinding binding;

    private Boolean nightMode = false;

    public static String MODE_FILE_NAME = "Mode";
    public static String MODE_KEY = "night";


    /* **********************************************************************
     * Area : Starter
     ********************************************************************** */
    public static void starter(Context context) {
        Intent intent = new Intent(context, ViewPagerTestActivity.class);
        context.startActivity(intent);
    }

    /* **********************************************************************
     * Area : Function - Override
     ********************************************************************** */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMixViewpagerBinding.inflate(getLayoutInflater());
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

        ViewPagerTestAdapter adapter = new ViewPagerTestAdapter(getSupportFragmentManager());
        adapter.setSameDirectionView(binding.viewpager);
//        binding.viewpager.setOffscreenPageLimit(4);
        binding.viewpager.setAdapter(adapter);
        binding.viewpager.setPageMargin(4);
        Button changeTheme = binding.changeTheme;
        boolean finalNightMode = nightMode;
        changeTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set the color to relative layout
                if(modePreferences != null){
                    if (finalNightMode) {
                        modePreferences.edit().putBoolean(MODE_KEY, false).apply();
                    } else {
                        modePreferences.edit().putBoolean(MODE_KEY, true).apply();
                    }
                }
                recreate();
                overridePendingTransition(0,0);
            }
        });
    }
}
