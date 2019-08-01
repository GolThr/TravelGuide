package com.golthr.travelguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class MyPublishActivity extends AppCompatActivity {

    private MyAdapter adapter;
    private RecyclerView mRecyclerView;
    Student[] students = {new Student(R.drawable.about2, "99999999999", "Cindy", "2019-w1-w1"),
            new Student(R.drawable.banana_pic, "101", "Lisa", "2019-w1-w1"), new Student(R.drawable.banana_pic, "102323", "Lili", "2019-w1-w1"),
            new Student(R.drawable.banana_pic, "103", "Jack", "2019-w1-w1"), new Student(R.drawable.banana_pic, "121324", "Jim", "2019-w1-w1"),
            new Student(R.drawable.banana_pic, "108", "Tom", "2019-w1-w1"), new Student(R.drawable.banana_pic, "1321421", "Mike", "2019-w1-w1")};

    //数据源
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);
        ImageView imageView = (ImageView) findViewById(R.id.backToMain);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        students = new ArrayList<Student>();
//        students.add(new Student(R.drawable.about2,"100", "Cindy", "2019-w1-w1"));
//        students.add(new Student(R.drawable.banana_pic,"101", "Lisa", "2019-w1-w1"));
//        students.add(new Student(R.drawable.banana_pic,"102323", "Lili","2019-w1-w1"));
//        students.add(new Student(R.drawable.banana_pic,"103", "Jack", "2019-w1-w1"));
//        students.add(new Student(R.drawable.banana_pic,"121324", "Jim","2019-w1-w1"));
//        students.add(new Student(R.drawable.banana_pic,"108", "Tom","2019-w1-w1"));
//        students.add(new Student(R.drawable.banana_pic,"1321421", "Mike","2019-w1-w1"));
        mRecyclerView = (RecyclerView) findViewById(R.id.reclcler_view);
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

    //public  ArrayList<Student> initData(){
//    ArrayList<Student> studentList = new ArrayList<>();
//    for (int j=0;j<students.length;j++){
//        studentList.add(students[j]);
//    }
//
//    return studentList;
//}
    public void initData() {
        ArrayList<Student> studentList = new ArrayList<>();
        for (int j = 0; j < students.length; j++) {
            studentList.add(students[j]);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(MyPublishActivity.this, studentList);

        mRecyclerView.setAdapter(adapter);
    }
}
