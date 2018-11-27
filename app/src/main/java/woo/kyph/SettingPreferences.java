package woo.kyph;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

/**
 * 설정값 저장
 */

class SettingPreferences {
    private SharedPreferences pref;

    SettingPreferences(Context mainContext) {
        pref = mainContext.getSharedPreferences("setting", MODE_PRIVATE);
    }

    //값 불러오기(부울)
    Boolean getBoolean(String key){

        Boolean defaultSetting;
        switch (key) {
            case "firstStart":
                defaultSetting = false;
                break;
            default:
                throw new UnknownError("Setting getBoolean : 값 불러오기 오류" + "key : " + key);
        }

        return pref.getBoolean(key , defaultSetting);
    }

    //값 저장하기(부울)
    void saveBoolean(String key, Boolean bool){
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, bool);
        editor.apply();
    }

    //설정 불러오기(int)
    int getInt(String key) {
        int defaultSet;
        switch (key) {
            case "hour":
                defaultSet = 7;
                break;
            default:
                throw new UnknownError("Setting getInt : 값 불러오기 오류" + "key : " + key);
        }

        return pref.getInt(key, defaultSet);
    }

    //설정 설정하기(int)
    void saveInt(String key, int num) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, num);
        editor.apply();
    }

    //값 불러오기(String)
    String getString(String key){
        String defaultSetting;
        switch (key) {
            case "name":
                defaultSetting = " ";
                break;
            default:
                throw new UnknownError("Setting getString : 값 불러오기 오류" + "key : " + key);
        }

        return pref.getString(key , defaultSetting);
    }

    //설정 저장하기(String)
    void saveString(String key, String str) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, str);
        editor.apply();
    }
}