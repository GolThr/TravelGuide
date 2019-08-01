package com.golthr.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Set;


public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        RelativeLayout r1=(RelativeLayout) findViewById(R.id.r1) ;
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this,SecurityActivity.class);
                startActivity(intent);
                finish();
            }
        });
        RelativeLayout r2=(RelativeLayout) findViewById(R.id.r2) ;
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this,InformActivity.class);
                startActivity(intent);
                finish();


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
        ImageView img_back=(ImageView) findViewById(R.id.im_back) ;
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}