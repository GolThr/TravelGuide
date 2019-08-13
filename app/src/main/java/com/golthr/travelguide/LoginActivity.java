package com.golthr.travelguide;
//登录界面

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import cn.edu.gdmec.android.androidstudiodemo.utils.MD5Utils;
//import android.support.v7.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText et_username;
    private EditText et_pwd;
    private Button btn_login;

    private TextView tv_register;
    private TextView tv_forgotPWD;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        GetImmersive.Immersive(LoginActivity.this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    //获取界面控件
    private void initView() {
        et_username = (EditText) findViewById(R.id.et_username);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        btn_login = (Button)findViewById(R.id.btn_login);

        tv_register = (TextView)findViewById(R.id.et_register);
        tv_forgotPWD = (TextView)findViewById(R.id.et_forgotPWD);

        //登录Bmob_login
        //数据：用户名username，密码password
        String username = et_username.getText().toString().trim();
        String password = et_pwd.getText().toString().trim();
        System.out.println("Bmob_login...");
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //登录
                //成功后跳转到MainActivity，并将用户信息保存到手机
            }
        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(go_register);
            }
        });

        tv_forgotPWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_forgotPWD = new Intent(LoginActivity.this, FindBackActivity.class);
                startActivity(go_forgotPWD);
            }
        });
    }
}

