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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//import cn.edu.gdmec.android.androidstudiodemo.utils.MD5Utils;
//import android.support.v7.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText et_username;
    private EditText et_pwd;
    private EditText et_repwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {

    }
}
