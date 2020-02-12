package com.syahputrareno975.dompetku.ui.activity.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.syahputrareno975.dompetku.R;
import com.syahputrareno975.dompetku.ui.activity.mainMenuActivity.MainMenuActivity;

public class SplashActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initWidget();
    }

    private void initWidget(){
        this.context = this;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(context, MainMenuActivity.class));
                finish();
            }
        },3000);
    }
}
