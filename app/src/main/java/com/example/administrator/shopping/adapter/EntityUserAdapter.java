/*
package com.example.administrator.shopping.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.shopping.AddressActivity;
import com.example.administrator.shopping.Impl.OnDelBtnClickListener;
import com.example.administrator.shopping.Impl.OnEditBtnClickListener;
import com.example.administrator.shopping.LoginActivity;
import com.example.administrator.shopping.R;
import com.example.administrator.shopping.entity.EntityUserEntity;
import com.example.administrator.shopping.utils.ToastUtils;

import java.util.List;

public class EntityUserAdapter  extends  BaseAdapter{
    private TextView tv_address;
    private final Context context;//上下文信息 谁是操作源对象
    private List<EntityUserEntity> addressList;//地址的数据集合

    private OnEditBtnClickListener onEditBtnClickListener;   //修改按钮点击事件的监听实例
    private OnDelBtnClickListener onDelBtnClickListener;     //删除按钮点击事件的监听实例

    public void setOnEditBtnClickListener(OnEditBtnClickListener onEditBtnClickListener) {
        this.onEditBtnClickListener = onEditBtnClickListener;
    }

    public void setOnDelBtnClickListener(OnDelBtnClickListener onDelBtnClickListener) {
        this.onDelBtnClickListener = onDelBtnClickListener;
    }

    public EntityUserAdapter(Context context, List<EntityUserEntity> addressList) {
        this.context = context;
        this.addressList = addressList;
        Log.i("0", "地址数量：" + addressList.size());

int aSize = addressList.size();
        if (aSize==0){
            ToastUtils.showMsg(AddressActivity.this,"无地址");


        }

    }

    public void setAddressList(List<EntityUserEntity> addressList) {
        this.addressList = addressList;
    }

    @Override
    public int getCount() {
        return addressList.size();
    }

    @Override
    public Object getItem(int position) {
        return addressList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        EntityUserEntity.ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.address_list_item, null);
            viewHolder = new OrderAdapter.ViewHolder();
            viewHolder.tv_address = convertView.findViewById(R.id.tv_address);
            viewHolder.iv_edit = convertView.findViewById(R.id.iv_editAddress);
            // viewHolder.iv_del = convertView.findViewById(R.id.iv_del);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (EntityUserAdapter.ViewHolder) convertView.getTag();
        }

        //数据填充
        EntityUserEntity address = addressList.get(position);
        viewHolder.tv_address.setText(address.getAddress());

        //修改
        viewHolder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEditBtnClickListener.onEditBtnClick(view, position);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView tv_address;
        private ImageView iv_edit;
        // private ImageView iv_del;
    }
}



*/
