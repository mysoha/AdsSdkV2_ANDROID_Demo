package vcc.viv.ads.demo.mix.viewpager_test;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import vcc.viv.ads.demo.mix.viewpager.fragment.EmptyFragment;
import vcc.viv.ads.demo.mix.viewpager.fragment.ScrollBannerAdsFragment;
import vcc.viv.ads.demo.mix.viewpager_test.fragment.TestThemeFragment;
import vcc.viv.ads.demo.mix.viewpager_test.fragment.Theme1Fragment;
import vcc.viv.ads.demo.mix.viewpager_test.fragment.Theme2Fragment;

public class ViewPagerTestAdapter extends FragmentPagerAdapter {
    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private ViewGroup sameDirectionView;

    /* **********************************************************************
     * Area : Constructor
     ********************************************************************** */
    public ViewPagerTestAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public ViewPagerTestAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    /* **********************************************************************
     * Area : Function - Setter
     ********************************************************************** */
    public void setSameDirectionView(ViewGroup view) {
        this.sameDirectionView = view;
    }

    /* **********************************************************************
     * Area : Function - Override
     ********************************************************************** */
    @NonNull
    @Override
    public Fragment getItem(int position) {
//        ViewPagerFragment fragment = new ViewPagerFragment();
//        fragment.createTag(position);
//        return fragment;

        switch (position) {
            case 1:
                return new Theme1Fragment();
            case 2:
                return new TestThemeFragment();
            case 3:
                return new Theme2Fragment();
            case 4:
                ScrollBannerAdsFragment scrollBannerAds = new ScrollBannerAdsFragment();
                scrollBannerAds.createTag(position);
                return scrollBannerAds;
            default:
                return new EmptyFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}