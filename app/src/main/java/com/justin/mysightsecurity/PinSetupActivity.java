package com.justin.mysightsecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class PinSetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_setup);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Intent pininput = new Intent(PinSetupActivity.this, MainActivity.class);
        PinSetupActivity.this.startActivity(pininput);
        return super.onTouchEvent(event);
    }
}