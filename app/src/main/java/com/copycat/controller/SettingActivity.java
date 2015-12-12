package com.copycat.controller;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.copycat.util.CoreUtil;
import com.example.baiqizhang.copycat.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //Hide actionbar and status bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Switch aSwitch = (Switch) findViewById(R.id.switch1);
        aSwitch.setChecked(CoreUtil.getSetting("save",SettingActivity.this).equals("yes"));

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CoreUtil.setSetting("save", "yes", SettingActivity.this);
                    Log.d("switch", CoreUtil.getSetting("save", SettingActivity.this));
                } else {
                    CoreUtil.setSetting("save", "no", SettingActivity.this);
                    Log.d("switch", CoreUtil.getSetting("save", SettingActivity.this));
                }
            }
        });

    }
}
