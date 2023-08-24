package vcc.viv.ads.demo.mix.viewpager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vcc.viv.ads.demo.databinding.ItemMixViewpagerBinding;

public class EmptyFragment extends Fragment {
    /* **********************************************************************
     * Area : Variable - Const
     ********************************************************************** */
    private final String TAG = EmptyFragment.class.getSimpleName();

    /* **********************************************************************
     * Area : Variable
     ********************************************************************** */
    private ItemMixViewpagerBinding binding;

    /* **********************************************************************
     * Area : Function Override
     ********************************************************************** */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ItemMixViewpagerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
