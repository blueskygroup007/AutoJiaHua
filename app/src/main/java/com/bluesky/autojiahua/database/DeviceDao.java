package com.bluesky.autojiahua.database;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.PagingSource;
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

    /*尝试用query加参数的方法查询,并实现分页.未成功*/
    @Query("select * from device where domain=:domain and :search like (:keyWords)")
    List<Device> getDevicesByKeyWordWithQuery(String domain, String search, List<String> keyWords);


    /**
     * 该方法试图拼接sqlite的部分查询语句,作为where子句.测试不可行
     * @param pattern
     * @return
     */
    @Query("select * from device where :pattern")
    List<Device> getDevicesByPattern(String pattern);

    @RawQuery(observedEntities = Device.class)
    List<Device> rawQueryDevicesByPattern(SupportSQLiteQuery query);

    /*采用jetpack中的paging3分页框架*/
//    @RawQuery(observedEntities = Device.class)
//    PagingSource<Integer, Device> LoadAllDevices();
}
