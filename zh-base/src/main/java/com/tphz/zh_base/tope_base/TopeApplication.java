package com.tphz.zh_base.tope_base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tphz.zh_base.common.Constants;
import com.tphz.zh_base.common.HandlerTasks;
import com.tphz.zh_base.common.LogUtil;
import com.tphz.zh_base.http_lib.HLog;
import com.tphz.zh_base.tope_base.analysis.TopeAnalysis;
import com.tphz.zh_base.tope_base.analysis.TopeAnalysisClient;
import com.tphz.zh_base.tope_base.data.TopeDatas;
import com.tphz.zh_base.tope_base.image.cdn.CdnDomain;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.umcrash.UMCrash;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

public class TopeApplication extends Application {
    private String uuid = "";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void init(boolean isDebug, String buglyKey, String umengKey) {
        Constants.init(getApplicationContext());
        HandlerTasks.initInstance();
        initCndKey();
        initLifecycle(isDebug);
        initLog(isDebug);
        LogUtil.imp(LogUtil.defaultTree);
//        initBugly(isDebug, buglyKey);
//        RxTool.init(this);
        initUment(isDebug, umengKey);
    }

    private void initCndKey() {
        CdnDomain.getKeys();
    }

    private void initLifecycle(boolean isDebug) {
        if (isDebug) {
            TopeAnalysisClient.setBaseUrl("https://gateway-test.izhh.com.cn/growthsecret/");
        } else {
            TopeAnalysisClient.setBaseUrl("https://gateway.izhh.com.cn/growthsecret/");
        }
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                uuid = UUID.randomUUID().toString();
                LogUtil.d("TopeApplication", "onActivityResumed:" + activity.getClass().getName());
                TopeAnalysis.saveEvent("10001", activity.getClass().getName(), uuid);
//                TopeAnalysis.saveEvent("10005",     activity.getClass().getName(), uuid);
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                LogUtil.d("TopeApplication", "onActivityPaused:" + activity.getClass().getName());
                TopeAnalysis.saveEvent("10002", activity.getClass().getName(), uuid);
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }

    private void initUment(boolean isDebug, String umengKey) {
        if (!TextUtils.isEmpty(umengKey)) {
            UMConfigure.setLogEnabled(isDebug);
            UMConfigure.preInit(this, umengKey, "channel");
            UMConfigure.init(getApplicationContext(), umengKey, "channel", UMConfigure.DEVICE_TYPE_PHONE, "");
            MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
            if (TopeDatas.getUserId() != null && TopeDatas.getChildrenId() != null) {
                MobclickAgent.onProfileSignIn(TopeDatas.getUserId() + "_" + TopeDatas.getChildrenId());
            }
        } else {
            LogUtil.e("TopeApplication", "buglyKey is null");
        }
    }

    private void initLog(boolean isDebug) {
        if (isDebug) {
            LogUtil.imp(LogUtil.defaultTree);
        } else {
            LogUtil.imp(new LogUtil.Tree() {
                @Override
                public void v(String tag, String info) {
                    Log.v(tag, info);
                }

                @Override
                public void d(String tag, String info) {
                    Log.d(tag, info);
                }

                @Override
                public void i(String tag, String info) {
                    Log.i(tag, info);
                }

                @Override
                public void w(String tag, String info) {
                    Log.w(tag, info);
                }

                @Override
                public void e(String tag, String info) {
                    Log.e(tag, info);
                    Exception exception = new Exception(info);
                    UMCrash.generateCustomLog(exception, "NewException");
                }

                @Override
                public void e(String tag, String info, Throwable tr) {
                    Log.e(tag, info);
                    UMCrash.generateCustomLog(tr, "NewException");
                }
            });
        }
        HLog.imp(new HLog.Tree() {
            @Override
            public void log(String s) {
                LogUtil.d("OkHttpClient", s);
            }

            @Override
            public void error(Throwable t) {
                LogUtil.e("OkHttpClient", t.getMessage(), t);
            }
        });
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    protected static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
