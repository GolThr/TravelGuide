package com.golthr.travelguide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.bmob.v3.BmobUser;

public class FavoriteActivity extends AppCompatActivity {
    private MyAdapter adapter;
    private RecyclerView mRecyclerView;
    private Article[] articles ={new Article(R.drawable.about2,"shaaa", "Cindy", "2019-1-1", "100"),
            new Article(R.drawable.banana_pic,"1dght01", "Lisa", "2019-1-1", "100"),
            new Article(R.drawable.banana_pic,"1023rtyh23", "Lili","2019-1-1", "100"),
            new Article(R.drawable.banana_pic,"10rht3", "Jack", "2019-1-1", "100"),
            new Article(R.drawable.banana_pic,"1rht21324", "Jim","2019-1-1", "100"),
            new Article(R.drawable.banana_pic,"1hrd08", "Tom","2019-1-1", "100"),
            new Article(R.drawable.banana_pic,"1rt321421", "Mike","2019-1-1", "100")};
    //数据源

     @Override
    protected void onCreate(Bundle savedInstanceState) {
         Affects.setStatusBarFontBlack(FavoriteActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);

        //标题栏
        ImageView imageView=(ImageView)findViewById(R.id.iv_back_btn);
        imageView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });
        TextView tv_title = (TextView)findViewById(R.id.tv_main_title);
        tv_title.setText("我的收藏");
        mRecyclerView=(RecyclerView) findViewById(R.id.reclcler_view);

        initData();
    }

    public void initData(){
        BmobUser user = BmobUser.getCurrentUser(BmobUser.class);
        String userId = user.getObjectId();
        //获取所有收藏Bmob_getFavorite_impl
        //数据：用户userId
        //获取到该用户收藏的所有攻略的信息，并将每个攻略分别存成一个Article对象，存入下面的articleList列表中即可(数据需包括攻略图片1张，攻略标题，攻略简短介绍，攻略作者用户名，攻略发布时间和攻略id号)
        //注意保存攻略id，每条攻略的点击事件不用设置
        ArrayList<Article> articleList = new ArrayList<>();
        for (int j = 0; j< articles.length; j++){
            articleList.add(articles[j]);
        }
        //////////////////

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MyAdapter(FavoriteActivity.this, articleList);
        mRecyclerView.setAdapter(adapter);
    }

}
