package com.justin.mysightsecurity.ui.add_device;

import static android.content.Context.WIFI_SERVICE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.justin.mysightsecurity.GalleryFragment;
import com.justin.mysightsecurity.PinSetupActivity;
import com.justin.mysightsecurity.R;
import com.justin.mysightsecurity.SplashActivity;
import com.justin.mysightsecurity.databinding.FragmentAdddeviceBinding;

public class AddDeviceFragment extends Fragment {

    private FragmentAdddeviceBinding binding;
    private TextView textDeviceName;
    private TextView textDeviceId;
    private TextView textSSID;
    private TextView textPassword;
    private Button btnAddDevice;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddDeviceViewModel notificationsViewModel =
                new ViewModelProvider(this).get(AddDeviceViewModel.class);

        binding = FragmentAdddeviceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        textDeviceName = (TextView) root.findViewById(R.id.text_device_name);
        textDeviceId = (TextView) root.findViewById(R.id.text_device_id);
        textSSID = (TextView) root.findViewById(R.id.text_ssid);
        textPassword = (TextView) root.findViewById(R.id.text_password);
        btnAddDevice = (Button) root.findViewById(R.id.btn_add_device);

        btnAddDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textDeviceName.getText().length() == 0 || textDeviceId.getText().length() == 0 || textSSID.getText().length() == 0 || textPassword.getText().length() == 0) {
                    Toast.makeText(getActivity(), "All fields are required!", Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Are these informations correct?");

                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Your action here

                        // It is time to wifi-checking
                        if ( connectWifi(textSSID.getText().toString(), textPassword.getText().toString()) ) {
                            Toast.makeText(getActivity(), "Wifi connection success!", Toast.LENGTH_SHORT).show();
                            String str = "{\"name\": \"" + textDeviceName.getText().toString()
                                    + "\", \"id\": \"" + textDeviceId.getText().toString()
                                    + "\", \"ssid\": \""+textSSID.getText().toString()
                                    +"\", \"pass\": \""+textPassword.getText().toString()
                                    +"\"}";
                            Bundle bundle = new Bundle();
                            bundle.putString("deviceinfo", str);

                            NavHostFragment.findNavController(AddDeviceFragment.this)
                                    .navigate(R.id.action_add_device_to_galleryFragment, bundle);
                        }
                        else {
                            Toast.makeText(getActivity(), "Wifi connection failed!, Try again!", Toast.LENGTH_SHORT).show();
                            textDeviceName.setText("");
                            textDeviceId.setText("");
                            textSSID.setText("");
                            textPassword.setText("");
                        }
                        // checking end

                    }
                });

                alert.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                textDeviceName.setText("");
                                textDeviceId.setText("");
                                textSSID.setText("");
                                textPassword.setText("");
                            }
                        });

                alert.show();
            }
        });

        return root;
    }

    public boolean connectWifi(String ssid, String password) {
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", ssid);
        wifiConfig.preSharedKey = String.format("\"%s\"", password);
        WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(WIFI_SERVICE);

        int netId =wifiManager.addNetwork(wifiConfig);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);

        boolean isConnectionSuccessful = wifiManager.reconnect();

        return isConnectionSuccessful;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}