package com.golthr.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.bmob.v3.BmobUser;

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

        BmobUser user = BmobUser.getCurrentUser(BmobUser.class);
        String userId = user.getObjectId();
        //获取攻略内容及是否收藏Bmob_getArticleInfo_impl
        //数据：用户userId，攻略号article_id
        //根据攻略号获取到对应攻略的标题、图片、发布者、发布时间和发布内容，并设置到下面的界面里
        //每次都要保存一次记录在相应的用户中，作为历史记录（注：连续两次打开相同的攻略算一次记录）
        //如果用户已经收藏，就将id为add_fav_btn的按钮隐藏，且显示id为del_fav_btn的按钮
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

        ////////////////////////////////////////
        //添加足迹Bmob_addFoot_impl
        //数据：用户userId，攻略号article_id

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }else if(id == R.id.add_fav_btn){
            //添加收藏Bmob_addFav_impl
            //数据：用户userId，攻略号article_id
            return true;
        }else if (id == R.id.del_fav_btn){
            //取消收藏Bmob_delFav_impl
            //数据：用户userId，攻略号article_id
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }
}
