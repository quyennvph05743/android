package edu.fpt.lenovo.shoponline.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import edu.fpt.lenovo.shoponline.R;
import edu.fpt.lenovo.shoponline.ultil.Checkconection;

public class HelloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        Timer timer = new Timer();
        if (Checkconection.haveNetworkConnection(getApplicationContext())) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(HelloActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);
        } else {
            Checkconection.ShowToast_short(getApplicationContext(), "Kiểm tra lại Internet");
            finish();
        }

    }
}
