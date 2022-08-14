package com.bluesky.autojiahua.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.bluesky.autojiahua.bean.Device;
import com.bluesky.autojiahua.common.App;
import com.bluesky.autojiahua.database.DeviceRepository;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private int mDomain;
    private int mSearch;
    private String mKeyWord = "";
    private MutableLiveData<List<Device>> mLiveDataDevices;

    Pager<Integer, Device> pager;


    public String getKeyWord() {
        return mKeyWord;
    }

    public void setKeyWord(String keyWord) {
        mKeyWord = keyWord;
    }

    //ViewModel必须有无参数的构造函数
    public HomeViewModel() {
        mDomain = App.HOME_SPINNER_DOMAIN;
        mSearch = App.HOME_SPINNER_SEARCH;
        PagingConfig config = new PagingConfig(10);
        pager = new Pager<>(config, () -> DeviceRepository.getInstance().loadDeviceByKeywordWithQuery(App.DOMAIN[mDomain], App.SEARCH[mSearch], mKeyWord));
    }

    public LiveData<PagingData<Device>> getAllDevicesByPaging() {
        return PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), this);
    }


    public int getDomain() {
        mDomain = App.HOME_SPINNER_DOMAIN;
        return mDomain;
    }

    public void setDomain(int domain) {
        App.putDomain(domain);
        mDomain = domain;
    }

    public int getSearch() {
        mSearch = App.HOME_SPINNER_SEARCH;
        return mSearch;
    }

    public void setSearch(int search) {
        App.putSearch(search);
        mSearch = search;

    }

    public MutableLiveData<List<Device>> getLiveDataDevices() {
        if (mLiveDataDevices == null) {
            mLiveDataDevices = DeviceRepository.getInstance().getMutableLiveData();
        }
        return mLiveDataDevices;
    }

    public void findDevices() {
        Log.e("HomeViewModel", "findDevices()的参数==  " + App.DOMAIN[mDomain] + "---" + App.SEARCH[mSearch] + "---" + mKeyWord);
        //DeviceRepository.getInstance().loadDeviceByKeyword(App.DOMAIN[mDomain], App.SEARCH[mSearch], mKeyWord);
        DeviceRepository.getInstance().loadDeviceByKeyword(App.DOMAIN[mDomain], App.SEARCH[mSearch], mKeyWord);

    }


}