package com.bluesky.autojiahua.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author BlueSky
 * @date 2021/12/31
 * Description:
 */
class DiskIOThreadExecutor implements Executor {
    private final ExecutorService mDiskIO;

    public DiskIOThreadExecutor() {
        mDiskIO = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(Runnable command) {
        mDiskIO.execute(command);
    }
}
