package com.example.administrator.shopping.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.shopping.Impl.OnDelBtnClickListener;
import com.example.administrator.shopping.Impl.OnInsBtnClickListener;
import com.example.administrator.shopping.Impl.OnLessBtnClickListener;
import com.example.administrator.shopping.Impl.OnPlusBtnClickListener;
import com.example.administrator.shopping.R;
import com.example.administrator.shopping.entity.OrderEntity;

import java.util.List;

//自定义商品适配器

public class StarAdapter extends BaseAdapter {

    private class ViewHolder {
        private TextView tv_title;
        private TextView tv_price;
        private Button btn_delOrder;
        private Button btn_payOrder;

    }

    private Context context;//上下文信息 谁是操作源对象
    private List<OrderEntity> orderList;//商品的数据集合


    public final String TAG = "OUTPUT";
    private OnInsBtnClickListener onInsBtnClickListener;     //添加按钮点击事件的监听实例
    private OnDelBtnClickListener onDelBtnClickListener;
    private OnPlusBtnClickListener onPlusBtnClickListener;
    private OnLessBtnClickListener onLessBtnClickListener;

    public StarAdapter() {

    }

    public void setOnInsBtnClickListener(OnInsBtnClickListener onInsBtnClickListener) {
        this.onInsBtnClickListener = onInsBtnClickListener;
    }

    public void setOnDelBtnClickListener(OnDelBtnClickListener onDelBtnClickListener) {
        this.onDelBtnClickListener = onDelBtnClickListener;
    }

    public StarAdapter(Context context, List<OrderEntity> orderList) {
        this.context = context;
        this.orderList = orderList;
        Log.i(TAG, "商品数量：" + orderList.size());

    }

    public void setOrderList(List<OrderEntity> orderList) {
        this.orderList = orderList;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.received_list_item, null);
            viewHolder = new ViewHolder();

            //viewHolder.iv_picture = convertView.findViewById(R.id.iv_picture);//1/2
            viewHolder.tv_title = convertView.findViewById(R.id.title);
            viewHolder.tv_price = convertView.findViewById(R.id.price);
            //viewHolder.btn_payOrder = convertView.findViewById(R.id.btn_payOrder);
            //viewHolder.btn_delOrder = convertView.findViewById(R.id.btn_delorder);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //数据填充
        OrderEntity orders = orderList.get(position);
        viewHolder.tv_title.setText(orders.getUuid() + "");
        viewHolder.tv_price.setText(orders.getGoodsprice());
        // viewHolder.iv_picture.setBackgroundResource(goods.getPicture());2/2

        /*viewHolder.btn_addshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onInsBtnClickListener.OnInsBtnClick(v, position);
            }
        });*/

        return convertView;
    }

}
