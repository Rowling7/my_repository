package com.example.administrator.shopping;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.shopping.dao.EntityUserDao;
import com.example.administrator.shopping.dao.GoodsDao;
import com.example.administrator.shopping.utils.ToastUtils;

public class GoodsDetailActivity extends AppCompatActivity {

    private ImageView img_goodsDetails;
    private TextView tv_detalisName;
    private TextView tv_detalisPrice;
    private TextView tv_place;
    private TextView tv_description;
    private Button btn_addshop;
    private ImageView go_back;
    private GoodsDao goodsDao;
    private Handler mainHandler;     // 主线程

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0) {
                String name = (String) msg.obj;
                tv_detalisName.setText(name);
            } else if (msg.what == 1) {
                String price = (String) msg.obj;
                tv_detalisPrice.setText(price);
            } else if (msg.what == 2) {
                String place = (String) msg.obj;
                tv_place.setText(place);
            } else if (msg.what == 3) {
                String description = (String) msg.obj;
                tv_description.setText(description);
            }
        }
    };


    public static final String TAG = "GoodsDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);

        SettingActivity.activityList.add(this);//用来退出应用

        initView();
        Intent intent = getIntent();
        final String passValue = intent.getStringExtra("passValue");//MyActivity的传值
        final String username = intent.getStringExtra("username");

        btn_addshop = findViewById(R.id.btn_addshop);
        btn_addshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goodsDao.updateNumber();
                doaddShop();
            }
        });
        tv_description = findViewById(R.id.tv_description);
        tv_place = findViewById(R.id.tv_place);
        tv_detalisPrice = findViewById(R.id.tv_detalisPrice);
        tv_detalisName = findViewById(R.id.tv_detalisName);

        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(GoodsDetailActivity.this, MainActivity.class);
                intent.putExtra("passValue", passValue);
                startActivity(intent);*/
                finish();
            }
        });

        doGoodsPicture();
        selectGoodsDescription();
        selectGoodsName();
        selectGoodsPlace();
        selectGoodsPrice();
    }

    private void initView() {
        mainHandler = new Handler(getMainLooper());//获取主线程
    }

    public void doGoodsPicture() {
        img_goodsDetails = findViewById(R.id.img_goodsDetails);
        Intent intent = getIntent();
        final String uuid = intent.getStringExtra("passValue");//登陆后的传值
        String imgGoods = GoodsDao.getGoodsPicture(uuid);
        byte[] imageBytes = Base64.decode(imgGoods, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        img_goodsDetails.setImageBitmap(decodedImage);

    }

    public void selectGoodsName() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                final String uuid = intent.getStringExtra("passValue");//MyActivity的传值
                String name = GoodsDao.getGoodsName(uuid);
                Message msg = Message.obtain();
                msg.what = 0;   // 查询结果
                msg.obj = name;
                // 向主线程发送数据
                handler.sendMessage(msg);
            }
        }).start();
    }

    public void selectGoodsPrice() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                final String uuid = intent.getStringExtra("passValue");//MyActivity的传值
                String price = GoodsDao.getGoodsPrice(uuid);
                Message msg = Message.obtain();
                msg.what = 1;   // 查询结果
                msg.obj = price;
                // 向主线程发送数据
                handler.sendMessage(msg);
            }
        }).start();
    }

    public void selectGoodsPlace() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                final String uuid = intent.getStringExtra("passValue");//MyActivity的传值
                String place = GoodsDao.getGoodsPlace(uuid);
                Message msg = Message.obtain();
                msg.what = 2;   // 查询结果
                msg.obj = place;
                // 向主线程发送数据
                handler.sendMessage(msg);
            }
        }).start();
    }

    public void selectGoodsDescription() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                final String uuid = intent.getStringExtra("passValue");//MyActivity的传值
                String description = GoodsDao.getGoodsDescription(uuid);
                Message msg = Message.obtain();
                msg.what = 3;   // 查询结果
                msg.obj = description;
                // 向主线程发送数据
                handler.sendMessage(msg);
            }
        }).start();
    }

    public void doaddShop() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                final String uuid = intent.getStringExtra("passValue");
                final String username = intent.getStringExtra("username");
                int iRow = GoodsDao.insCart(uuid, username);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (username == null) {
                            ToastUtils.showMsg(GoodsDetailActivity.this, "未登录哦~");

                        } else {
                            ToastUtils.showMsg(GoodsDetailActivity.this, "已添加至购物车！");
                        }
                    }
                });
            }
        }).start();
    }


}