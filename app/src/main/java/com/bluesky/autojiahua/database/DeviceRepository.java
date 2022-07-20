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
    private static volatile DeviceRepository INSTANCE;

    public DeviceRepository() {
        AutoDatabase database = AutoDatabase.getDatabase(App.getInstance().getApplicationContext());
        mDeviceDao = database.deviceDao();
    }

    public static DeviceRepository getInstance(DeviceDao deviceDao) {
        if (INSTANCE == null) {
            synchronized (DeviceRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DeviceRepository();
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<List<Device>> getAllDevices() {
        return mDeviceDao.getAll();
    }

    public LiveData<List<Device>> getDevicesByTag(String tag) {
        return mDeviceDao.getDevicesByTag(tag);
    }

    public LiveData<List<Device>> getDeviceBykeyWord(String domain, String search, String keyWord) {
        return mDeviceDao.getDevicesByKeyWord(domain, search, keyWord);
    }
}


