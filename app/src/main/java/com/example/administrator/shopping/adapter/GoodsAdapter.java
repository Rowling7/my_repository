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

import com.example.administrator.shopping.Impl.OnDelBtnClickListener;
import com.example.administrator.shopping.Impl.OnEditBtnClickListener;
import com.example.administrator.shopping.Impl.OnInsBtnClickListener;
import com.example.administrator.shopping.entity.GoodsEntity;
import com.example.administrator.shopping.R;

import java.util.List;

//自定义商品适配器

public class GoodsAdapter extends BaseAdapter {

    private class ViewHolder {
        private TextView tv_title;
        private TextView tv_price;
        private TextView tv_price2;
        private ImageView iv_picture;
        private Button btn_addshop;
    }

    private Context context;//上下文信息 谁是操作源对象
    private List<GoodsEntity> goodsList;//商品的数据集合



    public final String TAG= "OUTPUT";
    private OnInsBtnClickListener onInsBtnClickListener;     //添加按钮点击事件的监听实例

    public GoodsAdapter() {

    }

    public void setOnInsBtnClickListener(OnInsBtnClickListener onInsBtnClickListener) {
        this.onInsBtnClickListener = onInsBtnClickListener;
    }

    public GoodsAdapter(Context context, List<GoodsEntity> goodsList) {
        this.context = context;
        this.goodsList = goodsList;
        Log.i(TAG, "商品数量：" + goodsList.size());

    }

    public void setGoodsList(List<GoodsEntity> goodsList) {
        this.goodsList = goodsList;
    }

    @Override
    public int getCount() {
        return goodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.goods_list_item, null);
            viewHolder = new ViewHolder();

            //viewHolder.iv_picture = convertView.findViewById(R.id.iv_picture);//1/2
            viewHolder.tv_title = convertView.findViewById(R.id.tv_title);
            viewHolder.tv_price2 = convertView.findViewById(R.id.tv_price2);
            viewHolder.btn_addshop = convertView.findViewById(R.id.btn_addshop);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //数据填充
        GoodsEntity goods = goodsList.get(position);
        viewHolder.tv_title.setText(goods.getName());
        viewHolder.tv_price2.setText(goods.getPrice());
        // viewHolder.iv_picture.setBackgroundResource(goods.getPicture());2/2

        viewHolder.btn_addshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onInsBtnClickListener.OnInsBtnClick(v, position);
            }
        });

        return convertView;
    }

}
