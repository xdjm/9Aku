package com.xd.commander.aku.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatDelegate;
import com.xd.commander.aku.R;
import com.xd.commander.aku.util.ThemeUtil;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Administrator on 2017/4/17.
 */
public abstract class BaseActivity extends SupportActivity {

    //vector_supported
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    protected Context context;

    //abstracted_method

    //Get_Layout
    protected abstract int getLayoutId();

    //Get_Activity_for_setTheme
    protected abstract Context getActivity();

    //Get_init
    protected abstract void init(Bundle savedInstanceState);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int theme;
        if (savedInstanceState == null) {
            theme = ThemeUtil.getAppTheme(getActivity());
        } else {
            theme = savedInstanceState.getInt("theme");
        }
        setTheme(theme);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        context = this;
        init(savedInstanceState);
    }

    //Content_everywhere
    public Context getContext() {
        return context;
    }

    //loading_theme
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);//进入动画
        finish();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        startActivity(intent);
    }
    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }
}
