package com.justin.mysightsecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PinSetupActivity extends AppCompatActivity {

    private  Button pinSetupButton;
    private SQLiteDatabase db;
    private TextView password, password2;
    private ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_setup);

        try {
            db=openOrCreateDatabase("sightsecuritydb.db", Context.MODE_ENABLE_WRITE_AHEAD_LOGGING,null);
        }catch (Exception e) {
            Toast.makeText(PinSetupActivity.this, "Can not access database: "+ e.toString(), Toast.LENGTH_SHORT).show();
            return;
        }

        values=new ContentValues();

        password = (TextView) findViewById(R.id.password);
        password2 = (TextView) findViewById(R.id.password2);

        pinSetupButton=(Button) findViewById(R.id.pinsetup);

        pinSetupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(password.getText().length() == 0) {
                    Toast.makeText(PinSetupActivity.this, "Password is Required!", Toast.LENGTH_SHORT).show();
                    db.close();
                    return;
                }
                if(password2.getText().length() == 0) {
                    Toast.makeText(PinSetupActivity.this, "Password Confirmation is Required!", Toast.LENGTH_SHORT).show();
                    db.close();
                    return;
                }
                if(!password.getText().toString().equals(password2.getText().toString()) ) {
                    Toast.makeText(PinSetupActivity.this, "Password must be matched!", Toast.LENGTH_SHORT).show();
                    db.close();
                    return;
                }

                //
                values.put("user_email", "securitysight@gmail.com");
                values.put("user_password", password.getText().toString());


                try {
                    db.insert("User", null, values);
                }catch (Exception e) {
                    Toast.makeText(PinSetupActivity.this, "DataBase error: "+ e.toString(), Toast.LENGTH_SHORT).show();
                    db.close();
                    return;
                }

                Toast.makeText(PinSetupActivity.this, "Password Setting Success!", 1000).show();
                db.close();

                Intent intent = new Intent(PinSetupActivity.this, PinInputActivity.class);
                PinSetupActivity.this.startActivity(intent);
                return;

            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

}