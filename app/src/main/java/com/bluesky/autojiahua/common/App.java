package com.bluesky.autojiahua.common;

import android.app.Application;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * @author BlueSky
 * @date 2022/7/4
 * Description:
 */
public class App extends Application {
    private static App app;

    private static SharedPreferences mPreferences;
    public static final String SP_NAME = "auto_preference";
    //详情页的全-简切换按钮常量与记录
    public static boolean DETAIL_PAGE_SIMPLIFY = false;
    public static final String SP_PARAM_SIMPLE = "detail_page_simplify";
    //home查询页的区域下拉常量与记录
    public static int HOME_SPINNER_DOMAIN = 0;
    public static final String SP_PARAM_SPINNER_DOMAIN = "home_spinner_domain";
    //home查询页的搜索下拉常量与记录
    public static int HOME_SPINNER_SEARCH = 0;
    public static final String SP_PARAM_SPINNER_SEARCH = "home_spinner_search";


    //详情页的区域对照map
    public static final Map<String, String> DOMAIN_DISPLAY;

    static {
        DOMAIN_DISPLAY = new HashMap<>();
        DOMAIN_DISPLAY.put("chuleng", "初冷");
        DOMAIN_DISPLAY.put("cubenzhengliu", "粗苯蒸馏");
        DOMAIN_DISPLAY.put("dianbujiaoyou", "电捕焦油");
        DOMAIN_DISPLAY.put("gufengji", "鼓风机");
        DOMAIN_DISPLAY.put("jiaoyouanshui", "焦油氨水");
        DOMAIN_DISPLAY.put("liuan", "硫铵");
        DOMAIN_DISPLAY.put("tuoliu", "脱硫");
        DOMAIN_DISPLAY.put("youku", "油库");
        DOMAIN_DISPLAY.put("zhengan", "蒸氨");
        DOMAIN_DISPLAY.put("zhonglengxiben", "终冷洗苯");
        DOMAIN_DISPLAY.put("yuchuli", "预处理");
        DOMAIN_DISPLAY.put("fenshao", "焚烧");
        DOMAIN_DISPLAY.put("jinghua", "净化");
        DOMAIN_DISPLAY.put("zhuanhua", "转化");
        DOMAIN_DISPLAY.put("ganxiweixi", "干吸尾吸");
        DOMAIN_DISPLAY.put("ganxijiaobenti_1", "1#干熄焦");
        DOMAIN_DISPLAY.put("ganxijiaobenti_2", "2#干熄焦");
        DOMAIN_DISPLAY.put("ganxijiaobenti_3", "3#干熄焦");
        DOMAIN_DISPLAY.put("ganxijiaochuchen", "干熄焦除尘");
        DOMAIN_DISPLAY.put("ganxijiaoguolu_1", "1#干熄焦锅炉");
        DOMAIN_DISPLAY.put("ganxijiaoguolu_2", "2#干熄焦锅炉");
        DOMAIN_DISPLAY.put("ganxijiaoguolu_3", "3#干熄焦锅炉");
        DOMAIN_DISPLAY.put("guolugeishui", "锅炉给水");
        DOMAIN_DISPLAY.put("jiaolu_1_2", "1#2#焦炉");
        DOMAIN_DISPLAY.put("qilunfadian", "汽轮发电");

    }

    /*用作keyword转换的domain*/
    public static final String[] DOMAIN = new String[]{
            "",//代表所有区域
            "chuleng",
            "cubenzhengliu",
            "dianbujiaoyou",
            "gufengji",
            "jiaoyouanshui",
            "liuan",
            "tuoliu",
            "youku",
            "zhengan",
            "zhonglengxiben",
            "yuchuli",
            "fenshao",
            "jinghua",
            "zhuanhua",
            "ganxiweixi",
            "ganxijiaobenti_1",
            "ganxijiaobenti_2",
            "ganxijiaobenti_3",
            "ganxijiaochuchen",
            "ganxijiaoguolu_1",
            "ganxijiaoguolu_2",
            "ganxijiaoguolu_3",
            "guolugeishui",
            "jiaolu_1_2",
            "qilunfadian"
    };
    /*用作keyword转换的search*/
    public static final String[] SEARCH = new String[]{"tag", "affect", "name", "standard", "type"};

    public static App getInstance() {
        return app;
    }

    public static void putDomain(int domain) {
        mPreferences.edit().putInt(SP_PARAM_SPINNER_DOMAIN, domain).apply();
        HOME_SPINNER_DOMAIN = domain;
    }

    public static void putSearch(int search) {
        mPreferences.edit().putInt(SP_PARAM_SPINNER_SEARCH, search).apply();
        HOME_SPINNER_SEARCH = search;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        //Todo 初始化全局变量
        mPreferences = getSharedPreferences(SP_NAME, MODE_PRIVATE);
        DETAIL_PAGE_SIMPLIFY = mPreferences.getBoolean(SP_PARAM_SIMPLE, false);
        HOME_SPINNER_DOMAIN = mPreferences.getInt(SP_PARAM_SPINNER_DOMAIN, 0);
        HOME_SPINNER_SEARCH = mPreferences.getInt(SP_PARAM_SPINNER_SEARCH, 0);
    }

    public static void setSimply(boolean value) {
        DETAIL_PAGE_SIMPLIFY = value;
        mPreferences.edit().putBoolean(SP_PARAM_SIMPLE, value).apply();

    }
}
