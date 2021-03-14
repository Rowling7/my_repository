package com.example.administrator.shopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettlementActivity extends AppCompatActivity {

   /* private Button btnSettlement;
    public Intent intent = null;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement);

        SettingActivity.activityList.add(this);

        /*btnSettlement = findViewById(R.id.btn_settlement);
        btnSettlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(SettlementActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
*/
    }

}