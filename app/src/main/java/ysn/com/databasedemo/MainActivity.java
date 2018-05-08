package ysn.com.databasedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ysn.com.databasedemo.adapter.UserListAdapter;
import ysn.com.databasedemo.application.MyApplication;
import ysn.com.databasedemo.bean.UserBean;
import ysn.com.databasedemo.db.RoomDatabaseHepler;
import ysn.com.databasedemo.ui.EditDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText insertNameEdit, insertPhoneEdit, queryNamePhoneEdit;
    private RecyclerView mRecyclerView;
    private UserListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        insertNameEdit = findViewById(R.id.insert_name_edit);
        insertPhoneEdit = findViewById(R.id.insert_phone_edit);
        queryNamePhoneEdit = findViewById(R.id.query_name_edit);
        mRecyclerView = findViewById(R.id.data_list_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getInstance()));
        findViewById(R.id.query_all_button).setOnClickListener(this);
        findViewById(R.id.query_name_button).setOnClickListener(this);
        findViewById(R.id.insert_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String mName = insertNameEdit.getText().toString();
        String mPhone = insertPhoneEdit.getText().toString();
        switch (v.getId()) {
            case R.id.query_all_button:
                notifyItemChanged(RoomDatabaseHepler.getDefault(MyApplication.getInstance()).getUserDao().getAllUser());
                break;
            case R.id.query_name_button:
                String name = queryNamePhoneEdit.getText().toString().trim();
                if (name.isEmpty()) {
                    return;
                }
                notifyItemChanged(RoomDatabaseHepler.getDefault(MyApplication.getInstance()).getUserDao().queryByNameLimit(name, 1));
                break;
            case R.id.insert_button:
                if (mName.isEmpty()) {
                    Toast.makeText(MyApplication.getInstance(), "姓名不能为空", Toast.LENGTH_SHORT).show();
                } else if (mPhone.isEmpty()) {
                    Toast.makeText(MyApplication.getInstance(), "手机号不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    insertPhone(mName, mPhone);
                }
                break;
            default:
        }
    }

    private void insertPhone(String mName, String mPhone) {
        List<UserBean> mPhones = new ArrayList<>();
        mPhones.add(new UserBean(mPhone, mName, new Date()));
        RoomDatabaseHepler.getDefault(MyApplication.getInstance()).getUserDao().insertAll(mPhones);
        insertNameEdit.setText("");
        insertPhoneEdit.setText("");
    }

    private void notifyItemChanged(final List<UserBean> userBeans) {
        mAdapter = new UserListAdapter(userBeans);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new UserListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UserBean userBean) {
                Bundle mBundle = new Bundle();
                mBundle.putParcelable("UserBean", userBean);
                EditDialog mDialog = new EditDialog();
                mDialog.show(MainActivity.this, mBundle, "edit");
                mDialog.setOnRefreshDataListener(new EditDialog.OnRefreshDataListener() {
                    @Override
                    public void onRefresh() {
                        notifyItemChanged(userBeans);
                    }
                });
            }
        });
    }
}