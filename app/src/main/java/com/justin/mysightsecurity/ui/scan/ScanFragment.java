package com.justin.mysightsecurity.ui.scan;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.AppBarLayout;
import com.justin.mysightsecurity.R;

public class ScanFragment extends Fragment {

    private ScanViewModel mViewModel;

    public static ScanFragment newInstance() {
        return new ScanFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {



        return inflater.inflate(R.layout.fragment_scan, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ScanViewModel.class);
        // TODO: Use the ViewModel
    }

}