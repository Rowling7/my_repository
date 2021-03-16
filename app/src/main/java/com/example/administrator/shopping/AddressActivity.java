package com.example.administrator.shopping;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.shopping.Impl.OnDelBtnClickListener;
import com.example.administrator.shopping.Impl.OnEditBtnClickListener;
import com.example.administrator.shopping.adapter.AddressAdapter;
import com.example.administrator.shopping.adapter.EntityUserAdapter;
import com.example.administrator.shopping.dao.AddressDao;
import com.example.administrator.shopping.dao.EntityUserDao;
import com.example.administrator.shopping.dao.GoodsDao;
import com.example.administrator.shopping.entity.AddressEntity;
import com.example.administrator.shopping.entity.EntityUserEntity;
import com.example.administrator.shopping.utils.ToastUtils;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.List;


public class AddressActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView tv_userName;
    private Handler handler;
    /*地址列表*/
    private ListView lv_address;
    private AddressDao addressDao;
    private List<AddressEntity> addressList;
    private AddressAdapter addressAdapter;

    private Button btn_insAddress;
    private Button iv_editAddress;
    private Button iv_delAddress;
    private Handler mainHandler;     // 主线程


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        SettingActivity.activityList.add(this);

        Intent intent = getIntent();
        final String userNameForInAd = intent.getStringExtra("passValue2");//登陆后的传值

        lv_address = findViewById(R.id.lv_address);
        loadAddress();
        initView();

        iv_delAddress = findViewById(R.id.iv_delAddress);
        iv_editAddress = findViewById(R.id.iv_editAddress);

        //添加地址
        btn_insAddress = findViewById(R.id.btn_insAddress);
        btn_insAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(AddressActivity.this, AddressInsActivity.class);
                intent.putExtra("passValueForInsAd", userNameForInAd);
                startActivity(intent);
            }
        });

    }

    private void initView() {
        mainHandler = new Handler(getMainLooper());//获取主线程
        addressDao = new AddressDao();
    }

    /*填充地址列表*/
    private void loadAddress() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                String username3 = intent.getStringExtra("passValue2");//登陆后的传值
                addressList = addressDao.getAddressListByid(username3);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showAddressLvData();
                    }
                });
            }
        }).start();
    }

    private void showAddressLvData() {
        if (addressAdapter == null) {
            addressAdapter = new AddressAdapter(this, addressList);
            lv_address.setAdapter(addressAdapter);
        } else {
            addressAdapter.setAddressList(addressList);
            addressAdapter.notifyDataSetChanged();
        }

       /* // 修改按钮的操作
        addressAdapter.setOnEditBtnClickListener(new OnEditBtnClickListener() {
            @Override
            public void onEditBtnClick(View view, int position) {
                // 修改按钮的操作
                AddressEntity item = addressList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("userEdit", (Serializable) item);
                Intent intent = new Intent(AddressActivity.this, AddressEditActity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });*/

        // 删除按钮的操作
        addressAdapter.setOnDelBtnClickListener(new OnDelBtnClickListener() {
            @Override
            public void onDelBtnClick(View view, int position) {
                //  删除方法
                final AddressEntity item = addressList.get(position);
                new AlertDialog.Builder(AddressActivity.this)
                        .setTitle("删除确定")
                        .setMessage("确认删除：" +
                                item.getAddress() + "吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doDelAddress(item.getUuid());
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create().show();
            }
        });

    }

    //删除操作
    private void doDelAddress(final long uuid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final int iRow = addressDao.delAddress(uuid);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        loadAddress();  // 重新加载数据
                    }
                });
            }
        }).start();
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {   // 操作成功
            loadAddress();   // 重新加载数据
        }
    }
}