package com.golthr.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by Administrator on 2019/7/26 0026.
 */

public class AboutActivity extends AppCompatActivity {
    private TextView tv_version;
    private TextView tv_currentVersion;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    protected void onCreate(Bundle savedInstanceState) {
        Affects.setStatusBarFontBlack(AboutActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        RelativeLayout gotoIntroduction = (RelativeLayout) findViewById(R.id.gotoIntroduction);
        gotoIntroduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this,IntroductionActivity.class);
                startActivity(intent);
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

        RelativeLayout rl_checkUpdate = (RelativeLayout)findViewById(R.id.rl_checkUpdate);
        tv_version = (TextView)findViewById(R.id.tv_version);
        tv_currentVersion = (TextView)findViewById(R.id.tv_currentVersion);

        checkForUpdate();
        rl_checkUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForUpdate();
            }
        });
    }

    private void checkForUpdate(){
        //检测新版本Bmob_checkUpdate_impl
        //数据：当前版本currentVersion
        String currentVersion = tv_currentVersion.getText().toString().trim();
        //获取最新版本号，如果比现在更新，则设置tv_version显示有最新版本，否则不设置
    }
}
