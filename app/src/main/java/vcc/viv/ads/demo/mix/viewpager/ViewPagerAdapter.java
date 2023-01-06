package vcc.viv.ads.demo.mix.viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
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
     * Area : Function - Override
     ********************************************************************** */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.createTag(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
