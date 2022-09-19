package com.bluesky.autojiahua.database;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagingSource;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.bluesky.autojiahua.bean.Device;
import com.bluesky.autojiahua.common.App;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * @author BlueSky
 * @date 2022/7/16
 * Description:
 */
public class DeviceRepository {
    private AutoDatabase mDatabase;
    private DeviceDao mDeviceDao;
    private InterLockDao mInterLockDao;
    private static volatile DeviceRepository INSTANCE;
    private static MutableLiveData<List<Device>> sMutableLiveData;
    private ListeningExecutorService mPool;

    public DeviceRepository() {
        mDatabase = AutoDatabase.getDatabase(App.getInstance().getApplicationContext());
        mDeviceDao = mDatabase.deviceDao();
        mInterLockDao=mDatabase.ingerLockDao();
        mPool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5));
        sMutableLiveData = new MutableLiveData<>(new ArrayList<>());
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

    public DeviceDao getDeviceDao() {
        return mDeviceDao;
    }

    public InterLockDao getInterLockDao() {
        return mInterLockDao;
    }

    //销毁room数据库
    public void destroy() {
        if (mDatabase != null && mDatabase.isOpen())
            INSTANCE = null;
        mDatabase.close();
        mDatabase = null;
    }


    public MutableLiveData<List<Device>> getMutableLiveData() {
        return sMutableLiveData;
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
            pattern.append("domain='" + domain);
            pattern.append("' and ");
        }
        //循环遍历keyWord分割的数组,每个keyword与search做拼接
        String[] keyWords = keyWord.split(" ");
        if (keyWords != null && keyWords.length > 0) {
            pattern.append(search + " like '");
            for (String word : keyWords
            ) {
                pattern.append("%" + word);
            }
            pattern.append("%'");
        } else {
            pattern.append(search + " like " + "'%'");
        }
        SimpleSQLiteQuery query = new SimpleSQLiteQuery("select * from device where " + pattern);


        ListenableFuture<List<Device>> future = mPool.submit(new Callable<List<Device>>() {
            @Override
            public List<Device> call() throws Exception {
                return mDeviceDao.rawQueryDevicesByPattern(query);
            }
        });
        Futures.addCallback(future, new FutureCallback<List<Device>>() {
            @Override
            public void onSuccess(List<Device> result) {
                sMutableLiveData.postValue(result);
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
            pattern.append("domain='" + domain);
            pattern.append("' and ");
        }
        //循环遍历keyWord分割的数组,每个keyword与search做拼接
        String[] keyWords = keyWord.split(" ");
        if (keyWords != null && keyWords.length > 0) {
            pattern.append(search + " like '");
            for (String word : keyWords
            ) {
                pattern.append("%" + word);
            }
            pattern.append("%'");
        } else {
            pattern.append(search + " like " + "'%'");
        }
        SimpleSQLiteQuery query = new SimpleSQLiteQuery("select * from device where " + pattern);
        Log.e(this.getClass().getSimpleName(), "select * from device where " + pattern);
        return mDeviceDao.LoadAllDevicesByPagingWithKeyword(query);
    }

}


