package com.justin.mysightsecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class PinInputActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_input);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Intent pininput = new Intent(PinInputActivity.this, PinSetupActivity.class);
        PinInputActivity.this.startActivity(pininput);
        return super.onTouchEvent(event);
    }
}