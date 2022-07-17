package com.bluesky.autojiahua.database;

import androidx.lifecycle.LiveData;

import com.bluesky.autojiahua.bean.Device;
import com.bluesky.autojiahua.common.App;

import java.util.List;

/**
 * @author BlueSky
 * @date 2022/7/16
 * Description:
 */
public class DeviceRepository {
    private DeviceDao mDeviceDao;
    private static volatile DeviceRepository sRepository;

    public DeviceRepository() {
        AutoDatabase database = AutoDatabase.getDatabase(App.getInstance().getApplicationContext());
        mDeviceDao = database.deviceDao();
    }

    public LiveData<List<Device>> getAllDevices() {
        return mDeviceDao.getAll();
    }

    public LiveData<List<Device>> getDevicesByTag(String tag) {
        return mDeviceDao.getDevicesByTag(tag);
    }
}


