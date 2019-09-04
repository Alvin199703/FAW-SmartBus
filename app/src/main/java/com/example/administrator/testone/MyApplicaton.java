package com.example.administrator.testone;

import android.app.Application;

import com.videogo.openapi.EZOpenSDK;

/**
 * created by zw 2019/1/15
 */
public class MyApplicaton extends Application {
    static  public  String APP_KEY="d10d5af273b1418cacfa5e7c3a475509";
    public static EZOpenSDK getOpenSDK() {
        return EZOpenSDK.getInstance();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initSDK();
    }

    private void initSDK() {
        {
            /**
             * sdk日志开关，正式发布需要去掉
             */
            EZOpenSDK.showSDKLog(true);

            /**
             * 设置是否支持P2P取流,详见api
             */
            EZOpenSDK.enableP2P(true);

            /**
             * APP_KEY请替换成自己申请的
             */
            EZOpenSDK.initLib(this, APP_KEY);

        }
    }

}
