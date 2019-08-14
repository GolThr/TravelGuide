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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.golthr.travelguide.obj.Inform;
import com.golthr.travelguide.util.DensityUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class InformationActivity extends AppCompatActivity {
    //Dialog
    private EditText et_old;
    private EditText et_new;
    private EditText et_repassword;
    private Button btn_change;
    private EditText et1;//姓名
    private EditText et2;//邮箱
    private EditText et3;//地址
    private Button btn;//确认
    private RadioGroup rg_gender;
    private String Et1,Et2,Et3;

    final String name = (String) BmobUser.getObjectByKey("Name");
    final String email = (String) BmobUser.getObjectByKey("Phone");
    final String address = (String) BmobUser.getObjectByKey("Address");
    private String gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Affects.setStatusBarFontBlack(InformationActivity.this);
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

        et1=(EditText)findViewById(R.id.et1);
        et2=(EditText)findViewById(R.id.et2);
        et3=(EditText) findViewById(R.id.et3);
        btn=(Button)findViewById(R.id.btn_true);
        rg_gender = (RadioGroup)findViewById(R.id.rg_gender);

        et1.setText(name);
        et2.setText(email);
        et3.setText(address);
        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.radio_male){
                    gender = "男";
                }else if(i == R.id.radio_femle){
                    gender = "女";
                }else {
                    gender = "";
                }
            }
        });


        BmobUser user = BmobUser.getCurrentUser(BmobUser.class);
        String userId = user.getObjectId();
        //修改资料Bmob_modifyInfo_impl
        //数据：用户userId，用户名Et1，性别gender，手机号Et2，地址Et3
        btn.setOnClickListener(new View.OnClickListener(){ //点击保存，数据传到数据库中。
            @Override
            public void onClick(View view) {
                Et1 = et1.getText().toString().trim();
                Et2 = et2.getText().toString().trim();
                Et3 = et3.getText().toString().trim();
                //else if(!Et2.equals("女")&&!Et2.equals("男")){
//                    Toast.makeText(InformationActivity.this, "请按要求输入性别", Toast.LENGTH_SHORT).show();
//                    et2.setText(sex);
//                    return;
//                }
                if (Et1.equals("")) {
                    Toast.makeText(InformationActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
                    return;
                } else if(gender.equals("")){
                    Toast.makeText(InformationActivity.this, "请选择性别", Toast.LENGTH_SHORT).show();
                    return;
                } else if (Et2.equals("")) {
                    Toast.makeText(InformationActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (Et3.equals("")) {
                    Toast.makeText(InformationActivity.this, "请输入地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Et1.equals(name) && Et2.equals(email) && Et3.equals(address)) {
                    Toast.makeText(InformationActivity.this, "您没有做任何修改", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Inform inform=new Inform();

                    inform.setAddress(Et3);
                    inform.setSex(gender);
                    inform.setName(Et1);
                    inform.setPhone(Et2);
                    System.out.println(inform.getName());
                    Inform user = BmobUser.getCurrentUser(Inform.class);
                    inform.update(user.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(InformationActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(InformationActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
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
        et_repassword = (EditText)window.findViewById(R.id.et_repassword);
        btn_change = (Button)window.findViewById(R.id.btn_change);

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobUser user = BmobUser.getCurrentUser(BmobUser.class);
                String userId = user.getObjectId();
                String oldP = et_old.getText().toString();
                String newP = et_new.getText().toString();
                String reP = et_repassword.getText().toString();

                if(oldP.isEmpty() || newP.isEmpty() || reP.isEmpty()){
                    Toast.makeText(InformationActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                }else if(newP.equals(oldP)){
                    Toast.makeText(InformationActivity.this, "新密码与旧密码相同！", Toast.LENGTH_SHORT).show();
                } else if(!reP.equals(newP)){
                    Toast.makeText(InformationActivity.this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
                }else {
                    //修改密码Bmob_modifyPWD_impl
                    //数据：用户userId，旧密码oldPWD，新密码newPWD
                    //若成功则运行下面两行代码，若旧密码错误则提示原密码错误
                    Toast.makeText(InformationActivity.this, "密码修改成功！", Toast.LENGTH_SHORT).show();
                    bottomDialog.hide();
                }
            }
        });
    }
}
