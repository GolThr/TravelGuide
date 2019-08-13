package com.golthr.travelguide;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Affects {
    public static void Immersive(Activity activity){
        Window window = activity.getWindow();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){//6.0 以全透明状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏的字体为黑色
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.TRANSPARENT);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// 5.0 以上全透明状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static boolean checkEmail(String email){
        String mailRegex,mailName,mailDomain;
        Pattern pattern;
        Matcher matcher;
        mailName="^[0-9a-z]+\\w*";
        mailDomain="([0-9a-z]+\\.)+[0-9a-z]+$";
        mailRegex=mailName+"@"+mailDomain;
        pattern= Pattern.compile(mailRegex);
        matcher=pattern.matcher(email);
        return matcher.matches();
    }
}
