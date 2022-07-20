package com.bluesky.autojiahua.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.bluesky.autojiahua.bean.Device;

import java.util.List;

/**
 * @author BlueSky
 * @date 2022/7/16
 * Description:
 */
@Dao
public interface DeviceDao {
    @Query("select * from device")
    LiveData<List<Device>> getAll();

    @Query("select * from device where tag like :tag")
    LiveData<List<Device>> getDevicesByTag(String tag);

    @Insert
    void insertAll(Device... devices);

    @Delete
    void delete(Device device);

    @Query("select * from device where domain=:domain and :search like :keyWord")
    LiveData<List<Device>> getDevicesByKeyWord(String domain, String search, String keyWord);
}
