package com.golthr.travelguide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FootActivity extends AppCompatActivity {

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
         Affects.setStatusBarFontBlack(FootActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);
        ImageView imageView=(ImageView)findViewById(R.id.iv_back_btn);
        imageView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });
        TextView tv_title = (TextView) findViewById(R.id.tv_main_title);
        tv_title.setText("我的足迹");
//        articles = new ArrayList<Article>();
//        articles.add(new Article(R.drawable.about2,"100", "Cindy", "2019-1-1"));
//        articles.add(new Article(R.drawable.banana_pic,"101", "Lisa", "2019-1-1"));
//        articles.add(new Article(R.drawable.banana_pic,"102323", "Lili","2019-1-1"));
//        articles.add(new Article(R.drawable.banana_pic,"103", "Jack", "2019-1-1"));
//        articles.add(new Article(R.drawable.banana_pic,"121324", "Jim","2019-1-1"));
//        articles.add(new Article(R.drawable.banana_pic,"108", "Tom","2019-1-1"));
//        articles.add(new Article(R.drawable.banana_pic,"1321421", "Mike","2019-1-1"));
        mRecyclerView=(RecyclerView) findViewById(R.id.reclcler_view);
        initData();

        // 设置LinearLayoutManager
//       mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter=new MyAdapter(MainActivity.this,initData());

        // 设置ItemAnimator
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        // 设置固定大小
//        mRecyclerView.setHasFixedSize(true);
        // 初始化自定义的适配器
        // 为mRecyclerView设置适配器
   //     mRecyclerView.setAdapter(adapter);




    }
//public  ArrayList<Article> initData(){
//    ArrayList<Article> studentList = new ArrayList<>();
//    for (int j=0;j<articles.length;j++){
//        studentList.add(articles[j]);
//    }
//
//    return studentList;
//}
    public void initData(){
        ArrayList<Article> articleList = new ArrayList<>();
    for (int j = 0; j< articles.length; j++){
        articleList.add(articles[j]);
    }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MyAdapter(FootActivity.this, articleList);

        mRecyclerView.setAdapter(adapter);
    }

}
