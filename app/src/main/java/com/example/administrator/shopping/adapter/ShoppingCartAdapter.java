package com.example.administrator.shopping.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.administrator.shopping.R;
import com.example.administrator.shopping.entity.ShoppingCartEntity;
import java.util.List;

//自定义购物车适配器

public class ShoppingCartAdapter extends BaseAdapter {

    private Context context;//上下文信息 谁是操作源对象
    private List<ShoppingCartEntity> cartList;//商品的数据集合

    public ShoppingCartAdapter(Context context, List<ShoppingCartEntity> cartList) {
        this.context = context;
        this.cartList = cartList;
        Log.i("数据适配器", "购物车数量：" + cartList.size());
    }
    public void setShoppingCartList(List<ShoppingCartEntity> cartList) {
        this.cartList = cartList;
    }

    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ShoppingCartAdapter.ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.shoppingcart_list_item, null);
            viewHolder = new ViewHolder();

            // viewHolder.iv_picture = convertView.findViewById(R.id.iv_picture);
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.price = convertView.findViewById(R.id.price);
            // viewHolder.btn_addshop = convertView.findViewById(R.id.btn_addshop);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ShoppingCartAdapter.ViewHolder) convertView.getTag();
        }

        //数据填充
        ShoppingCartEntity item = cartList.get(position);
        viewHolder.title.setText(item.getName());
        viewHolder.price.setText(item.getPrice());

        return convertView;
    }
    private class ViewHolder {
        private TextView title;
        private TextView price;

    }

}
