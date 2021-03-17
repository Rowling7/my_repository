package com.example.administrator.shopping;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.shopping.Impl.OnInsBtnClickListener;
import com.example.administrator.shopping.adapter.GoodsAdapter;
import com.example.administrator.shopping.dao.GoodsDao;
import com.example.administrator.shopping.entity.GoodsEntity;
import com.example.administrator.shopping.utils.ToastUtils;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ImageView iv_reHome;
    private ImageView iv_search;
    private ListView lv_searchGoods;
    private Handler mainHandler;     // 主线程
    private EditText et_insNameFofSearch;

    /*商品列表*/
    private GoodsDao goodsDao;
    private List<GoodsEntity> goodsList;
    private GoodsAdapter goodsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        final String userNameForSearch = intent.getStringExtra("passValueForSearch");//MyActivity的传值
        Log.i("0", "地址数量：" + userNameForSearch);

        initView();
        lv_searchGoods = findViewById(R.id.lv_searchGoods);


        iv_reHome = findViewById(R.id.iv_retHome);
        iv_reHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                intent.putExtra("passValueForMain", userNameForSearch);
                startActivity(intent);
            }
        });

        iv_search = findViewById(R.id.iv_search);
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                final String userNameForSearch = intent.getStringExtra("passValueForSearch");//MyActivity的传值
                Log.i("0", "地址数量：" + userNameForSearch);
                loadSearchGoodsDb();
            }
        });
    }

    private void initView() {
        mainHandler = new Handler(getMainLooper());//获取主线程
        goodsDao = new GoodsDao();
    }
    /*填充商品列表*/

    private void loadSearchGoodsDb() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                et_insNameFofSearch=findViewById(R.id.et_insNameFofSearch);
                String etinsNameFofSearch = et_insNameFofSearch.getText().toString();
                goodsList = goodsDao.getSearchGoods(etinsNameFofSearch);
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
            lv_searchGoods.setAdapter(goodsAdapter);
        } else {
            goodsAdapter.setGoodsList(goodsList);
            goodsAdapter.notifyDataSetChanged();
        }

        // 添加按钮的操作
        goodsAdapter.setOnInsBtnClickListener(new OnInsBtnClickListener() {
            @Override
            public void OnInsBtnClick(View view, int position) {

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
                        final Intent intent = getIntent();
                        final String userNameForSearch = intent.getStringExtra("passValueForSearch");//MyActivity的传值
                        Log.i("0", "地址数量：" + userNameForSearch);
                        if (userNameForSearch==null){
                            new AlertDialog.Builder(SearchActivity.this)
                                    .setTitle("未登录，无法添加购物车！")
                                    .setMessage("是否前往登录？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent1=null;
                                            intent1= new Intent(SearchActivity.this,LoginActivity.class);
                                            startActivity(intent1);
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .create().show();
                        }else {
                            ToastUtils.showMsg(SearchActivity.this, "已添加至购物车！");
                           // loadSearchGoodsDb();// 重新加载数据
                        }
                    }
                });
            }
        }).start();
    }
}