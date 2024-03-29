package com.bluesky.autojiahua.database;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagingSource;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.bluesky.autojiahua.bean.Device;
import com.bluesky.autojiahua.bean.InterLock;
import com.bluesky.autojiahua.common.App;
import com.bluesky.autojiahua.utils.AppExecutors;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author BlueSky
 * @date 2022/7/16
 * Description:
 */
public class DeviceRepository {
    private AutoDatabase mDatabase;
    private final DeviceDao mDeviceDao;
    private final InterLockDao mInterLockDao;
    private static volatile DeviceRepository INSTANCE;
    private static MutableLiveData<List<Device>> mRepsMutableDevice;
    private final MutableLiveData<List<InterLock>> mRepsMutableInterlock;
    private static MutableLiveData<Integer> mCountDevice;
    //ListenableFuture方式异步线程
    private final ListeningExecutorService mPool;
    //Executor方式异步线程
    final AppExecutors mExecutors = AppExecutors.getInstance();


    public DeviceRepository() {
        mDatabase = AutoDatabase.getDatabase(App.getInstance().getApplicationContext());
        mDeviceDao = mDatabase.deviceDao();
        mInterLockDao = mDatabase.ingerLockDao();
        mPool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5));
        mRepsMutableDevice = new MutableLiveData<>();
        mRepsMutableInterlock = new MutableLiveData<>();
        mCountDevice = new MutableLiveData<>();
    }

    public static DeviceRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (DeviceRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DeviceRepository();
                }
            }
        }
        return INSTANCE;
    }

    //销毁room数据库
    public void destroy() {
        if (mDatabase != null && mDatabase.isOpen()) {
            INSTANCE = null;
            mDatabase.close();
            mDatabase = null;
        }
    }

    public DeviceDao getDeviceDao() {
        return mDeviceDao;
    }

    public InterLockDao getInterLockDao() {
        return mInterLockDao;
    }

    public MutableLiveData<List<Device>> getmRepsMutableDevice() {
        if (mRepsMutableDevice.getValue() == null) {
            //赋一个空list
            mRepsMutableDevice.setValue(new ArrayList<>());
        }
        return mRepsMutableDevice;
    }

    public MutableLiveData<List<InterLock>> getRepsMutableInterlock() {
        if (mRepsMutableInterlock.getValue() == null) {
            getAllInterLock();
        }
        return mRepsMutableInterlock;
    }

    public MutableLiveData<Integer> getmCountDevice() {
        if (mCountDevice.getValue() == null) {
            mCountDevice.setValue(0);
        }
        return mCountDevice;
    }

    public void countDeviceByKeyword(String domain, String search, String keyWord) {
        StringBuilder pattern = new StringBuilder();
        //如果domain为空,即搜索全部,跳过domain字串拼接
        if (!domain.isEmpty()) {
            pattern.append("domain='").append(domain);
            pattern.append("' and ");
        }
        //循环遍历keyWord分割的数组,每个keyword与search做拼接
        String[] keyWords = keyWord.split(" ");
        if (keyWords.length > 0) {
            pattern.append(search).append(" like '");
            for (String word : keyWords
            ) {
                pattern.append("%").append(word);
            }
            pattern.append("%'");
        } else {
            pattern.append(search).append(" like ").append("'%'");
        }
        SimpleSQLiteQuery query = new SimpleSQLiteQuery("select count(*) from device where " + pattern);


        ListenableFuture<Integer> future = mPool.submit(() -> mDeviceDao.rawCountQueryDevicesByPattern(query));
        Futures.addCallback(future, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                mCountDevice.postValue(result);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        }, mPool);
    }

    /**
     * 使用新的线程池来执行数据库查询
     *
     * @param domain
     * @param search
     * @param keyWord
     */
    public void loadDeviceByKeyword(String domain, String search, String keyWord) {
        StringBuilder pattern = new StringBuilder();
        //如果domain为空,即搜索全部,跳过domain字串拼接
        if (!domain.isEmpty()) {
            pattern.append("domain='").append(domain);
            pattern.append("' and ");
        }
        //循环遍历keyWord分割的数组,每个keyword与search做拼接
        String[] keyWords = keyWord.split(" ");
        if (keyWords.length > 0) {
            pattern.append(search).append(" like '");
            for (String word : keyWords
            ) {
                pattern.append("%").append(word);
            }
            pattern.append("%'");
        } else {
            pattern.append(search).append(" like ").append("'%'");
        }
        SimpleSQLiteQuery query = new SimpleSQLiteQuery("select * from device where " + pattern);


        ListenableFuture<List<Device>> future = mPool.submit(() -> mDeviceDao.rawQueryDevicesByPattern(query));
        Futures.addCallback(future, new FutureCallback<List<Device>>() {
            @Override
            public void onSuccess(List<Device> result) {
                mRepsMutableDevice.postValue(result);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        }, mPool);
    }


    public PagingSource<Integer, Device> loadDeviceByKeywordWithQuery(String domain, String search, String keyWord) {
        StringBuilder pattern = new StringBuilder();
        //如果domain为空,即搜索全部,跳过domain字串拼接
        if (!domain.isEmpty()) {
            pattern.append("domain='").append(domain);
            pattern.append("' and ");
        }
        //循环遍历keyWord分割的数组,每个keyword与search做拼接
        String[] keyWords = keyWord.split(" ");
        if (keyWords != null && keyWords.length > 0) {
            pattern.append(search).append(" like '");
            for (String word : keyWords
            ) {
                pattern.append("%").append(word);
            }
            pattern.append("%'");
        } else {
            pattern.append(search).append(" like ").append("'%'");
        }
        SimpleSQLiteQuery query = new SimpleSQLiteQuery("select * from device where " + pattern);
        Log.e(this.getClass().getSimpleName(), "select * from device where " + pattern);
        return mDeviceDao.LoadAllDevicesByPagingWithKeyword(query);
    }

    public void getInterLockByDomain(String domain) {

        mExecutors.getDatabaseIO().execute(() -> mRepsMutableInterlock.postValue(mInterLockDao.getInterLockByDomain(domain)));
    }

    public void getAllInterLock() {
        mExecutors.getDatabaseIO().execute(() -> mRepsMutableInterlock.postValue(mInterLockDao.getAllInterLock()));
    }
}


