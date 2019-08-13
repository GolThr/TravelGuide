package com.golthr.travelguide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class FindBackActivity extends AppCompatActivity {
    private ImageView iv_back_btn;
    private TextView tv_main_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Affects.Immersive(FindBackActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_back);

        initView();
    }

    private void initView(){
        iv_back_btn = (ImageView)findViewById(R.id.iv_back_btn);
        tv_main_title = (TextView)findViewById(R.id.tv_main_title);

        tv_main_title.setText("找回密码");

        iv_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
