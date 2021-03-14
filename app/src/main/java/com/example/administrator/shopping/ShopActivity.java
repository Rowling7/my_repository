 package com.example.administrator.shopping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.shopping.database.SQLiteHelper;


public class ShopActivity extends Activity{

    private ImageView imgMy;
    private ImageView imgHome;

    private ListView mListView;
    SQLiteHelper mSQLiteHelper;
    String id;
    TextView Shopping_name,content;

    private String[] titles={
            "辣椒","圣女果","三华李", "罗汉果","糯玉米","大叶金钱草","五彩椒","葛根","武鸣沃柑","百香果","八角大料","木瓜丝干","腐竹","沙田柚子","大芋头"
                };
    private  String[] prices={
            "44.98元","52.90元","26.90元","28.80元","24.80元","29.80元","48.80元","20.00元","49.90元","21.80元","32.50元","16.50元","27.50元","69.90元","12.90元"
    };
    private int[] icons={
            R.drawable.shengnvguo,R.drawable.sanhuali,
            R.drawable.dayejinqiancao, R.drawable.nuoyumi,
            R.drawable.wucaijiao, R.drawable.gegen,
            R.drawable.wumingwogan, R.drawable.baixiangguo,
            R.drawable.bajiaodaliao, R.drawable.muguasigan,
            R.drawable.fuzhu, R.drawable.shatianyouzi,
            R.drawable.dayutou, R.drawable.lajiao,
            R.drawable.luohanguo,};
    private android.view.LayoutInflater LayoutInflater;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);


        SettingActivity.activityList.add(this);

        ImageView car=(ImageView) findViewById(R.id.img_cart);
        mListView=(ListView) findViewById(R.id.lv);
        MybaseAdapter mAdapter =new MybaseAdapter();//
        mSQLiteHelper = new SQLiteHelper(this);
        mListView.setAdapter(mAdapter);
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShopActivity.this,ShoplistActivity.class);
                startActivityForResult(intent,1);
            }
        });
        initData();


        imgMy = findViewById(R.id.img_my);
        imgMy.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(ShopActivity.this,MyActivity.class);
                startActivity(intent);
            }
        });
        imgHome = findViewById(R.id.img_home);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(ShopActivity.this,ShopActivity.class);
                startActivity(intent);
            }
        });

    }
    protected void initData(){
    }
    class MybaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return titles.length;
        }
        @Override
        public Object getItem(int position) {
            return titles[position];
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        class ViewHolder {
            TextView title, price;
            ImageView iv;
            Button addshop;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(ShopActivity.this, R.layout.list_item, null);
                holder = new ViewHolder();
                holder.title =  convertView.findViewById(R.id.title);
                holder.price = convertView.findViewById(R.id.price);
                holder.iv =  convertView.findViewById(R.id.iv);
                holder.addshop = convertView.findViewById(R.id.addshop);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.addshop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean n = mSQLiteHelper.insertData(titles[position],prices[position],icons[position]);
                    if(n){
                        Toast.makeText(ShopActivity.this,"加入购物车 成功",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ShopActivity.this,"加入购物车 失败",Toast.LENGTH_SHORT).show();
                    }

                }
            });
            holder.title.setText(titles[position]);
            holder.price.setText(prices[position]);
            holder.iv.setBackgroundResource(icons[position]);
            return convertView;
        }
    }
}
