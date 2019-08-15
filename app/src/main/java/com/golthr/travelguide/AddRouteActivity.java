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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.golthr.travelguide.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class AddRouteActivity extends AppCompatActivity implements View.OnClickListener, GeocodeSearch.OnGeocodeSearchListener {
    //title
    private ImageView iv_back_btn;
    private TextView tv_main_title;
    private ImageView iv_func_btn_main;
    private ImageView iv_func_btn_done;

    private static final String TAG = "MainActivity";
    private ImageView img_add;
    private int itemCnt=-1;
    private LinearLayout my_layout;
    private EditText editText1;
    private ArrayList<RelativeLayout> spotItems;
    private ArrayList<SpotViewItem> spotViewItems;
    private Button btn_start;

    //数据
    private List<Destination> destinations;
    String goCity;
    String goAttraction;
    Boolean goSuggest;

    //Dialog
    Dialog bottomDialog;
    private EditText et_city;
    private EditText et_attraction;
    private CheckBox cb_suggest;
    private Button btn_add;
    private LinearLayout ll_edit_attraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Affects.setStatusBarFontBlack(AddRouteActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);
//        spotItems = new ArrayList<>();
        spotViewItems = new ArrayList<>();
        destinations = new ArrayList<>();
        initView();
    }

    public void addView(String city, String attraction, boolean isSuggest){
        if(isSuggest){
            attraction = "推荐景区";
        }
        my_layout = findViewById(R.id.My_layout);
        itemCnt++;
        //RelativeLayout
        RelativeLayout relativeLayout = new RelativeLayout(this);
        LinearLayout.LayoutParams relativeLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
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
        cityParams.setMargins(14,0,14,0);
        tv_city.setTextSize(20);


        //AttractionText
        TextView tv_attractions = new TextView(this);
        LinearLayout.LayoutParams attractionsParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        attractionsParams.setMargins(14,27,14,0);
        tv_attractions.setText(attraction);
        tv_attractions.setTextSize(16);


        //ImageView
        ImageView iv_delete = new ImageView(this);
        RelativeLayout.LayoutParams imageViewParams = new RelativeLayout.LayoutParams(60, 60);
        imageViewParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        imageViewParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        iv_delete.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        iv_delete.setBackground(getResources().getDrawable(R.drawable.ic_delete));
        iv_delete.setId(itemCnt);
        iv_delete.setVisibility(View.GONE);
        iv_delete.setOnClickListener(this);


        //addView
        linearLayout.addView(tv_city, cityParams);
        linearLayout.addView(tv_attractions, attractionsParams);
        relativeLayout.addView(linearLayout, linearLayoutParams);
        relativeLayout.addView(iv_delete, imageViewParams);
        my_layout.addView(relativeLayout, relativeLayoutParams);
//        spotItems.add(relativeLayout);
        spotViewItems.add(new SpotViewItem(itemCnt, relativeLayout, iv_delete));
    }

    public void deleteView(int id) {
        for(SpotViewItem img : spotViewItems){
            if(img.getId() == id){
                RelativeLayout item = img.getRlSpot();
                my_layout.removeView(item);
                spotViewItems.remove(img);
                break;
            }
        }
//        spotItems.remove(id);
//        spotViewItems.remove(id);
        for(Destination des : destinations){
            if(des.getId() == id){
                destinations.remove(des);
                break;
            }
        }
        System.out.println("Delete-----------------------------------------------------------------------------");
        for (Destination des : destinations){
            System.out.println(des.getId());
        }
//        itemCnt--;
//        Log.d(TAG, "deleteView: --------"+itemCnt);
    }

    public void addDestination(String city, String attraction){
        GeocodeSearch geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        // name表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode
        GeocodeQuery query = new GeocodeQuery(attraction, city);
        geocoderSearch.getFromLocationNameAsyn(query);
    }

    private void showBottomDialog() {
        bottomDialog = new Dialog(this, R.style.BottomDialog);
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
                goCity = et_city.getText().toString();
                goAttraction = et_attraction.getText().toString();
                goSuggest = cb_suggest.isChecked();
                if(goCity.isEmpty() || (!goSuggest && goAttraction.isEmpty())){
                    Toast.makeText(AddRouteActivity.this, "城市和景区不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    if(goSuggest){
                        //获取最推荐的景区Bmob_popularAttraction_impl
                        //数据：城市city
                        //获取到该城市最推荐的一个景区，调用addPlan方法添加行程，addPlan(城市, 景区);
                        addDestination(goCity, "此处填推荐的景区");
                    }else {
                        addDestination(goCity, goAttraction);
                    }
                }
            }
        });
    }

    private void initView() {
        iv_back_btn = (ImageView)findViewById(R.id.iv_back_btn);
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        iv_func_btn_main = (ImageView) findViewById(R.id.iv_func_btn_main);
        iv_func_btn_done = (ImageView) findViewById(R.id.iv_func_btn_done);
        img_add = findViewById(R.id.img_add);
        btn_start = (Button)findViewById(R.id.btn_start);

        tv_main_title.setText("行程规划");
        iv_func_btn_main.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));

        iv_func_btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemCnt >= 0){
                    img_add.setVisibility(View.GONE);
                    btn_start.setVisibility(View.GONE);
                    iv_func_btn_main.setVisibility(View.GONE);
                    iv_func_btn_done.setVisibility(View.VISIBLE);
                    for(SpotViewItem i : spotViewItems){
                        if(i.getIvItem() != null){
                            i.getIvItem().setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });

        iv_func_btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_add.setVisibility(View.VISIBLE);
                btn_start.setVisibility(View.VISIBLE);
                iv_func_btn_main.setVisibility(View.VISIBLE);
                iv_func_btn_done.setVisibility(View.GONE);
                for(SpotViewItem i : spotViewItems){
                    if(i.getIvItem() != null){
                        i.getIvItem().setVisibility(View.GONE);
                    }
                }
            }
        });

        iv_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog();
            }
        });

        //开始规划
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //规划行程Bmob_planingTrip_impl
                //数据：目的地destinations(List<Destination>)
                if(!destinations.isEmpty()){
                    //输出规划结果，并跳转到DetailActivity显示，需将规划详情当做参数传递
                    //跳转示例
                    Intent intent=new Intent(AddRouteActivity.this, PlanResultActivity.class);
                    intent.putExtra(PlanResultActivity.PLAN_INFO, "规划结果");
                    startActivity(intent);
                }else {
                    Toast.makeText(AddRouteActivity.this, "请先添加行程", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        deleteView(view.getId());
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        List<GeocodeAddress> list = geocodeResult.getGeocodeAddressList();
        System.out.println("SearchAttractionResult---------------------------------------------------------------------");
        for(GeocodeAddress ga : list){
            System.out.println(ga.getFormatAddress());
            LatLonPoint point = ga.getLatLonPoint();
            System.out.println("point: " + point.getLatitude() + ", " + point.getLongitude());
        }

        if(list.isEmpty()){
            Toast.makeText(AddRouteActivity.this, "没有找到您输入的景区", Toast.LENGTH_SHORT).show();
        }else {
            GeocodeAddress item = list.get(0);
            String address = item.getFormatAddress();
            LatLonPoint point = item.getLatLonPoint();

            addView(goCity, goAttraction, goSuggest);
            destinations.add(new Destination(itemCnt, goCity, goAttraction, point.getLatitude(), point.getLongitude()));
            bottomDialog.hide();
        }
    }
}
