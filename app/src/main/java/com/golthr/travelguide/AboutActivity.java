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

public class AboutActivity extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    protected void onCreate(Bundle savedInstanceState) {
        Affects.setStatusBarFontBlack(AboutActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ImageView gotoIntroduction = (ImageView) findViewById(R.id.gotoIntroduction);
        gotoIntroduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this,IntroductionActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ImageView imageView=(ImageView)findViewById(R.id.iv_back_btn);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tv_title = (TextView)findViewById(R.id.tv_main_title);
        tv_title.setText("关于引路人");
    }

}
