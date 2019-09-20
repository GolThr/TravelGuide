package com.golthr.travelguide;
//登录界面

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.golthr.travelguide.util.DensityUtil;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class LoginActivity extends AppCompatActivity {
    private EditText et_username;
    private EditText et_pwd;
    private Button btn_login;

    private TextView tv_register;
    private TextView tv_forgotPWD;

    //Dialog
    private TextView show_tip_content;
    private Button btn_read_tip;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        Affects.Immersive(LoginActivity.this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if (BmobUser.isLogin()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            initView();
        }

        showTipDialog("默认账号: 123456789@163.com\n      密  码: 123456");
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
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_username.getText().toString().trim();
                String password = et_pwd.getText().toString().trim();
                System.out.println("Bmob_login...");
                BmobUser userlogin = new BmobUser();
                userlogin.setUsername(email);
                userlogin.setPassword(password);
                userlogin.login(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if (e == null) {
                            Toast.makeText(LoginActivity.this, bmobUser.getUsername() + "登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            LoginActivity.this.finish();
        }
        return true;
    }

    private void showTipDialog(String tips) {
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_show_tip, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(this, 16f);
        params.bottomMargin = DensityUtil.dp2px(this, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();

        Window window = bottomDialog.getWindow();
        show_tip_content = (TextView) window.findViewById(R.id.show_tip_content);
        btn_read_tip = (Button)window.findViewById(R.id.btn_read_tip);

        show_tip_content.setText(tips);

        btn_read_tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.hide();
            }
        });
    }
}

