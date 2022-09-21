package com.justin.mysightsecurity;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.justin.mysightsecurity.databinding.FragmentGalleryBinding;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GalleryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GalleryFragment extends Fragment {
    public interface OnIPCameraListener {
        public void onIPCameraFind(String ip);
    }

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
    private SocketClient mThread;

    private Button btnScan;
    public OnIPCameraListener onIPCameraListner;
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

    public void setIPCameraListner(OnIPCameraListener listener) {
        this.onIPCameraListner = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.onIPCameraListner = null;
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setIPCameraListner(new OnIPCameraListener() {
            @Override
            public void onIPCameraFind(String ip) {
                // Code to handle object ready
                String mip = ip;
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnScan = (Button) view.findViewById(R.id.btn_scan);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Here, Scan IP address to connect

                deviceScan();
                // Edited by BINGO
            }
        });

        String str = getArguments().getString("deviceinfo");

//        imageView = (ImageView) view.findViewById(R.id.imgQR);
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hintMap =new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        CreateQRCode(str, charset, hintMap,250, 250);

//        List<QrSegment> segs = QrSegment.makeSegments(str);
//        QrCode qr1 = QrCode.encodeSegments(segs, QrCode.Ecc.HIGH, 5, 40, 2, false);
//
//        bmp = Bitmap.createBitmap(qr1.size, qr1.size, Bitmap.Config.RGB_565);
//        for (int y = 0; y < qr1.size; y++) {
//            for (int x = 0; x < qr1.size; x++) {
//                bmp.setPixel(x, y, qr1.getModule(x, y) ? Color.BLACK : Color.WHITE);
//            }
//
//        }
//
//        try {
//            imageView.setImageBitmap(encodeAsBitmap(str));
//        } catch (WriterException e) {
//            throw new RuntimeException(e);
//        }
    }

    private void deviceScan() {
        mThread = new SocketClient(this,"", 0);
    }
    public  void CreateQRCode(String qrCodeData, String charset, Map hintMap, int qrCodeheight, int qrCodewidth){

        try {
            //generating qr code.
            BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
            //converting bitmatrix to bitmap

            int width = matrix.getWidth();
            int height = matrix.getHeight();
            int[] pixels = new int[width * height];
            // All are 0, or black, by default
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    //for black and white
                    pixels[offset + x] = matrix.get(x, y) ? BLACK : WHITE;
                    //for custom color
//                    pixels[offset + x] = matrix.get(x, y) ?
//                            ResourcesCompat.getColor(getResources(),R.color.colorB,null) :WHITE;
                }
            }
            //creating bitmap
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

            //getting the logo
            Bitmap overlay = BitmapFactory.decodeResource(getResources(), R.drawable.logo_1);
            //setting bitmap to image view
            imageView.setImageBitmap(mergeBitmaps(overlay, bitmap));

        }catch (Exception er){
            Log.e("QrGenerate",er.getMessage());
        }
    }
    public Bitmap mergeBitmaps(Bitmap overlay, Bitmap bitmap) {

        int height = bitmap.getHeight();
        int width = bitmap.getWidth();

        Bitmap combined = Bitmap.createBitmap(width, height, bitmap.getConfig());
        Canvas canvas = new Canvas(combined);
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        canvas.drawBitmap(bitmap, new Matrix(), null);

        int centreX = (canvasWidth  - overlay.getWidth()) /2;
        int centreY = (canvasHeight - overlay.getHeight()) /2 ;
        canvas.drawBitmap(overlay, centreX, centreY, null);

        return combined;
    }
//    Bitmap encodeAsBitmap(String str) throws WriterException {
//        QRCodeWriter writer = new QRCodeWriter();
//        BitMatrix bitMatrix = writer.encode(str, BarcodeFormat.QR_CODE, 250, 250);
//
//        int w = bitMatrix.getWidth();
//        int h = bitMatrix.getHeight();
//        int[] pixels = new int[w * h];
//        for (int y = 0; y < h; y++) {
//            for (int x = 0; x < w; x++) {
//                pixels[y * w + x] = bitMatrix.get(x, y) ? BLACK : WHITE;
//            }
//        }
//
//        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
//        return bitmap;
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }
}