package com.example.administrator.shopping;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.shopping.adapter.GoodsAdapter;
import com.example.administrator.shopping.adapter.ShoppingCartAdapter;
import com.example.administrator.shopping.dao.GoodsDao;
import com.example.administrator.shopping.dao.ShoppingCartDao;
import com.example.administrator.shopping.entity.ShoppingCartEntity;

import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {

    /*跳转用*/
    private ImageView imgMy;
    private ImageView imgHome;
    private ImageView imgCart;

    private Handler handler;

    /*购物车列表*/
    private ListView lv_cartList;
    private ShoppingCartDao shoppingCartDao;
    private List<ShoppingCartEntity> cartList;
    private ShoppingCartAdapter shoppingCartAdapter;
    private Handler mainHandler;     // 主线程


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        SettingActivity.activityList.add(this);//用来退出应用
        initView();
        Intent intent = getIntent();
        final String userNameForCart = intent.getStringExtra("passValueForCart");//MyActivity的传值

        imgMy = findViewById(R.id.img_my);
        imgMy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingCartActivity.this, MyActivity.class);
                intent.putExtra("passValue", userNameForCart);
                startActivity(intent);
            }
        });
        imgHome = findViewById(R.id.img_home);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingCartActivity.this, MainActivity.class);
                intent.putExtra("passValueForMain", userNameForCart);
                startActivity(intent);
            }
        });

      /*  imgCart = findViewById(R.id.img_cart);
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(ShoppingCartActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
            }
        });*/

        /*填充列表*/
        lv_cartList = findViewById(R.id.lv_cartList);
        loadCartDb();

    }

    private void initView() {
        mainHandler = new Handler(getMainLooper());//获取主线程
        shoppingCartDao = new ShoppingCartDao();
    }

    /*填充商品列表*/
    private void loadCartDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                final String userNameForCart = intent.getStringExtra("passValueForCart");//MyActivity的传值
                cartList=shoppingCartDao.getCartListById(userNameForCart);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showCartLvData();
                    }
                });
            }
        }).start();
    }

    private void showCartLvData() {
        if (shoppingCartAdapter == null) {
            shoppingCartAdapter = new ShoppingCartAdapter(this, cartList);
            lv_cartList.setAdapter(shoppingCartAdapter);
        } else {
            shoppingCartAdapter.setShoppingCartList(cartList);
            shoppingCartAdapter.notifyDataSetChanged();
        }
    }


}