package vcc.viv.ads.demo.mix.viewpager;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import vcc.viv.ads.demo.mix.viewpager.fragment.EmptyFragment;
import vcc.viv.ads.demo.mix.viewpager.fragment.ScrollBannerAdsFragment;
import vcc.viv.ads.demo.mix.viewpager.fragment.ViewPagerRecyclerFragment;
import vcc.viv.ads.demo.mix.viewpager.fragment.ViewPagerScrollViewFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private ViewGroup sameDirectionView;

    /* **********************************************************************
     * Area : Constructor
     ********************************************************************** */
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
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
                ViewPagerRecyclerFragment recyclerFragment = new ViewPagerRecyclerFragment(sameDirectionView);
                recyclerFragment.createTag(position);
                return recyclerFragment;
            case 5:
                ViewPagerScrollViewFragment scrollFragment = new ViewPagerScrollViewFragment(sameDirectionView);
                scrollFragment.createTag(position);
                return scrollFragment;
            case 9:
                ScrollBannerAdsFragment scrollBannerAds = new ScrollBannerAdsFragment();
                scrollBannerAds.createTag(position);
                return scrollBannerAds;
            default:
                return new EmptyFragment();
        }
    }

    @Override
    public int getCount() {
        return 10;
    }
}