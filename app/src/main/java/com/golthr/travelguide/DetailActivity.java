package com.golthr.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    public static final String ARTICLE_ID = "article_id";

    String article_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Affects.ImmersiveWhiteFont(DetailActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent=getIntent();
        String article_id=intent.getStringExtra(ARTICLE_ID);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolBar);

        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initData();
    }

    private void initData(){
        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        ImageView imageView=(ImageView)findViewById(R.id.imageView);
        TextView textView=(TextView)findViewById(R.id.tv_content);
        TextView tv_author=(TextView)findViewById(R.id.tv_author);
        TextView tv_publishTime=(TextView)findViewById(R.id.tv_publishTime);

        //获取攻略内容Bmob_getArticleInfo_impl
        //数据：攻略号article_id
        //根据攻略号获取到对应攻略的标题、图片、发布者、发布时间和发布内容，并设置到下面的界面里
        //攻略标题
        collapsingToolbarLayout.setTitle("攻略标题");
        //发布者
        tv_author.setText("发布者");
        //发布时间
        tv_publishTime.setText("发布时间");
        //攻略图片
        Glide.with(this).load("攻略图片").into(imageView);
        //攻略内容
        textView.setText("攻略内容");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
