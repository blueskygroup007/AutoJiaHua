package com.bluesky.autojiahua.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.bluesky.autojiahua.bean.Device;
import com.bluesky.autojiahua.common.App;
import com.bluesky.autojiahua.database.AutoDatabase;
import com.bluesky.autojiahua.database.DeviceRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MediatorLiveData<List<Device>> mDevices=new MediatorLiveData<>();
    private int mDomain;
    private int mSearch;
    private String mKeyWord="";
    private LiveData<List<Device>> mLiveDataDevices;

    public String getKeyWord() {
        return mKeyWord;
    }

    public void setKeyWord(String keyWord) {
        mKeyWord = keyWord;
    }

    //ViewModel必须有无参数的构造函数
    public HomeViewModel() {
        mLiveDataDevices=DeviceRepository.getInstance(AutoDatabase.getDatabase(App.getInstance()).deviceDao()).getAllDevices();
        mDevices.addSource(mLiveDataDevices, new Observer<List<Device>>() {
            @Override
            public void onChanged(List<Device> devices) {
                mDevices.setValue(devices);
            }
        });

    }

    public int getDomain() {
        mDomain=App.HOME_SPINNER_DOMAIN;
        return mDomain;
    }

    public void setDomain(int domain) {
        App.putDomain(domain);
        mDomain = domain;
    }

    public int getSearch() {
        mSearch=App.HOME_SPINNER_SEARCH;
        return mSearch;
    }

    public void setSearch(int search) {
        App.putSearch(search);
        mSearch = search;

    }

    public MediatorLiveData<List<Device>> getDevices() {
        return mDevices;
    }

    public MediatorLiveData<List<Device>> findDevices() {
        mLiveDataDevices=DeviceRepository.getInstance(AutoDatabase.getDatabase(App.getInstance()).deviceDao())
                .getDeviceBykeyWord(App.DOMAIN[mDomain],App.SEARCH[mSearch],mKeyWord);
        //todo 这里获取到查询数据库的结果LiveData，如何赋值给被监听的mDevices
        mDevices.addSource(mLiveDataDevices, new Observer<List<Device>>() {
            @Override
            public void onChanged(List<Device> devices) {
                mDevices.setValue(devices);
            }
        });
        return mDevices;
    }
}