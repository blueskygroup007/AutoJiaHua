package com.bluesky.autojiahua.ui.interlock;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bluesky.autojiahua.bean.InterLock;
import com.bluesky.autojiahua.database.DeviceRepository;

import java.util.ArrayList;
import java.util.List;

public class InterlockViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<List<InterLock>> mData;
    private int domainPosition;

    public InterlockViewModel() {
        mData = new MutableLiveData<>(new ArrayList<>());
        mData = DeviceRepository.getInstance().getRepsMutableInterlock();
        this.domainPosition = 0;
    }

    public MutableLiveData<List<InterLock>> getData() {
        return mData;
    }

    public int getDomainPosition() {
        return domainPosition;
    }

    public void setDomainPosition(int domainPosition) {
        this.domainPosition = domainPosition;
    }
}