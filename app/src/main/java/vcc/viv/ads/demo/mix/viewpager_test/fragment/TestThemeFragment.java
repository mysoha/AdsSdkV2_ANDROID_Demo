package vcc.viv.ads.demo.mix.viewpager_test.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import vcc.viv.ads.demo.DummyData;
import vcc.viv.ads.demo.databinding.FragmentTestThemeBinding;

public class TestThemeFragment extends Fragment implements DummyData {

    private FragmentTestThemeBinding binding ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTestThemeBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

}