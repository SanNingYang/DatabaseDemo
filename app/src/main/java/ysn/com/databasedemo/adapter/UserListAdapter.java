package ysn.com.databasedemo.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ysn.com.databasedemo.R;
import ysn.com.databasedemo.bean.UserBean;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private List<UserBean> userBeans;
    private OnItemClickListener mListener;

    public UserListAdapter(List<UserBean> userBeans) {
        this.userBeans = userBeans;
        notifyItemChanged(userBeans.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phone_text, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        UserBean userBean = userBeans.get(position);
        holder.mPhoneTx.setText(getFormatContent(userBean.getName(), userBean.getPhone(), userBean.getDate(), userBean.getAge()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(userBeans.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userBeans == null ? 0 : userBeans.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    private String getFormatContent(String name, String phone, Date date, int age) {
        StringBuilder mBuilder = new StringBuilder();
        mBuilder.append("昵称:");
        mBuilder.append(name);
        mBuilder.append("\n手机:");
        mBuilder.append(phone);
        mBuilder.append("\n年龄:");
        mBuilder.append(age);
        mBuilder.append("\n日期:");
        mBuilder.append(getFormatDate(date));
        return mBuilder.toString();
    }

    private String getFormatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(date);
    }

    public interface OnItemClickListener {
        void onItemClick(UserBean userBean);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mPhoneTx;

        private ViewHolder(View itemView) {
            super(itemView);
            mPhoneTx = itemView.findViewById(R.id.phone_number_text);
        }
    }
}
