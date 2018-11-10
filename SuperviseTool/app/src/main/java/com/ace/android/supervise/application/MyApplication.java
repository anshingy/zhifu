package com.ace.android.supervise.application;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by ace on 2018/10/23.
 */

public class MyApplication extends Application {
    private RefWatcher mWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.mWatcher;
    }

    public static Context getApplicationContext(Context context){
        return context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        mWatcher = LeakCanary.install(this);
    }
}
