package com.bluesky.autojiahua.database;

import androidx.room.Dao;
import androidx.room.Query;

import com.bluesky.autojiahua.bean.InterLock;

import java.util.List;

/**
 * @author BlueSky
 * @date 2022/7/18
 * Description:
 */
@Dao
public interface InterLockDao {
    @Query("select * from interlock")
    List<InterLock> LoadAllInterLock();

    @Query("select * from interlock")
    List<InterLock> getAllInterLock();

    @Query("select * from interlock where domain like :domain")
    List<InterLock> getInterLockByDomain(String domain);
}
