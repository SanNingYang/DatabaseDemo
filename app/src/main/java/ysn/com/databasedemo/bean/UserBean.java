package ysn.com.databasedemo.bean;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "USER")
public class UserBean implements Parcelable {

    @PrimaryKey(autoGenerate = true) // 设置主键
    @ColumnInfo(name = "ID") // 定义对应的数据库的字段名成
    private int id;

    @ColumnInfo(name = "PHONE")
    private String phone;

    @NonNull
    @ColumnInfo(name = "NAME")
    private String name;

    @ColumnInfo(name = "DATE")
    private Date date;

    @ColumnInfo(name = "AGE")
    private int age = 18;

    public UserBean(String phone, String name, Date date) {
        this.phone = phone;
        this.name = name;
        this.date = date;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.phone);
        dest.writeString(this.name);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
    }

    protected UserBean(Parcel in) {
        this.id = in.readInt();
        this.phone = in.readString();
        this.name = in.readString();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
    }

    public static final Parcelable.Creator<UserBean> CREATOR = new Parcelable.Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel source) {
            return new UserBean(source);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };
}
