package com.golthr.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Set;


public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Affects.setStatusBarFontBlack(SettingActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        TextView tv_title =(TextView)findViewById(R.id.tv_main_title);
        tv_title.setText("设置");

        RelativeLayout r1=(RelativeLayout) findViewById(R.id.r1) ;
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this,SecurityActivity.class);
                startActivity(intent);
            }
        });
        RelativeLayout r2=(RelativeLayout) findViewById(R.id.r2) ;
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this,InformActivity.class);
                startActivity(intent);
            }
        });
        RelativeLayout r3=(RelativeLayout) findViewById(R.id.r3) ;
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this,YinsiActivity.class);
                startActivity(intent);
            }
        });
        RelativeLayout r4=(RelativeLayout) findViewById(R.id.r4) ;
        r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this,NormalActivity.class);
                startActivity(intent);
            }
        });
        RelativeLayout r5=(RelativeLayout) findViewById(R.id.r5) ;
        r5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this,OtherActivity.class);
                startActivity(intent);
            }
        });
        ImageView img_back=(ImageView) findViewById(R.id.iv_back_btn) ;
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
