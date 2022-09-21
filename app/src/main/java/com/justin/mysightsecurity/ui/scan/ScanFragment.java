package com.justin.mysightsecurity.ui.scan;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.justin.mysightsecurity.CameraViewAdapter;
import com.justin.mysightsecurity.R;

import java.util.ArrayList;

public class ScanFragment extends Fragment {

    private ScanViewModel mViewModel;
    private SQLiteDatabase db;
    private CameraViewAdapter cameraViewAdapter;
    ArrayList<String> imagePaths;

    public static ScanFragment newInstance() {
        return new ScanFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        try {
            db= getActivity().openOrCreateDatabase("sight.db", Context.MODE_PRIVATE,null);
        }catch (Exception e) {
            Toast.makeText(getActivity(), "Can not access database: "+ e.toString(), Toast.LENGTH_SHORT).show();
            db.close();
            return inflater.inflate(R.layout.fragment_scan, container, false);
        }
        Cursor c = db.rawQuery("SELECT * FROM Device", null);
        c.moveToFirst();

        imagePaths = new ArrayList<>();
        do {
            imagePaths.add(c.getString(5));
        } while (c.moveToNext());
        c.close();
        db.close();

        RecyclerView cameraView = getActivity().findViewById(R.id.camera_views);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        cameraView.setLayoutManager(horizontalLayoutManager);
        cameraViewAdapter = new CameraViewAdapter(getActivity(), imagePaths);
        cameraViewAdapter.setClickListener(new CameraViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "You clicked " + cameraViewAdapter.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();

            }
        });
        cameraView.setAdapter(cameraViewAdapter);


        return inflater.inflate(R.layout.fragment_scan, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ScanViewModel.class);
        // TODO: Use the ViewModel
    }

}