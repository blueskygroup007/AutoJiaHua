package com.bluesky.autojiahua.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bluesky.autojiahua.bean.Device;

/**
 * @author BlueSky
 * @date 2022/7/16
 * Description:
 */
@Database(entities = {Device.class}, version = 1)
public abstract class AutoDatabase extends RoomDatabase {
    private static AutoDatabase INSTANCE;

    public abstract DeviceDao deviceDao();

    public static synchronized AutoDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AutoDatabase.class, "jiahua.db")
                    .createFromAsset("jiahua.db")//从资源文件预加载
                    //.addMigrations(migration_1_2)//添加迁移策略
                    //.fallbackToDestructiveMigration()//回滚
                    //.allowMainThreadQueries()//强制主线程查询
                    .build();
        }
        return INSTANCE;
    }
}
