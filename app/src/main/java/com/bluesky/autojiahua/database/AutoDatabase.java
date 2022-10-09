package com.bluesky.autojiahua.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bluesky.autojiahua.bean.Device;
import com.bluesky.autojiahua.bean.InterLock;
import com.bluesky.autojiahua.common.App;

import java.io.File;

/**
 * @author BlueSky
 * @date 2022/7/16
 * Description:
 */
@Database(entities = {Device.class, InterLock.class}, version = 2, exportSchema = false)
public abstract class AutoDatabase extends RoomDatabase {
    private static AutoDatabase INSTANCE;

    public abstract DeviceDao deviceDao();

    public abstract InterLockDao ingerLockDao();

    public static synchronized AutoDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            //如果本地数据库文件存在,则不必从asset拷贝
            File fileDatabase = App.getInstance().getDatabasePath("jiahua.db");
            Builder<AutoDatabase> autoDatabaseBuilder = Room.databaseBuilder(context.getApplicationContext(), AutoDatabase.class, "jiahua.db");
            if (!fileDatabase.exists()) {
                autoDatabaseBuilder.createFromAsset("jiahua.db");//从资源文件预加载
                //.addMigrations(migration_1_2)//添加迁移策略
                //.fallbackToDestructiveMigration()//回滚
                //.allowMainThreadQueries()//强制主线程查询
            }
            INSTANCE = autoDatabaseBuilder.build();
        }
        return INSTANCE;
    }
}
