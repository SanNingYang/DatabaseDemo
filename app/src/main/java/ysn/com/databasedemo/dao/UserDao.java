package ysn.com.databasedemo.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ysn.com.databasedemo.bean.UserBean;

@Dao
public interface UserDao {

    /**
     * @return 返回所有数据
     */
    @Query("SELECT * FROM USER")
    List<UserBean> getAllUser();

    /**
     * @param phone 手机号
     * @return 根据 phone 查询的结果
     */
    @Query("SELECT * FROM USER WHERE phone = :phone")
    List<UserBean> queryByPhone(String phone);

    /**
     * @param name  姓名
     * @param limit 查询结果的最大条数
     * @return 根据 phone 查询的 limit 条结果
     */
    @Query("SELECT * FROM USER WHERE name = :name LIMIT :limit")
    List<UserBean> queryByNameLimit(String name, int limit);

    /**
     * 从 offset 条开始，根据指定名字查询 limit 条数据
     *
     * @param name   姓名
     * @param offset 从第 offset 条开始查询
     * @param limit  查询结果的最大条数
     * @return 根据 phone 查询的 limit 条结果
     */
    @Query("SELECT * FROM USER WHERE name = :name LIMIT :offset,:limit")
    List<UserBean> queryByNameOffsetLimit(String name, int offset, int limit);

    /**
     * @param userBeans 需要插入的数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UserBean> userBeans);

    /**
     * @param userBean 需要更新的数据
     */
    @Update()
    void update(UserBean userBean);

    /**
     * @param userBean 需要删除的数据
     */
    @Delete()
    void delete(UserBean userBean);
}
