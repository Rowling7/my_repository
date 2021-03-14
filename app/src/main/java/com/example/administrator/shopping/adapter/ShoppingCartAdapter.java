package com.example.administrator.shopping.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.shopping.Entity.GoodsEntity;
import com.example.administrator.shopping.Entity.ShoppingCartEntity;
import com.example.administrator.shopping.R;

import java.util.List;

public class ShoppingCartAdapter   extends BaseAdapter {

    private Context context;//上下文信息 谁是操作源对象
    private List<ShoppingCartEntity> cartList;//商品的数据集合

    public ShoppingCartAdapter(Context context, List<ShoppingCartEntity> cartList) {
        this.context = context;
        this.cartList = cartList;
        Log.i("数据适配器", "数量：" + cartList.size());
    }

    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ShoppingCartAdapter.ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.goods_list_item, null);
           // viewHolder = new ShoppingCartAdapter().ViewHolder();

            // viewHolder.iv_picture = convertView.findViewById(R.id.iv_picture);
            viewHolder.tv_title = convertView.findViewById(R.id.tv_title);
            viewHolder.tv_price2 = convertView.findViewById(R.id.tv_price2);
            viewHolder.btn_delCart = convertView.findViewById(R.id.btn_delCart);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ShoppingCartAdapter.ViewHolder) convertView.getTag();
        }

        //数据填充
        ShoppingCartEntity cart = cartList.get(position);
       // viewHolder.tv_title.setText(cart.getName());
        //viewHolder.tv_price.setText(goods.get());
       // viewHolder.tv_price2.setText(cart.getPrice());
        // viewHolder.iv_picture.setBackgroundResource(goods.getPicture());


        return convertView;
    }

    private class ViewHolder {
        private TextView tv_title;
        private TextView tv_price2;
        private ImageView iv_picture;
        private Button btn_delCart;
    }
}

