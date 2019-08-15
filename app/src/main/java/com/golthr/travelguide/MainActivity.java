package com.golthr.travelguide;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.AoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.golthr.travelguide.slidecontentlayout.IInterceptChecker;
import com.golthr.travelguide.slidecontentlayout.SlideContentLayout;
import com.golthr.travelguide.test.CustomScrollView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

import static com.amap.api.maps.model.BitmapDescriptorFactory.getContext;

public class MainActivity extends AppCompatActivity implements AMap.CancelableCallback, AMap.OnMyLocationChangeListener, GeocodeSearch.OnGeocodeSearchListener, IInterceptChecker, NavigationView.OnNavigationItemSelectedListener {
    //AMap
    private MapView mMapView = null;
    private AMap aMap;
    private Bitmap bitmap;
    private String CURRENT_ADDRESS = "";
    //是否需要检测后台定位权限，设置为true时，如果用户没有给予后台定位权限会弹窗提示
    private boolean needCheckBackLocation = false;
    //如果设置了target > 28，需要增加这个权限，否则不会弹出"始终允许"这个选择框
    private static String BACK_LOCATION_PERMISSION = "android.permission.ACCESS_BACKGROUND_LOCATION";
    private UiSettings mUiSettings;//定义一个UiSettings对象

    //View
    private ImageView iv_mine_btn;
    private ImageView iv_add_btn;
    private TextView tv_locate;
    private TextView tv_locate_info;
    private LinearLayout ll_get_location;

    private TextView tv_suggestInfo;
    private TextView tv_foldMore;
    private TextView tv_route_1;
    private TextView tv_route_2;
    private LinearLayout ll_route_more;
    private TextView tv_evacuation_1;
    private TextView tv_evacuation_2;
    private LinearLayout ll_evacuation_more;

    //FloatView
    private static final String TAG = "MainActivity";
    private RelativeLayout mDataContainerLayout;
    private SlideContentLayout mSlideContentLayout;
    private CustomScrollView mDataRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Affects.Immersive(MainActivity.this);
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT > 28
                && getApplicationContext().getApplicationInfo().targetSdkVersion > 28) {
            needPermissions = new String[] {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE,
                    BACK_LOCATION_PERMISSION
            };
            needCheckBackLocation = true;
        }
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_locate_poi), 100, 100, true);
        initMap();
        initView();
        initInfo();
    }

    private void initMap(){
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();
        }

        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位模式
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
        myLocationStyle.strokeColor(Color.parseColor("#4c329af2"));//设置定位蓝点精度圆圈的边框颜色的方法。
        myLocationStyle.radiusFillColor(Color.parseColor("#4c329af2"));//设置定位蓝点精度圆圈的填充颜色的方法。

        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        mUiSettings = aMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(false);
        //设置希望展示的地图缩放级别
        aMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, this);
        aMap.setOnMyLocationChangeListener(this);
    }

    private void initView(){
        tv_locate = (TextView)findViewById(R.id.tv_locate);
        tv_locate_info = (TextView)findViewById(R.id.tv_locate_info);
        ll_get_location = (LinearLayout)findViewById(R.id.ll_get_location);

        //ClickListener
        //Located
        ll_get_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initMap();
            }
        });
        //FloatViewInit
        mDataContainerLayout = (RelativeLayout) findViewById(R.id.rl_data_content_layout);
        mSlideContentLayout = (SlideContentLayout) findViewById(R.id.slide_layout);
        //设置事件拦截器
        mSlideContentLayout.setInterceptChecker(this);
        mDataRecyclerView = (CustomScrollView) findViewById(R.id.recyclerview_data_list);
    }

    private void initInfo(){
        tv_suggestInfo = (TextView)findViewById(R.id.tv_suggestInfo);
        tv_foldMore = (TextView)findViewById(R.id.tv_foldMore);
        tv_route_1 = (TextView)findViewById(R.id.tv_route_1);
        tv_route_2 = (TextView)findViewById(R.id.tv_route_2);
        ll_route_more = (LinearLayout)findViewById(R.id.ll_route_more);
        tv_evacuation_1 = (TextView)findViewById(R.id.tv_evacuation_1);
        tv_evacuation_2 = (TextView)findViewById(R.id.tv_evacuation_2);
        ll_evacuation_more = (LinearLayout)findViewById(R.id.ll_evacuation_more);


        //获取景区相关信息Bmob_getInfo_impl
        //数据：当前地点CURRENT_ADDRESS
        //获取到数据并将景区介绍放到tv_suggestInfo里，如果不在景区则不要设置介绍文字（避免将默认文字覆盖）
        //获取到当前景区的推荐游玩路线或攻略，推荐出两条，分别放到tv_route_1和tv_route_2中并分别为其设置点击事件跳转到DetailActivity，并向其传1个名为攻略号的参数，(下面有跳转示例)
        //      如果仅有2条以内路线，则把其余卡片隐藏掉，展示更多的卡片id为ll_route_more
        //获取当前景区的逃生路线说明，推荐出两条，分别放到tv_evacuation_1和tv_evacuation_2中并分别为其设置点击事件跳转到DetailActivity，并向其传1个名为逃生路线号的参数（这个数据库没设计），
        //      如果仅有2条以内路线，则把其余卡片隐藏掉，展示更多的卡片id为ll_evacuation_more
        //跳转示例(参数变量名不要改)
        Intent intent=new Intent(MainActivity.this,DetailActivity.class);
        intent.putExtra(DetailActivity.ARTICLE_ID, "攻略号id");
        startActivity(intent);
    }

    @Override
    public boolean checkIfIntercept() {
        View firstChild = mDataRecyclerView.getChildAt(0);
        boolean shouldIntercept;
        if (firstChild.getTop() == 0) {
            shouldIntercept = true;
        } else {
            shouldIntercept = false;
        }
        return shouldIntercept;
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
        tv_locate.setText(regeocodeAddress.getCity());
        List<AoiItem> list = regeocodeAddress.getAois();
        CURRENT_ADDRESS = list.get(0).getAoiName();

        System.out.println("getAois---------------------------------------------------------------------------");
        for(AoiItem aoi : list){
            System.out.println(aoi.getAoiName());
        }

        tv_locate_info.setText("·" + CURRENT_ADDRESS);
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onMyLocationChange(Location location) {
        GeocodeSearch geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        LatLonPoint latLonPoint = new LatLonPoint(location.getLatitude(), location.getLongitude());
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onCancel() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_guihua) {
            Intent intent = new Intent(MainActivity.this, AddRouteActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_fabu) {
            Intent intent = new Intent(MainActivity.this, PublishActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_self) {
            Intent intent = new Intent(MainActivity.this,InformationActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_favorite) {
            Intent intent = new Intent(MainActivity.this,FavoriteActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_foot) {
            Intent intent = new Intent(MainActivity.this,FootActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_fabu) {
            Intent intent = new Intent(MainActivity.this,MyPublishActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(MainActivity.this,SettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(MainActivity.this,AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            BmobUser.logOut();
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

/*************************************** 权限检查******************************************************/

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            BACK_LOCATION_PERMISSION
    };

    private static final int PERMISSON_REQUESTCODE = 0;

    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;

    @Override
    protected void onResume() {
        try{
            super.onResume();
            if (Build.VERSION.SDK_INT >= 23) {
                if (isNeedCheck) {
                    checkPermissions(needPermissions);
                }
            }
        }catch(Throwable e){
            e.printStackTrace();
        }
        mMapView.onResume();
    }

    /**
     * @param
     * @since 2.5.0
     */
    @TargetApi(23)
    private void checkPermissions(String... permissions) {
        try{
            if (Build.VERSION.SDK_INT >= 23 && getApplicationInfo().targetSdkVersion >= 23) {
                List<String> needRequestPermissonList = findDeniedPermissions(permissions);
                if (null != needRequestPermissonList
                        && needRequestPermissonList.size() > 0) {
                    try {
                        String[] array = needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]);
                        Method method = getClass().getMethod("requestPermissions", new Class[]{String[].class, int.class});
                        method.invoke(this, array, 0);
                    } catch (Throwable e) {

                    }
                }
            }

        }catch(Throwable e){
            e.printStackTrace();
        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    @TargetApi(23)
    private List<String> findDeniedPermissions(String[] permissions) {
        try{
            List<String> needRequestPermissonList = new ArrayList<String>();
            if (Build.VERSION.SDK_INT >= 23 && getApplicationInfo().targetSdkVersion >= 23) {
                for (String perm : permissions) {
                    if (checkMySelfPermission(perm) != PackageManager.PERMISSION_GRANTED
                            || shouldShowMyRequestPermissionRationale(perm)) {
                        if(!needCheckBackLocation
                                && BACK_LOCATION_PERMISSION.equals(perm)) {
                            continue;
                        }
                        needRequestPermissonList.add(perm);
                    }
                }
            }
            return needRequestPermissonList;
        }catch(Throwable e){
            e.printStackTrace();
        }
        return null;
    }

    private int checkMySelfPermission(String perm) {
        try {
            Method method = getClass().getMethod("checkSelfPermission", new Class[]{String.class});
            Integer permissionInt = (Integer) method.invoke(this, perm);
            return permissionInt;
        } catch (Throwable e) {
        }
        return -1;
    }

    private boolean shouldShowMyRequestPermissionRationale(String perm) {
        try {
            Method method = getClass().getMethod("shouldShowRequestPermissionRationale", new Class[]{String.class});
            Boolean permissionInt = (Boolean) method.invoke(this, perm);
            return permissionInt;
        } catch (Throwable e) {
        }
        return false;
    }

    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        try{
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }catch(Throwable e){
            e.printStackTrace();
        }
        return true;
    }

    @TargetApi(23)
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        try{
            if (Build.VERSION.SDK_INT >= 23) {
                if (requestCode == PERMISSON_REQUESTCODE) {
                    if (!verifyPermissions(paramArrayOfInt)) {
                        //showMissingPermissionDialog();
                        isNeedCheck = false;
                    }
                }
            }
        }catch(Throwable e){
            e.printStackTrace();
        }
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    private void showMissingPermissionDialog() {
        try{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("当前应用缺少必要权限。\n\n请点击\"设置\"-\"权限\"-打开所需权限");

            // 拒绝, 退出应用
            builder.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try{
                                finish();
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    });

            builder.setPositiveButton("设置",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                startAppSettings();
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    });

            builder.setCancelable(false);

            builder.show();
        }catch(Throwable e){
            e.printStackTrace();
        }
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        try{
            Intent intent = new Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
