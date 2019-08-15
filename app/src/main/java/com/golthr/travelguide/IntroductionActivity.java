package com.golthr.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2019/7/26 0026.
 */

public class IntroductionActivity extends AppCompatActivity {
    private ImageView iv_back_btn;
    private TextView tv_main_title;

    protected void onCreate(Bundle savedInstanceState) {
        Affects.setStatusBarFontBlack(IntroductionActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        initView();
    }

    private void initView(){
        iv_back_btn = (ImageView)findViewById(R.id.iv_back_btn);
        tv_main_title = (TextView)findViewById(R.id.tv_main_title);

        tv_main_title.setText("功能介绍");
        iv_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
