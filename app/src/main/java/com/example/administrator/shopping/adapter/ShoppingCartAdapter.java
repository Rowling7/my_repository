package com.example.administrator.shopping.adapter;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.shopping.Impl.OnDelBtnClickListener;
import com.example.administrator.shopping.Impl.OnLessBtnClickListener;
import com.example.administrator.shopping.Impl.OnPlusBtnClickListener;
import com.example.administrator.shopping.R;
import com.example.administrator.shopping.entity.ShoppingCartEntity;

import java.util.List;

//自定义购物车适配器

public class ShoppingCartAdapter extends BaseAdapter {

    private final Context context;//上下文信息 谁是操作源对象
    private List<ShoppingCartEntity> cartList;//商品的数据集合

    private class ViewHolder {
        private TextView title;
        private TextView price;
        private Button btn_delCart;
        private Button btn_plus;
        private Button btn_less;
        private TextView tv_amount;
    }

    public static final String TAG = "OUTPUT";
    //删除按钮点击事件的监听实例
    private OnDelBtnClickListener onDelBtnClickListener;
    private OnPlusBtnClickListener onPlusBtnClickListener;
    private OnLessBtnClickListener onLessBtnClickListener;

    public void setOnDelBtnClickListener(OnDelBtnClickListener onDelBtnClickListener) {
        this.onDelBtnClickListener = onDelBtnClickListener;
    }

    public void setOnPlusBtnClickListener(OnPlusBtnClickListener onPlusBtnClickListener) {
        this.onDelBtnClickListener = onDelBtnClickListener;
    }

    public void setOnLessBtnClickListener(OnLessBtnClickListener onLessBtnClickListener) {
        this.onLessBtnClickListener = onLessBtnClickListener;
    }


    public ShoppingCartAdapter(Context context, List<ShoppingCartEntity> cartList) {
        this.context = context;
        this.cartList = cartList;
        Log.i(TAG, "购物车数量：" + cartList.size());
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ShoppingCartAdapter.ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.shoppingcart_list_item, null);
            viewHolder = new ViewHolder();

            // viewHolder.iv_picture = convertView.findViewById(R.id.iv_picture);
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.price = convertView.findViewById(R.id.price);
            viewHolder.btn_less = convertView.findViewById(R.id.btn_less);
            viewHolder.btn_plus = convertView.findViewById(R.id.btn_plus);
            viewHolder.tv_amount = convertView.findViewById(R.id.tv_amount);
            // viewHolder.btn_addshop = convertView.findViewById(R.id.btn_addshop);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ShoppingCartAdapter.ViewHolder) convertView.getTag();
        }

        //数据填充
        ShoppingCartEntity item = cartList.get(position);
        viewHolder.title.setText(item.getName());
        viewHolder.price.setText(item.getPrice());
        viewHolder.tv_amount.setText(item.getNumber());


        // 删除按钮
        viewHolder.btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelBtnClickListener.onDelBtnClick(v, position);
            }
        });
        /*viewHolder.btn_less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelBtnClickListener.onDelBtnClick(v, position);
            }
        });*/
        /* viewHolder.btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlusBtnClickListener.onPlusBtnClick(v, position);
            }
        });*/
        viewHolder.btn_less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLessBtnClickListener.onLessBtnClick(v, position);
            }
        });
        return convertView;
    }


}
