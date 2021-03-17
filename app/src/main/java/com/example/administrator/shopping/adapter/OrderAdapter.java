package com.example.administrator.shopping.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.shopping.Impl.OnInsBtnClickListener;
import com.example.administrator.shopping.R;
import com.example.administrator.shopping.entity.OrderEntity;

import java.util.List;

public class OrderAdapter extends BaseAdapter {


    private class ViewHolder {


        private TextView tv_goodsPrice;
        private TextView tv_goodsCount;
        private Button btn_delorder;
        private Button btn_gopay;
    }

    private Context context;//上下文信息 谁是操作源对象
    private List<OrderEntity> orderList;//商品的数据集合


    public final String TAG = "OUTPUT";
    private OnInsBtnClickListener onInsBtnClickListener;     //添加按钮点击事件的监听实例

    public OrderAdapter() {

    }

    public void setOnInsBtnClickListener(OnInsBtnClickListener onInsBtnClickListener) {
        this.onInsBtnClickListener = onInsBtnClickListener;
    }

    public OrderAdapter(Context context, List<OrderEntity> orderList) {
        this.context = context;
        this.orderList = orderList;
        Log.i(TAG, "商品数量：" + orderList.size());

    }

    public void setOrderList(List<OrderEntity> goodsList) {
        this.orderList = goodsList;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.notpay_list_item, null);
            viewHolder = new ViewHolder();

            //viewHolder.iv_picture = convertView.findViewById(R.id.iv_picture);//1/2
            viewHolder.tv_goodsCount = convertView.findViewById(R.id.tv_goodsCount);
            viewHolder.tv_goodsPrice = convertView.findViewById(R.id.tv_goodsPrice);
            viewHolder.btn_delorder = convertView.findViewById(R.id.btn_delorder);
            viewHolder.btn_gopay = convertView.findViewById(R.id.btn_gopay);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //数据填充
        OrderEntity orders = orderList.get(position);
        viewHolder.tv_goodsCount.setText(orders.getGoodscount());
        viewHolder.tv_goodsPrice.setText(orders.getGoodsprice());
        // viewHolder.iv_picture.setBackgroundResource(goods.getPicture());2/2

        viewHolder.btn_delorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onInsBtnClickListener.OnInsBtnClick(v, position);
            }
        });

        return convertView;
    }

}
