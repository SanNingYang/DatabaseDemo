package ysn.com.databasedemo.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import ysn.com.databasedemo.bean.UserBean;
import ysn.com.databasedemo.dao.UserDao;

@Database(entities = {UserBean.class}, version = 1, exportSchema = false)
@TypeConverters({ConversionFactory.class})
public abstract class RoomDatabaseHepler extends RoomDatabase {

    private static String ADD_INTEGER_COLUMN = "ALTER TABLE %s ADD %s INTEGER DEFAULT %d ";
    private static String ADD_TEXT_COLUMN = "ALTER TABLE %s ADD COLUMN %s TEXT";

    public static RoomDatabaseHepler getDefault(Context context) {
        return databaseBuilder(context, RoomDatabaseHepler.class, "user.db");
    }

    /**
     * 为持久数据库创建一个RoomDatabase.Builder。
     */
    private static <T extends RoomDatabase> T databaseBuilder(@NonNull Context context, @NonNull Class<T> klass, @NonNull String tableName) {
        return Room.databaseBuilder(context.getApplicationContext(), klass, tableName).
                allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build();
    }

    /**
     * 为内存数据库创建一个RoomDatabase.Builder。存储在内存数据库中的信息在进程终止时消失。
     */
    private static <T extends RoomDatabase> T inMemoryDatabaseBuilder(@NonNull Context context, @NonNull Class<T> klass) {
        return Room.inMemoryDatabaseBuilder(context.getApplicationContext(), klass)
                .allowMainThreadQueries()
                .build();
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL(String.format(ADD_INTEGER_COLUMN, "age", 18));
        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    public abstract UserDao getUserDao();
}
