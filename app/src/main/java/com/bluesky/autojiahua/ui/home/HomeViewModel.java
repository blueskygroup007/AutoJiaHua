package com.bluesky.autojiahua.ui.home;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.bluesky.autojiahua.bean.Device;
import com.bluesky.autojiahua.database.DeviceRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MediatorLiveData<List<Device>> mDevices;

    public HomeViewModel(DeviceRepository deviceRepository) {
        mDevices.addSource(deviceRepository.getAllDevices(), new Observer<List<Device>>() {
            @Override
            public void onChanged(List<Device> devices) {
                mDevices.setValue(devices);
            }
        });

    }

    public MediatorLiveData<List<Device>> getDevices() {
        return mDevices;
    }
}