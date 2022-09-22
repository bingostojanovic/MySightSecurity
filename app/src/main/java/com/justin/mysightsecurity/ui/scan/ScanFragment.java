package com.justin.mysightsecurity.ui.scan;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.justin.mysightsecurity.MainActivity;
import com.justin.mysightsecurity.MyRecyclerViewAdapter;
import com.justin.mysightsecurity.R;

import java.util.ArrayList;

public class ScanFragment extends Fragment {

    private ScanViewModel mViewModel;
    MyRecyclerViewAdapter adapter;
    ArrayList<String> mCameraImagePaths;
    public static ScanFragment newInstance() {
        return new ScanFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_scan, container, false);
        mCameraImagePaths = new ArrayList<>();
        mCameraImagePaths.add("Horse");
        mCameraImagePaths.add("Cow");
        mCameraImagePaths.add("Camel");
        mCameraImagePaths.add("Sheep");
        mCameraImagePaths.add("Goat");
        mCameraImagePaths.add("Goat");
        mCameraImagePaths.add("Goat");
        mCameraImagePaths.add("Goat");
        mCameraImagePaths.add("Goat");
        mCameraImagePaths.add("Goat");
        mCameraImagePaths.add("Goat");
        mCameraImagePaths.add("Goat");
        mCameraImagePaths.add("Goat");

        // set up the RecyclerView
        RecyclerView recyclerView = root.findViewById(R.id.rvCameras);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);

        adapter = new MyRecyclerViewAdapter(root.getContext(), mCameraImagePaths);
        adapter.setClickListener(new MyRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "You clicked " + adapter.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ScanViewModel.class);
        // TODO: Use the ViewModel
    }

}