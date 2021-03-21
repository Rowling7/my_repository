package com.example.administrator.shopping;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.shopping.Impl.OnDelBtnClickListener;
import com.example.administrator.shopping.Impl.OnLessBtnClickListener;
import com.example.administrator.shopping.adapter.GoodsAdapter;
import com.example.administrator.shopping.adapter.ShoppingCartAdapter;
import com.example.administrator.shopping.dao.GoodsDao;
import com.example.administrator.shopping.dao.ShoppingCartDao;
import com.example.administrator.shopping.entity.GoodsEntity;
import com.example.administrator.shopping.entity.ShoppingCartEntity;
import com.example.administrator.shopping.utils.ToastUtils;

import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {

    /*跳转用*/
    private ImageView imgMy;
    private ImageView imgHome;
    private TextView tv_cartSum;
    private Button btn_settlement;
    private ImageView go_back;
    private Button btn_doAllCart;
    private Button btn_less;
    private Button btn_plus;
    private TextView tv_amount;

    /*购物车列表*/
    private ListView lv_cartList;
    private ShoppingCartDao shoppingCartDao;
    private List<ShoppingCartEntity> cartList;
    private ShoppingCartAdapter shoppingCartAdapter;
    private Handler mainHandler;     // 主线程
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            if (msg.what == 0) {
                String sum = (String) msg.obj;
                if (sum == null) {
                    tv_cartSum.setText("¥ 0");
                } else
                    tv_cartSum.setText("¥ " + sum);
            }
        }
    };
    private int count = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        SettingActivity.activityList.add(this);//用来退出应用


        Intent intent = getIntent();
        final String userNameForCart = intent.getStringExtra("passValueForCart");//MyActivity的传值

        tv_cartSum = findViewById(R.id.tv_cartSum);

        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgMy = findViewById(R.id.img_my);
        imgMy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //验证是否登录
                if (userNameForCart == null) {
                    ToastUtils.showMsg(ShoppingCartActivity.this, "未登录！即将跳转登陆");
                    Intent intent = new Intent(ShoppingCartActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ShoppingCartActivity.this, MyActivity.class);
                    intent.putExtra("passValue", userNameForCart);
                    startActivity(intent);
                }
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

        btn_settlement = findViewById(R.id.btn_settlement);
        btn_settlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ShoppingCartActivity.this, SettlementActivity.class);
                intent.putExtra("passValue", userNameForCart);
                startActivity(intent);
            }
        });

        btn_doAllCart = findViewById(R.id.btn_delAllCart);
        btn_doAllCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(ShoppingCartActivity.this)
                        .setTitle("清空购物车")
                        .setMessage("确认清空购物车吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doAllCart();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create().show();

            }
        });


        tv_amount = findViewById(R.id.tv_amount);
        btn_plus = (Button) findViewById(R.id.btn_plus);
       /* btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                tv_amount.setText(count+"");
            }
        });*/
        btn_less = findViewById(R.id.btn_less);
        initView();
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
                cartList = shoppingCartDao.getCartListById(userNameForCart);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showCartLvData();
                        doQueryCount();
                    }
                });
            }
        }).start();
    }

    //展示数据
    private void showCartLvData() {
        if (shoppingCartAdapter == null) {
            shoppingCartAdapter = new ShoppingCartAdapter(this, cartList);
            lv_cartList.setAdapter(shoppingCartAdapter);
        } else {
            shoppingCartAdapter.setShoppingCartList(cartList);
            shoppingCartAdapter.notifyDataSetChanged();
        }


        // 删除按钮的操作
        shoppingCartAdapter.setOnDelBtnClickListener(new OnDelBtnClickListener() {
            @Override
            public void onDelBtnClick(View view, int position) {
                //  删除方法
                final ShoppingCartEntity item = cartList.get(position);
                doDelCart(item.getUuid());
            }
        });
        // 删除按钮的操作
        shoppingCartAdapter.setOnLessBtnClickListener(new OnLessBtnClickListener() {
            @Override
            public void onLessBtnClick(View v, int position) {
                //  删除方法
                final ShoppingCartEntity item = cartList.get(position);
                doLessCart(item.getUuid());
            }

        });
    }

    //执行删除购物车中的数据
    private void doDelCart(final long uuid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final int iRow = ShoppingCartDao.delCart(uuid);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        loadCartDb();
                        doQueryCount();// 重新加载数据
                    }
                });
            }
        }).start();
    }

    //执行删除购物车中的数据
    private void doLessCart(final long uuid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final int iRow = ShoppingCartDao.doLessCart(uuid);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        loadCartDb();
                        doQueryCount();// 重新加载数据
                    }
                });
            }
        }).start();
    }

    // 查询购物车总金额
    public void doQueryCount() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                final String userNameForCart = intent.getStringExtra("passValueForCart");//MyActivity的传值
                String count = ShoppingCartDao.getCartSum(userNameForCart);
                Message msg = Message.obtain();
                msg.what = 0;   // 查询结果
                msg.obj = count;
                // 向主线程发送数据
                handler.sendMessage(msg);
            }
        }).start();
    }


    //清空购物车
    private void doAllCart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                final String userNameForCart = intent.getStringExtra("passValueForCart");//MyActivity的传值
                final int iRow = ShoppingCartDao.delAllCart(userNameForCart);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showMsg(ShoppingCartActivity.this, "已清空！");
                        loadCartDb();
                        doQueryCount();// 重新加载数据
                    }
                });
            }
        }).start();
    }

   /* public void doInsOrder() {
        new Thread(new Runnable() {
            Intent intent = getIntent();
            final String userNameForCart = intent.getStringExtra("passValueForCart");//MyActivity的传值

            @Override
            public void run() {
                ShoppingCartDao.insOrder(userNameForCart);


            }
        }).start();
    }*/

   /* public View.OnClickListener onclicklistener = new View.OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.btn_jia:
                    tv_amount.setText(Integer.valueOf(tv_amount.getText().toString()) + 1);
                    break;
                case R.id.btn_jian:
                    if (tv_amount.getText().toString().equals("1")) {
                        tv_amount.setText(1 + "");
                    } else {
                        tv_amount.setText(Integer.valueOf(tv_amount.getText().toString()) - 1);

                    }
                    break;

            }
        }
    };*/
}
