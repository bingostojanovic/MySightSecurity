package com.justin.mysightsecurity.ui.add_device;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.justin.mysightsecurity.databinding.FragmentAdddeviceBinding;

public class AddDeviceFragment extends Fragment {

    private FragmentAdddeviceBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddDeviceViewModel notificationsViewModel =
                new ViewModelProvider(this).get(AddDeviceViewModel.class);

        binding = FragmentAdddeviceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}