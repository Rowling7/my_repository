package com.example.administrator.shopping.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.shopping.R;
import com.example.administrator.shopping.SettlementActivity;
import com.example.administrator.shopping.entity.ShoppingCartEntity;

import java.util.List;

public class SettlementAdapter extends BaseAdapter {


    private final Context context;//上下文信息 谁是操作源对象
    private List<ShoppingCartEntity> settlementList;//商品的数据集合

    private class ViewHolder {
        private TextView title;
        private TextView price;
        private TextView tv_amount;
        private ImageView iv_img;
    }

    public static final String TAG = "OUTPUT";

    public SettlementAdapter(Context context, List<ShoppingCartEntity> settlementList) {
        this.context = context;
        this.settlementList = settlementList;
        Log.i(TAG, "数量：" + settlementList.size());
    }

    public void setSettlementList(List<ShoppingCartEntity> settlementList) {
        this.settlementList = settlementList;
    }

    @Override
    public int getCount() {
        return settlementList.size();
    }

    @Override
    public Object getItem(int position) {
        return settlementList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        SettlementAdapter.ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.settlement_list_item, null);
            viewHolder = new ViewHolder();

            viewHolder.iv_img = convertView.findViewById(R.id.iv_img);
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.price = convertView.findViewById(R.id.price);
            viewHolder.tv_amount = convertView.findViewById(R.id.tv_amount);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SettlementAdapter.ViewHolder) convertView.getTag();
        }

        //数据填充
        ShoppingCartEntity item = settlementList.get(position);
        viewHolder.title.setText(item.getName());
        viewHolder.price.setText(item.getPrice());
        viewHolder.tv_amount.setText(item.getNumber());

        byte[] imageBytes = Base64.decode(item.getPicture(), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        viewHolder.iv_img.setImageBitmap(decodedImage);

        return convertView;
    }
}
