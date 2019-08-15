package com.golthr.travelguide;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PlanResultActivity extends AppCompatActivity {
    //Bundle参数
    public static final String PLAN_INFO = "plan_info";

    private ImageView iv_back_btn;
    private TextView tv_main_title;

    private TextView tv_plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Affects.setStatusBarFontBlack(PlanResultActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_result);

        Intent intent = getIntent();
        String plan_info = intent.getStringExtra(PLAN_INFO);

        initView(plan_info);
    }

    private void initView(String plan_info){
        iv_back_btn = (ImageView)findViewById(R.id.iv_back_btn);
        tv_main_title = (TextView)findViewById(R.id.tv_main_title);
        tv_plan = (TextView)findViewById(R.id.tv_plan);

        tv_main_title.setText("规划结果");
        iv_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_plan.setText(plan_info);
    }
}
