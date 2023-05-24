package vcc.viv.ads.demo.mix.viewpager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import vcc.viv.ads.demo.BaseActivity;
import vcc.viv.ads.demo.databinding.ActivityMixViewpagerBinding;

public class ViewPagerActivity extends BaseActivity {
    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private ActivityMixViewpagerBinding binding;

    /* **********************************************************************
     * Area : Starter
     ********************************************************************** */
    public static void starter(Context context) {
        Intent intent = new Intent(context, ViewPagerActivity.class);
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
        setContentView(view);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.setSameDirectionView(binding.viewpager);
//        binding.viewpager.setOffscreenPageLimit(4);
        binding.viewpager.setAdapter(adapter);
        binding.viewpager.setPageMargin(4);
    }
}
