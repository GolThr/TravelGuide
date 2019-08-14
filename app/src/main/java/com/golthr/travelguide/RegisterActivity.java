package com.golthr.travelguide;
//注册界面

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.golthr.travelguide.obj.Inform;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
//import cn.edu.gdmec.android.androidstudiodemo.utils.MD5Utils;
//import android.support.v7.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText et_username;
    private EditText et_email;
    private EditText et_verifyCode;
    private EditText et_pwd;
    private EditText et_rePWD;
    private Button btn_register;
    private Button btn_getVerifyCode;

    private ImageView iv_back_btn;
    private TextView tv_main_title;

    private String VERIFY_CODE = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Affects.setStatusBarFontBlack(RegisterActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        et_username = (EditText)findViewById(R.id.et_username);
        et_email = (EditText)findViewById(R.id.et_email);
        et_verifyCode = (EditText)findViewById(R.id.et_verifyCode);
        et_pwd = (EditText)findViewById(R.id.et_pwd);
        et_rePWD = (EditText)findViewById(R.id.et_rePWD);
        btn_register = (Button)findViewById(R.id.btn_register);
        btn_getVerifyCode = (Button)findViewById(R.id.btn_getVerifyCode);
        iv_back_btn = (ImageView)findViewById(R.id.iv_back_btn);
        tv_main_title = (TextView)findViewById(R.id.tv_main_title);
        tv_main_title.setText("注册账号");

        //注册Bmob_register
        //数据：用户名username，邮箱email，密码rePWD
        btn_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                System.out.println("Bmob_register...");
                String username = et_username.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String verifyCode = et_verifyCode.getText().toString().trim();
                String password = et_pwd.getText().toString().trim();
                String rePWD = et_rePWD.getText().toString().trim();

                System.out.println(username+" "+email+" " +verifyCode+" "+password+" "+rePWD);

                if(!username.equals("")){
                    if(Affects.checkEmail(email)){
                        if(verifyCode.equals(VERIFY_CODE)){
                            if(password.equals(rePWD) && !password.equals("") && !rePWD.equals("")){



                            }else {
                                Toast.makeText(RegisterActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(RegisterActivity.this, "请输入正确的邮箱", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_getVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getText().toString().trim();
                if(Affects.checkEmail(email)){
                    VERIFY_CODE = Affects.sendEmailVerifyCode(email);
                }else {
                    Toast.makeText(RegisterActivity.this, "请输入正确的邮箱", Toast.LENGTH_SHORT).show();
                }
            }
        });

        iv_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
