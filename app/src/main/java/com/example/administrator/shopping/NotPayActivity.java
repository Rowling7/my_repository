package com.example.administrator.shopping;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.shopping.Impl.OnDelBtnClickListener;
import com.example.administrator.shopping.Impl.OnInsBtnClickListener;
import com.example.administrator.shopping.adapter.NotPayAdapter;
import com.example.administrator.shopping.dao.EntityUserDao;
import com.example.administrator.shopping.dao.GoodsDao;
import com.example.administrator.shopping.dao.OrderDao;
import com.example.administrator.shopping.dao.ShoppingCartDao;
import com.example.administrator.shopping.entity.OrderEntity;
import com.example.administrator.shopping.entity.ShoppingCartEntity;
import com.example.administrator.shopping.utils.ToastUtils;

import java.util.List;

public class NotPayActivity extends AppCompatActivity {

    private ImageView go_back;
    private Button btn_payOrder;
    private Button btn_delOrder;
    private TextView tvprice;

    /*商品列表*/
    private ListView lv_orderList;
    private OrderDao orderDao;
    private List<OrderEntity> orderList;
    private NotPayAdapter notPayAdapter;

    private Handler mainHandler;     // 主线程

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notpay);
        SettingActivity.activityList.add(this);//用来退出应用
        initView();


        btn_delOrder = findViewById(R.id.btn_delOrder);
        btn_payOrder = findViewById(R.id.btn_payOrder);
        tvprice = findViewById(R.id.price);


        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                final String userName = intent.getStringExtra("passValue");//登陆后的传值
                String numbForNoReceived = EntityUserDao.getNumbForReceived(userName);
                String numbForNopay = EntityUserDao.getNumbForNopay(userName);
                intent = new Intent(NotPayActivity.this, MyActivity.class);
                intent.putExtra("passValue", userName);
                intent.putExtra("numbForNoReceived", numbForNoReceived);
                intent.putExtra("numbForNopay", numbForNopay);
                startActivity(intent);

                //finish();
            }
        });
        //Intent intent = getIntent();
        // final String userNameForMain = intent.getStringExtra("passValue");//MyActivity的传值

        /*填充列表*/
        lv_orderList = findViewById(R.id.lv_orderList);
        loadOrderDb();

    }

    private void initView() {
        mainHandler = new Handler(getMainLooper());//获取主线程
        orderDao = new OrderDao();

    }


    /*填充商品列表*/
    private void loadOrderDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                final String username = intent.getStringExtra("passValue");//登陆后的传值
                orderList = orderDao.getNotPayOrderList(username);
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
        if (notPayAdapter == null) {
            notPayAdapter = new NotPayAdapter(this, orderList);
            lv_orderList.setAdapter(notPayAdapter);
        } else {
            notPayAdapter.setOrderList(orderList);
            notPayAdapter.notifyDataSetChanged();
        }


        notPayAdapter.setOnDelBtnClickListener(new OnDelBtnClickListener() {
            @Override
            public void onDelBtnClick(View view, int position) {
                final OrderEntity item = orderList.get(position);
                doDelOrder(item.getUuid());
            }
        });

        notPayAdapter.setOnInsBtnClickListener(new OnInsBtnClickListener() {
            @Override
            public void OnInsBtnClick(View view, int position) {
                final OrderEntity item = orderList.get(position);
                doInsOrder(item.getUuid());
            }

        });


    }

    //执行删除购物车中的数据
    private void doDelOrder(final long uuid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final int iRow = OrderDao.delOrder(uuid);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showMsg(NotPayActivity.this, "已取消订单");
                        loadOrderDb();// 重新加载数据
                    }
                });
            }
        }).start();
    }


    private void doInsOrder(final long uuid) {
        tvprice = findViewById(R.id.price);
        final String goodsPrice = tvprice.getText().toString().trim();
        final Intent intent = getIntent();
        final String username = intent.getStringExtra("passValue");//登陆后的传值
        new Thread(new Runnable() {
            @Override
            public void run() {
                final int iRow = OrderDao.payOrder(uuid);
                Looper.prepare();
                final PayPasswordDialog dialog = new PayPasswordDialog(NotPayActivity.this, R.style.mydialog);
                dialog.setDialogClick(new PayPasswordDialog.DialogClick() {
                    @Override
                    public void doConfirm(String password) {
                        dialog.dismiss();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String isnull = EntityUserDao.confWallet(goodsPrice, username);
                                if (isnull.equals(0 + "")) {
                                    Intent intent = null;
                                    // EntityUserDao.reConfWallet(goodsPrice, username);
                                    intent = new Intent(NotPayActivity.this, WalletActivity.class);
                                    intent.putExtra("passValue", username);
                                    startActivity(intent);
                                    ToastUtils.showMsg(NotPayActivity.this, "余额不足无法完成支付！");
                                } else if (isnull.equals(1 + "")) {
                                    // OrderDao.updateStatus(username);//更新订单
                                    EntityUserDao.updateWallet(goodsPrice, username);//更新钱包余额
                                    ToastUtils.showMsg(NotPayActivity.this, "支付完成,可在待收货中查看");
                                    ShoppingCartDao.delAllCart(username);//清空购物车
                                    GoodsDao.updateNumber();//更新goods数量
                                    loadOrderDb();
                                }
                            }
                        }).start();

                    }
                });
                dialog.show();
                Looper.loop();


            }
        }).start();
    }

}