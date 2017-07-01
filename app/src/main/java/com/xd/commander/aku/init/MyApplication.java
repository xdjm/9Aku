package com.xd.commander.aku.init;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.pgyersdk.crash.PgyCrashManager;
import com.tencent.bugly.Bugly;
import com.xd.commander.aku.constants.Constants;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {
    /**
     * 初始化，定义全局上下文
     */
    @Override
    public void onCreate() {
        super.onCreate();
//        LeakCanary.install(this);
        PgyCrashManager.register(this);
        //初始BUGly
        Bugly.init(getApplicationContext(), Constants.Key_Bugly, true);
        //TODO 全局监听网络变化 弹小吃
        registerActivityLifecycleCallbacks(this);
        registerReceiver(broadcastReceiver, new IntentFilter(Constants.NETWORK_DETECTOR));
        LitePal.initialize(getApplicationContext());
        Connector.getDatabase();
    }
    Activity visibleActivity;
    final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // internet lost alert dialog method call from here...
            boolean isDataAvailable = intent.getBooleanExtra(Constants.DATA_AVAILABLE, false);
            if (visibleActivity != null) {
                try {
                    String className = visibleActivity.getComponentName().getClassName();
                    Class<?> clazz = Class.forName(className);
                    Method method = clazz.getMethod(Constants.ON_SYNC, boolean.class);
                    try {
                        method.invoke(visibleActivity, isDataAvailable);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchMethodException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        visibleActivity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }
}
