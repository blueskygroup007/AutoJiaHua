package com.bluesky.autojiahua.database;

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
public interface DeviceDao {
    @Query("select * from device")
    List<Device> getAll();

    @Query("select * from device where tag like :tag")
    List<Device> loadAllByTag(String tag);

    @Insert
    void insertAll(Device... devices);

    @Delete
    void delete(Device device);
}
