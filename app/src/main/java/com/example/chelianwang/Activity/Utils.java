package com.example.chelianwang.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.Map;
public class Utils {
    public static boolean saveUserInfo(Context context, String number,
                                       String password) {
        SharedPreferences sp = context.getSharedPreferences("data",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("userName", number);
        edit.putString("pwd", password);
        edit.commit();
        return true;
    }

    // 从data.xml文件中获取存储的QQ号码和密码
    public static Map<String, String> getUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("data",
                Context.MODE_PRIVATE);
        String number = sp.getString("userName", null);
        String password = sp.getString("pwd", null);
        Map<String, String> userMap = new HashMap<String, String>();
        userMap.put("number", number);
        userMap.put("password", password);
        return userMap;
    }
    public static boolean saveCarInfo(Context context, String carid
                                       ) {
        SharedPreferences sp = context.getSharedPreferences("data2",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("carid", carid);
        edit.commit();
        return true;
    }

    // 从data.xml文件中获取carid
    public static Map<String, String> getCarInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("data2",
                Context.MODE_PRIVATE);
        String carid = sp.getString("carid", null);
        Map<String, String> userMap = new HashMap<String, String>();
        userMap.put("carid",carid);
        return userMap;
    }

}
