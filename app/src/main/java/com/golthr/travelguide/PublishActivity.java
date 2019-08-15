package com.golthr.travelguide;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.golthr.travelguide.util.DensityUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.bmob.v3.BmobUser;


public class PublishActivity extends AppCompatActivity {
    private ImageView im_more;
    private EditText et_article;
    private TextView tv_myLocation;
    private ImageView iv_back_btn;
    private RelativeLayout rl_getLocation;

    //head
    private ImageView iv_func_btn_main;
    private TextView tv_main_title;
    private TextView tv_func;

    //Dialog
    private TextView tv_take_photo;
    private TextView tv_gallery;
    private EditText et_article_title;
    private Button btn_publish;

    private ImageView ivHead;//头像显示
    private TextView btnTakephoto;//拍照
    private TextView btnPhotos;//相册
    private Bitmap head;//头像Bitmap
    private static String path = "/sdcard/DemoHead/";

    //数据
    private String article = "";
    private String location = "";
    private String title = "";

    //定位
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    System.out.println(aMapLocation.getLocationType());//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    System.out.println(aMapLocation.getLatitude());//获取纬度
                    System.out.println(aMapLocation.getLongitude());//获取经度
                    System.out.println(aMapLocation.getAccuracy());//获取精度信息
                    System.out.println(aMapLocation.getAddress());//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    System.out.println(aMapLocation.getCountry());//国家信息
                    System.out.println(aMapLocation.getProvince());//省信息
                    System.out.println(aMapLocation.getCity());//城市信息
                    System.out.println(aMapLocation.getDistrict());//城区信息
                    System.out.println(aMapLocation.getStreet());//街道信息
                    System.out.println(aMapLocation.getStreetNum());//街道门牌号信息
                    System.out.println(aMapLocation.getCityCode());//城市编码
                    System.out.println(aMapLocation.getAdCode());//地区编码
                    System.out.println(aMapLocation.getAoiName());//获取当前定位点的AOI信息
                    System.out.println(aMapLocation.getBuildingId());//获取当前室内定位的建筑物Id
                    System.out.println(aMapLocation.getFloor());//获取当前室内定位的楼层
                    System.out.println(aMapLocation.getGpsAccuracyStatus());//获取GPS的当前状态
                    if(tv_myLocation != null){
                        location = aMapLocation.getAoiName();
                        tv_myLocation.setText(location);
                    }
                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Affects.setStatusBarFontBlack(PublishActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        initView();
        im_more = (ImageView)findViewById(R.id.picture);
        im_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog();
            }
        });

        iv_func_btn_main = (ImageView)findViewById(R.id.iv_func_btn_main);
        tv_main_title = (TextView)findViewById(R.id.tv_main_title);
        tv_func = (TextView)findViewById(R.id.tv_func);
        iv_func_btn_main.setVisibility(View.GONE);
        tv_func.setVisibility(View.VISIBLE);
        tv_main_title.setText("发布攻略");
        tv_func.setText("发布");
        iv_back_btn = (ImageView)findViewById(R.id.iv_back_btn);
        et_article = (EditText)findViewById(R.id.et_article);
        tv_myLocation = (TextView)findViewById(R.id.tv_myLocation);
        rl_getLocation = (RelativeLayout)findViewById(R.id.rl_getLocation);

        iv_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_func.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                article = et_article.getText().toString().trim();

                if(!article.equals("")){
                    if(!location.equals("")){
                        showPublishDialog();
                    }else {
                        Toast.makeText(PublishActivity.this, "请输入设置您的位置", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(PublishActivity.this, "请输入攻略内容", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rl_getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
                initLocation();
            }
        });

        initLocation();
    }

    private void initView() {
        ivHead = (ImageView) findViewById(R.id.picture);
        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");//从Sd中找头像，转换成Bitmap
        if (bt != null) {
            //如果本地有头像图片的话
            ivHead.setImageBitmap(bt);
        } else {
            //如果本地没有头像图片则从服务器取头像，然后保存在SD卡中，本Demo的网络请求头像部分忽略
        }
    }

    private void initLocation(){
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if(null != mLocationClient){
            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //从相册里面取相片的返回结果
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }

                break;
            //相机拍照后的返回结果
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));//裁剪图片
                }

                break;
            //调用系统裁剪图片后
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);//保存在SD卡中
                        ivHead.setImageBitmap(head);//用ImageView显示出来
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showPublishDialog() {
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_set_title, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(this, 16f);
        params.bottomMargin = DensityUtil.dp2px(this, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();

        Window window = bottomDialog.getWindow();
        et_article_title = (EditText)window.findViewById(R.id.et_article_title);
        btn_publish = (Button)window.findViewById(R.id.btn_publish);

        //发布攻略Bmob_publishArticle_impl
        //数据：用户userId，攻略内容article，图片images，位置location
        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobUser user = BmobUser.getCurrentUser(BmobUser.class);
                String userId = user.getObjectId();
                String title = et_article_title.getText().toString().trim();

                if(!title.equals("")){
                    //发布攻略

                    Toast.makeText(PublishActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(PublishActivity.this, "请输入攻略标题", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        //找到指定URI对应的资源图片
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        //进入系统裁剪图片的界面
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd卡是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建以此File对象为名（path）的文件夹
        String fileName = path + "head.jpg";//图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件（compress：压缩）

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void showBottomDialog() {
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_publish, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(this, 16f);
        params.bottomMargin = DensityUtil.dp2px(this, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();

        Window window = bottomDialog.getWindow();
        tv_take_photo = (TextView)window.findViewById(R.id.tv_take_photo);
        tv_gallery = (TextView)window.findViewById(R.id.tv_gallery);
        TextView tv_cancel =(TextView)window.findViewById(R.id.tv_cancel);

        tv_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.hide();
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);//返回被选中项的URI
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");//得到所有图片的URI
                //                System.out.println("MediaStore.Images.Media.EXTERNAL_CONTENT_URI  ------------>   "
                //                        + MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//   content://media/external/images/media
                startActivityForResult(intent1, 1);
            }
        });
        tv_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.hide();
                //最好用try/catch包裹一下，防止因为用户未给应用程序开启相机权限，而使程序崩溃
                try {
                    Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//开启相机应用程序获取并返回图片（capture：俘获）
                    intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                            "head.jpg")));//指明存储图片或视频的地址URI
                    startActivityForResult(intent2, 2);//采用ForResult打开
                } catch (Exception e) {
                    Toast.makeText(PublishActivity.this, "相机无法启动，请先开启相机权限", Toast.LENGTH_LONG).show();
                }
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.hide();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
    }
}
