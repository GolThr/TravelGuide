package com.golthr.travelguide;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.golthr.travelguide.util.DensityUtil;

import java.util.ArrayList;

public class AddRouteActivity extends AppCompatActivity implements View.OnClickListener {

    //title
    private ImageView iv_back_btn;
    private TextView tv_main_title;

    private static final String TAG = "MainActivity";
    private ImageView img_add;
    private int i=-1;
    private LinearLayout my_layout;
    private EditText editText1;
    private ArrayList<EditText> editTexts;
    private Button btn_start;

    //Dialog
    private EditText et_city;
    private EditText et_attraction;
    private CheckBox cb_suggest;
    private Button btn_add;
    private LinearLayout ll_edit_attraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);
        editTexts = new ArrayList<>();
        initView();

    }

//    public void addView(String city, String attraction, boolean isSuggest){
//        if(isSuggest){
//            attraction = "推荐景区";
//        }
//        my_layout = findViewById(R.id.My_layout);
//        LinearLayout linearLayout = new LinearLayout(this);
//        i++;
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.setMargins(0,5,0,5);
//        LinearLayout.LayoutParams cityParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        cityParams.setMargins(5,0,5,0);
//        LinearLayout.LayoutParams attractionsParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        attractionsParams.setMargins(5,10,5,0);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        linearLayout.setGravity(Gravity.CENTER);
//        linearLayout.setBackground(getResources().getDrawable(R.drawable.back_card));
//        TextView tv_city = new TextView(this);
//        TextView tv_attractions = new TextView(this);
//        tv_city.setText(city);
//        tv_city.setTextSize(20);
//        tv_attractions.setText(attraction);
//        tv_attractions.setTextSize(16);
//        linearLayout.addView(tv_city, cityParams);
//        linearLayout.addView(tv_attractions, attractionsParams);
//        my_layout.addView(linearLayout, layoutParams);
//    }

    public void addView(String city, String attraction, boolean isSuggest){
        if(isSuggest){
            attraction = "推荐景区";
        }
        my_layout = findViewById(R.id.My_layout);
        i++;
        //RelativeLayout
        RelativeLayout relativeLayout = new RelativeLayout(this);
        LinearLayout.LayoutParams relativeLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 110);
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.setBackground(getResources().getDrawable(R.drawable.back_card));

        //LinearLayout
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        //CityText
        TextView tv_city = new TextView(this);
        LinearLayout.LayoutParams cityParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv_city.setText(city);
        cityParams.setMargins(5,0,5,0);
        tv_city.setTextSize(20);


        //AttractionText
        TextView tv_attractions = new TextView(this);
        LinearLayout.LayoutParams attractionsParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        attractionsParams.setMargins(5,10,5,0);
        tv_attractions.setText(attraction);
        tv_attractions.setTextSize(16);


        //ImageView
        ImageView iv_delete = new ImageView(this);
        RelativeLayout.LayoutParams imageViewParams = new RelativeLayout.LayoutParams(20, ViewGroup.LayoutParams.MATCH_PARENT);
        imageViewParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        relativeLayout.setBackground(getResources().getDrawable(R.drawable.ic_delete));
        iv_delete.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


        //addView
        linearLayout.addView(tv_city, cityParams);
        linearLayout.addView(tv_attractions, attractionsParams);
        relativeLayout.addView(linearLayout, linearLayoutParams);
//        relativeLayout.addView(iv_delete, imageViewParams);
        my_layout.addView(relativeLayout, relativeLayoutParams);
    }


    public void deleteView() {
        EditText editText = editTexts.get(i);
        my_layout.removeView(editText);
        editTexts.remove(i);
        i--;
        Log.d(TAG, "deleteView: --------"+i);
    }

    private void showBottomDialog() {
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_circle, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(this, 16f);
        params.bottomMargin = DensityUtil.dp2px(this, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();

        Window window = bottomDialog.getWindow();
        et_city = (EditText)window.findViewById(R.id.et_city);
        et_attraction = (EditText)window.findViewById(R.id.et_attraction);
        cb_suggest = (CheckBox)window.findViewById(R.id.cb_suggest);
        btn_add = (Button)window.findViewById(R.id.btn_add);
        ll_edit_attraction = (LinearLayout)window.findViewById(R.id.ll_edit_attraction);

        cb_suggest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    ll_edit_attraction.setVisibility(View.GONE);
                }else {
                    ll_edit_attraction.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String goCity = et_city.getText().toString();
                String goAttraction = et_attraction.getText().toString();
                Boolean goSuggest = cb_suggest.isChecked();
                if(goCity.isEmpty() || (!goSuggest && goAttraction.isEmpty())){
                    Toast.makeText(AddRouteActivity.this, "城市和景区不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    addView(goCity, goAttraction, goSuggest);
                    bottomDialog.hide();
                }
            }
        });
    }

    private void initView() {
        iv_back_btn = (ImageView)findViewById(R.id.iv_back_btn);
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        img_add = findViewById(R.id.img_add);
        img_add.setOnClickListener(this);
        btn_start = (Button)findViewById(R.id.btn_start);

        tv_main_title.setText("行程规划");
        iv_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //开始规划
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_add:
                showBottomDialog();
                break;
        }
    }
}
