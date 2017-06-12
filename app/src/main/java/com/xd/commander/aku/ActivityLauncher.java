package com.xd.commander.aku;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.jrummyapps.android.widget.AnimatedSvgView;
import com.xd.commander.aku.base.BaseActivity;
import com.xd.commander.aku.util.ThemeUtil;

public class ActivityLauncher extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launcher;
    }

    @Override
    protected Context getActivity() {
        return ActivityLauncher.this;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 19)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        AnimatedSvgView svgView = (AnimatedSvgView) findViewById(R.id.animated_svg_view);
        svgView.start();
        svgView.setOnStateChangeListener(new AnimatedSvgView.OnStateChangeListener() {
            @Override
            public void onStateChange(int state) {
                if (state == AnimatedSvgView.STATE_FINISHED)
                    startActivity(new Intent(ActivityLauncher.this, ActivityMain.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int theme;
        if (savedInstanceState == null) {
            theme = ThemeUtil.getAppTheme(ActivityLauncher.this);
        } else {
            theme = savedInstanceState.getInt("theme");
        }
        setTheme(theme);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        //TODO nothing
    }
}
