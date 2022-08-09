package com.bluesky.autojiahua.utils;

/**
 * @author BlueSky
 * @date 2021/12/31
 * Description:
 */

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author BlueSky
 * @date 2019/3/23
 * Description:
 */
public class AppExecutors {

    private static final int THREAD_COUNT = 5;
    private static AppExecutors INSTANCE;
    private final Executor diskIO;
    private final Executor networkIO;
    private final Executor mainThread;
    private final Executor databaseIO;

    public AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread, Executor databaseIO) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
        this.databaseIO = databaseIO;
    }

    public static AppExecutors getInstance(){
        if (INSTANCE==null){
            INSTANCE=new AppExecutors(new DiskIOThreadExecutor(), Executors.newFixedThreadPool(THREAD_COUNT), new MainThreadExecutor(), Executors.newFixedThreadPool(THREAD_COUNT));
        }
        return INSTANCE;
    }

//    public  AppExecutors() {
//        this(new DiskIOThreadExecutor(), Executors.newFixedThreadPool(THREAD_COUNT), new MainThreadExecutor(), Executors.newFixedThreadPool(THREAD_COUNT));
//    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }

    public Executor getMainThread() {
        return mainThread;
    }

    public Executor getDatabaseIO() {
        return databaseIO;
    }

}