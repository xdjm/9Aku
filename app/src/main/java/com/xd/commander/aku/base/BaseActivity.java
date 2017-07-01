package com.xd.commander.aku.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import com.xd.commander.aku.R;
import com.xd.commander.aku.util.ThemeUtil;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Administrator on 2017/4/17.
 */
public abstract class BaseActivity extends SupportActivity{
    //支持vector图片
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    protected Context context;
    private int messageShowCount = 0;

    //抽象类，需要在具体Activity实现其方法

    protected abstract int getLayoutId();

    protected abstract Context getActivity();

    protected abstract void init(Bundle savedInstanceState);

    //Activity加载布局的生命周期，所有Activity都必有，写在基类中，减少重复
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

    //方法：全局上下文

    public Context getContext() {
        return context;
    }

    //清理无用内存的

    public void count() {
        messageShowCount++;
        if (messageShowCount >= 5) {
            System.gc();
            messageShowCount = 0;
        }
    }

    //方法：加载Theme，实现切换皮肤效果
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);//进入动画
        finish();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        startActivity(intent);
    }
}
