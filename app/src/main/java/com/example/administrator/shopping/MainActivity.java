package com.example.administrator.shopping;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.administrator.shopping.Impl.OnInsBtnClickListener;
import com.example.administrator.shopping.dao.GoodsDao;
import com.example.administrator.shopping.entity.GoodsEntity;
import com.example.administrator.shopping.adapter.GoodsAdapter;
import com.example.administrator.shopping.utils.ToastUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    /*跳转用*/
    private ImageView imgMy;
    private ImageView imgHome;
    private ImageView imgCart;
    private Button btn_addshop;
    private Handler handler;
    private TextView ivpicture;
    private ImageView iv_search;

    /*商品列表*/
    private ListView lv_goods;
    private GoodsDao goodsDao;
    private List<GoodsEntity> goodsList;
    private GoodsAdapter goodsAdapter;

    private Handler mainHandler;     // 主线程


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SettingActivity.activityList.add(this);//用来退出应用
        initView();

        Intent intent = getIntent();
        final String userNameForMain = intent.getStringExtra("passValueForMain");//MyActivity的传值

        iv_search=findViewById(R.id.iv_search);
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("passValueForSearch", userNameForMain);
                startActivity(intent);
            }
        });


        imgMy = findViewById(R.id.img_my);
        imgMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //验证是否登录
                if (userNameForMain == null) {
                    ToastUtils.showMsg(MainActivity.this, "未登录！即将跳转登陆");
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, MyActivity.class);
                    intent.putExtra("passValue", userNameForMain);
                    startActivity(intent);
                }
            }
        });
        imgHome = findViewById(R.id.img_home);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        /*跳转到mysql购物车*/
        imgCart = findViewById(R.id.img_cart);
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MainActivity.this, ShoppingCartActivity.class);
                intent.putExtra("passValueForCart", userNameForMain);
                if (userNameForMain == null) {
                    ToastUtils.showMsg(MainActivity.this, "未登录 ");
                }
                startActivity(intent);
            }
        });

        /*填充列表*/
        lv_goods = findViewById(R.id.lv_goods);
        loadGoodsDb();

    }

    private void initView() {
        mainHandler = new Handler(getMainLooper());//获取主线程
        goodsDao = new GoodsDao();
    }

    /*填充商品列表*/
    private void loadGoodsDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                goodsList = goodsDao.getAllGoodsList();
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showLvData();
                    }
                });
            }
        }).start();
    }

    private void showLvData() {
        if (goodsAdapter == null) {
            goodsAdapter = new GoodsAdapter(this, goodsList);
            lv_goods.setAdapter(goodsAdapter);
        } else {
            goodsAdapter.setGoodsList(goodsList);
            goodsAdapter.notifyDataSetChanged();
        }

        // 添加按钮的操作
        goodsAdapter.setOnInsBtnClickListener(new OnInsBtnClickListener() {
            @Override
            public void OnInsBtnClick(View view, int position) {
                //  方法
                final GoodsEntity item = goodsList.get(position);
                doInsCart(item.getUuid());
            }
        });
    }

    //执行添加购物车中的数据
    private void doInsCart(final String uuid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                final String username = intent.getStringExtra("passValueForMain");//MyActivity的传值
                final int iRow = GoodsDao.insCart(uuid, username);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showMsg(MainActivity.this,"已添加至购物车！");
                        loadGoodsDb();// 重新加载数据
                    }
                });
            }
        }).start();
    }

}