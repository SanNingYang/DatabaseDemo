package ysn.com.databasedemo.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import ysn.com.databasedemo.R;
import ysn.com.databasedemo.bean.UserBean;
import ysn.com.databasedemo.db.RoomDatabaseHepler;

public class EditDialog extends BaseDialog implements View.OnClickListener {

    private EditText mNameEdit, mPhoneEdit;
    private UserBean userBean;
    private OnRefreshDataListener mListener;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_edit_info;
    }

    @Override
    protected void initView(View view) {
        mNameEdit = view.findViewById(R.id.update_name_edit);
        mPhoneEdit = view.findViewById(R.id.update_phone_edit);
        view.findViewById(R.id.update_button).setOnClickListener(this);
        view.findViewById(R.id.delete_button).setOnClickListener(this);
    }

    @Override
    protected void loadData(Bundle bundle) {
        if (bundle != null) {
            userBean = bundle.getParcelable("UserBean");
            mNameEdit.setText(userBean.getName());
            mPhoneEdit.setText(userBean.getPhone());
        }
    }

    @Override
    public void onClick(View v) {
        String mName = mNameEdit.getText().toString();
        String mPhone = mPhoneEdit.getText().toString();
        switch (v.getId()) {
            case R.id.update_button:
                if (mName.isEmpty()) {
                    Toast.makeText(getActivity(), "姓名不能为空", Toast.LENGTH_SHORT).show();
                } else if (mPhone.isEmpty()) {
                    Toast.makeText(getActivity(), "手机号不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    updatePhone(mName, mPhone);
                }
                break;
            case R.id.delete_button:
                deletePhone();
                break;
            default:
        }
    }

    private void updatePhone(String name, String phone) {
        userBean.setName(name);
        userBean.setPhone(phone);
        userBean.setDate(new Date());
        RoomDatabaseHepler.getDefault(getActivity().getApplicationContext()).getUserDao().update(userBean);
        dismiss();
        if (mListener != null) {
            mListener.onRefresh();
        }
    }

    private void deletePhone() {
        RoomDatabaseHepler.getDefault(getActivity().getApplicationContext()).getUserDao().delete(userBean);
        dismiss();
        if (mListener != null) {
            mListener.onRefresh();
        }
    }

    public void setOnRefreshDataListener(OnRefreshDataListener listener) {
        this.mListener = listener;
    }

    public interface OnRefreshDataListener {
        void onRefresh();
    }
}
