package com.bluesky.autojiahua.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

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
    List<Device> getDevicesByTag(String tag);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Device... devices);

    @Delete
    void delete(Device device);

    @Query("select * from device where domain=:domain and :search like :keyWord")
    List<Device> getDevicesByKeyWord(String domain, String search, String keyWord);


    @Query("select * from device where :pattern")
    List<Device> getDevicesByPattern(String pattern);

    @RawQuery(observedEntities = Device.class)
    List<Device> rawQueryDevicesByPattern(SupportSQLiteQuery query);
}
