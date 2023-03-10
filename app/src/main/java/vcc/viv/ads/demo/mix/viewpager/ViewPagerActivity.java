package vcc.viv.ads.demo.mix.viewpager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import vcc.viv.ads.demo.databinding.ActivityMixViewpagerBinding;

public class ViewPagerActivity extends AppCompatActivity {
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
        binding.viewpager.setOffscreenPageLimit(4);
        binding.viewpager.setAdapter(adapter);
    }
}
