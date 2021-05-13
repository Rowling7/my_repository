package com.example.administrator.shopping.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.shopping.R;
import com.example.administrator.shopping.entity.OrderEntity;

import java.util.List;

public class HisWalletAdapter extends BaseAdapter {

    private class ViewHolder {
        private TextView tv_time2;
        private TextView tv_goodsPrice2;
    }

    private Context context;//上下文信息 谁是操作源对象
    private List<OrderEntity> hisWalletList;//商品的数据集合

    public static final String TAG = "OUTPUT";

    public HisWalletAdapter(Context context, List<OrderEntity> hisWalletList) {
        this.context = context;
        this.hisWalletList = hisWalletList;
        Log.i(TAG, "数量：" + hisWalletList.size());
    }

    public void setHisWalletList(List<OrderEntity> hisWalletList) {
        this.hisWalletList = hisWalletList;
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.hiswallet_list_item, null);
            viewHolder = new HisWalletAdapter.ViewHolder();

            viewHolder.tv_time2 = convertView.findViewById(R.id.tv_time2);
            viewHolder.tv_goodsPrice2 = convertView.findViewById(R.id.tv_goodsPrice2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HisWalletAdapter.ViewHolder) convertView.getTag();
        }

        //数据填充
        OrderEntity item = hisWalletList.get(position);
        viewHolder.tv_time2.setText(item.getDatetime());
        viewHolder.tv_goodsPrice2.setText(item.getGoodsprice());

        return convertView;
    }
}
