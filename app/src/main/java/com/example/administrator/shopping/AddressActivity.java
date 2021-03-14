package com.example.administrator.shopping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
/*import com.example.administrator.shopping.adapter.LvUserinfoAdapter;*/

public class AddressActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        SettingActivity.activityList.add(this);
    }

    @Override
    public void onClick(View view) {

    }

}