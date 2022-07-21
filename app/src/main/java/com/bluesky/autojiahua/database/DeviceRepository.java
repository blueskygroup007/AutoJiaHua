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
        StringBuilder pattern = new StringBuilder();
        //如果domain为空,直接跳过该条件.
        if (!domain.isEmpty()) {
            pattern.append("domain='" + domain);
            pattern.append("' and ");

        }
        //将keyword转换为多个字符串,再用%串起来.
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
        return mDeviceDao.getDevicesByPattern( pattern.toString());
    }
}


