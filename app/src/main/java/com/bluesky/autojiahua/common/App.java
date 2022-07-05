package com.bluesky.autojiahua.common;

import android.app.Application;

/**
 * @author BlueSky
 * @date 2022/7/4
 * Description:
 */
class App extends Application {
    public static App app;

    public static App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        //Todo 初始化全局变量
    }
}
