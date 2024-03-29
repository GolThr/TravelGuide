package com.golthr.travelguide;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.golthr.travelguide.util.DensityUtil;

public class FindBackActivity extends AppCompatActivity {
    private ImageView iv_back_btn;
    private TextView tv_main_title;

    private EditText et_email;
    private EditText et_verifyCode;
    private EditText et_newPWD;
    private EditText et_rePWD;
    private Button btn_findBack;
    private Button btn_getVerifyCode;

    //Dialog
    private TextView show_tip_content;
    private Button btn_read_tip;

    private String VERIFY_CODE = "7849";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Affects.setStatusBarFontBlack(FindBackActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_back);

        initView();
        showTipDialog("为了方便您的测试与使用，\n我们将邮箱验证码默认设置为7849。");
    }

    private void initView(){
        iv_back_btn = (ImageView)findViewById(R.id.iv_back_btn);
        tv_main_title = (TextView)findViewById(R.id.tv_main_title);
        et_email = (EditText)findViewById(R.id.et_email);
        et_verifyCode = (EditText)findViewById(R.id.et_verifyCode);
        et_newPWD = (EditText)findViewById(R.id.edit_newPWD);
        btn_getVerifyCode = (Button)findViewById(R.id.btn_getVerifyCode);
        et_rePWD = (EditText)findViewById(R.id.et_rePWD);
        btn_findBack = (Button)findViewById(R.id.btn_findBack);

        tv_main_title.setText("找回密码");

        //找回密码Bmob_findBack_impl
        //数据：邮箱email，验证码verifyCode，新密码rePWD
        btn_findBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getText().toString().trim();
                String verifyCode = et_verifyCode.getText().toString().trim();
                String newPWD = et_newPWD.getText().toString().trim();
                String rePWD = et_rePWD.getText().toString().trim();

                if (Affects.checkEmail(email)){
                    if(verifyCode.equals(VERIFY_CODE)){
                        if (newPWD.length() >= 6 && newPWD.length() <= 16){
                            if (rePWD.equals(newPWD)){
                                //可以找回,成功找回后返回登录页面重新登录

                            }else {
                                Toast.makeText(FindBackActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(FindBackActivity.this, "请设置新密码", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(FindBackActivity.this, "验证码不正确", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(FindBackActivity.this, "请输入正确的邮箱", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(FindBackActivity.this, "请输入正确的邮箱", Toast.LENGTH_SHORT).show();
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
        show_tip_content = (TextView)window.findViewById(R.id.show_tip_content);
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
