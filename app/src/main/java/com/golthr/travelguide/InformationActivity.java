package com.golthr.travelguide;

import android.app.Dialog;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.golthr.travelguide.util.DensityUtil;

public class InformationActivity extends AppCompatActivity {
    //Dialog
    private EditText et_old;
    private EditText et_new;
    private EditText et_repassword;
    private Button btn_change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        TextView tv_title = (TextView) findViewById(R.id.tv_main_title);
        tv_title.setText("编辑资料");

        RelativeLayout r1 = (RelativeLayout) findViewById(R.id.l1);
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InformationActivity.this,PictureActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout r2 = (RelativeLayout) findViewById(R.id.l6);
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog();
            }
        });
        ImageView img=(ImageView)findViewById(R.id.iv_back_btn);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showBottomDialog() {
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_change_password, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(this, 16f);
        params.bottomMargin = DensityUtil.dp2px(this, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();

        Window window = bottomDialog.getWindow();
        et_old = (EditText)window.findViewById(R.id.et_old);
        et_new = (EditText)window.findViewById(R.id.et_new);
        et_repassword = (EditText)window.findViewById(R.id.edit_repassword);
        btn_change = (Button)window.findViewById(R.id.btn_change);

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldP = et_old.getText().toString();
                String newP = et_new.getText().toString();
                String reP = et_repassword.getText().toString();

                if(newP.equals(oldP)){
                    Toast.makeText(InformationActivity.this, "新密码与旧密码相同！", Toast.LENGTH_SHORT).show();
                } else if(!reP.equals(newP)){
                    Toast.makeText(InformationActivity.this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(InformationActivity.this, "密码修改成功！", Toast.LENGTH_SHORT).show();
                    bottomDialog.hide();
                }
            }
        });
    }
}
