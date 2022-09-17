package com.justin.mysightsecurity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.justin.mysightsecurity.databinding.FragmentAdddeviceBinding;
import com.justin.mysightsecurity.databinding.FragmentGalleryBinding;

import io.nayuki.qrcodegen.*;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GalleryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GalleryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private FragmentGalleryBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Bitmap bmp;
    private ImageView imageView;

    private Button btnScan;
    public GalleryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GalleryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GalleryFragment newInstance(String param1, String param2) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String str = getArguments().getString("deviceinfo");

        imageView = (ImageView) view.findViewById(R.id.imgQR);

        List<QrSegment> segs = QrSegment.makeSegments(str);
        QrCode qr1 = QrCode.encodeSegments(segs, QrCode.Ecc.HIGH, 5, 40, 2, false);

        bmp = Bitmap.createBitmap(qr1.size, qr1.size, Bitmap.Config.RGB_565);
        for (int y = 0; y < qr1.size; y++) {
            for (int x = 0; x < qr1.size; x++) {
//                        (... paint qr1.getModule(x, y) ...)
                bmp.setPixel(x, y, qr1.getModule(x, y) ? Color.BLACK : Color.WHITE);
            }

        }
        imageView.setImageBitmap(bmp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }
}