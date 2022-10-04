package com.bluesky.autojiahua.ui.home;

import androidx.lifecycle.LiveData;
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

import java.util.List;

import kotlinx.coroutines.CoroutineScope;

public class HomeViewModel extends ViewModel {

    private final PagingConfig mConfig;
    private int mDomain;
    private int mSearch;
    private String mKeyWord = "";
    private MutableLiveData<String> mLiveDataKeyword;
    private MutableLiveData<List<Device>> mLiveDataDevices;

    CoroutineScope viewModelScope;
    Pager<Integer, Device> pager;

    public MutableLiveData<String> getLiveDataKeyword() {
        if (mLiveDataKeyword==null){
            mLiveDataKeyword=new MutableLiveData<>();
            mLiveDataKeyword.setValue("");
        }
        return mLiveDataKeyword;
    }

    public void setKeyWord(String keyWord) {
        mLiveDataKeyword.postValue(keyWord);
        mKeyWord = keyWord;
    }

    //ViewModel必须有无参数的构造函数
    public HomeViewModel() {
        mDomain = App.HOME_SPINNER_DOMAIN;
        mSearch = App.HOME_SPINNER_SEARCH;

        viewModelScope = ViewModelKt.getViewModelScope(this);
        //enablePlaceholders必须手动指定false,才不会启用占位符.item数量也正确了.
        mConfig = new PagingConfig(20,5,false);

/*        mConfig = new PagingConfig(pageSize,
                prefetchDistance,//
                enablePlaceholders,
                initialLoadSize,
                maxSize,
                jumpThreshold);*/


/*      pageSize：每页多少个条目；必填
        prefetchDistance ：表示距离底部多少条数据开始预加载，设置0则表示滑到底部才加载.无缝加载（可选）默认值是pageSize
        enablePlaceholders：是否启用条目占位，当条目总数量确定的时候；列表一次性展示所有条目，但是没有数据；在adapter的onBindViewHolder里面绑定数据时候，是空数据，判断是空数据展示对应的占位item；可选，默认开启。
        initialLoadSize ：第一页加载条目数量 ，可选，默认值是 3*pageSize （有时候需要第一页多点数据可用）
        maxSize ：定义列表最大数量；可选，默认值是：Int.MAX_VALUE
        jumpThreshold：暂时还不知道用法，从文档注释上看，是滚动大距离导致加载失效的阈值；可选，默认值是：Int.MIN_VALUE （表示禁用此功能）*/


    }

    public LiveData<PagingData<Device>> getAllDevicesByPaging() {
        pager = new Pager<>(mConfig, () -> DeviceRepository.getInstance().loadDeviceByKeywordWithQuery(App.DOMAIN[mDomain], App.SEARCH[mSearch], mKeyWord));
        //cacheIn方法是Flow<PagingData>的扩展方法,用于将服务器返回的数据在ViewModelScope这个作用域内进行缓存,
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
            mLiveDataDevices = DeviceRepository.getInstance().getmRepsMutableDevice();
        }
        return mLiveDataDevices;
    }

}