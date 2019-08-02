package com.golthr.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class NormalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);

        ImageView img=(ImageView)findViewById(R.id.iv_back_btn);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView tv_title = (TextView)findViewById(R.id.tv_main_title);
        tv_title.setText("通用");
    }
}
