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
    private Activity visibleActivity;
    @Override
    public void onCreate() {
        super.onCreate();
        //init LeakCanary
       // LeakCanary.install(this);
        //init pgy_crash_report_from_user
        PgyCrashManager.register(this);
        //init bugly_crash_report_from_net
        Bugly.init(getApplicationContext(), Constants.Key_Bugly, true);
        registerActivityLifecycleCallbacks(this);
        registerReceiver(broadcastReceiver, new IntentFilter(Constants.NETWORK_DETECTOR));
        LitePal.initialize(getApplicationContext());
        Connector.getDatabase();
    }

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
