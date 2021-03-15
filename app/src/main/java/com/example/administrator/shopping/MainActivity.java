package com.example.administrator.shopping;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.shopping.adapter.EntityUserAdapter;
import com.example.administrator.shopping.dao.EntityUserDao;
import com.example.administrator.shopping.dao.GoodsDao;
import com.example.administrator.shopping.entity.EntityUserEntity;
import com.example.administrator.shopping.entity.GoodsEntity;
import com.example.administrator.shopping.adapter.GoodsAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    /*跳转用*/
    private ImageView imgMy;
    private ImageView imgHome;
    private ImageView imgCart;

    private Handler handler;
    private TextView ivpicture;

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

        imgMy = findViewById(R.id.img_my);
        imgMy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyActivity.class);
                startActivity(intent);
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

        //跳转到sqlite购物车
       /* imgCart = findViewById(R.id.img_cart);
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MainActivity.this, ShoplistActivity.class);
                startActivity(intent);
            }
        });*/

        /*跳转到mysql购物车*/
        imgCart = findViewById(R.id.img_cart);
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(MainActivity.this, ShoppingCartActivity.class);
                intent.putExtra("passValueForCart", userNameForMain);
                startActivity(intent);
            }
        });

        /*填充列表*/
        lv_goods = findViewById(R.id.lv_goods);
        loadGoodsDb();


        // setupViews();//TextView显示图片
    }

   /* private void setupViews() {
        ivpicture = (TextView) findViewById(R.id.iv_picture);
        ivpicture.append(Html.fromHtml("<img src='" + R.drawable.lajiao+"'/>", imageGetter, null));
    }

    Html.ImageGetter imageGetter = new Html.ImageGetter() {

        @Override
        public Drawable getDrawable(String source) {
            int id = Integer.parseInt(source);
            Drawable drawable = getResources().getDrawable(id);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            return drawable;
        }
    };*/


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
    }


}