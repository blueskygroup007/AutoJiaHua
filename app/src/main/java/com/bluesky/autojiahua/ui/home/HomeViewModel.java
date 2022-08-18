package com.bluesky.autojiahua.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.bluesky.autojiahua.bean.Device;
import com.bluesky.autojiahua.common.App;
import com.bluesky.autojiahua.database.DeviceRepository;

import java.util.ArrayList;
import java.util.List;

import kotlinx.coroutines.CoroutineScope;

public class HomeViewModel extends ViewModel {

    private final PagingConfig mConfig;
    private int mDomain;
    private int mSearch;
    private String mKeyWord = "";
    private MutableLiveData<List<Device>> mLiveDataDevices;

    CoroutineScope viewModelScope;
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

        viewModelScope = ViewModelKt.getViewModelScope(this);
        mConfig = new PagingConfig(50);

        mConfig = new PagingConfig(pageSize,
                prefetchDistance,//表示距离底部多少条数据开始预加载，设置0则表示滑到底部才加载
                enablePlaceholders,
                initialLoadSize,
                maxSize,
                jumpThreshold);

//        PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), this);
    }

    public LiveData<PagingData<Device>> getAllDevicesByPaging() {
        pager = new Pager<>(mConfig, () -> DeviceRepository.getInstance().loadDeviceByKeywordWithQuery(App.DOMAIN[mDomain], App.SEARCH[mSearch], mKeyWord));
        //cacheIn方法使Flow<PagingData>的扩展方法,用于将服务器返回的数据在ViewModelScope这个作用域内进行缓存,
        // 假如手机横竖屏导致activity重建,Paging3就可以直接读取缓存中的数据,而不用重新发起网络请求了.
        return PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);
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