package com.bfd.web.util;

import com.bfd.crawler.utils.DataUtil;
import com.google.gson.Gson;

/**
 * Created by BFD_303 on 2017/5/8.
 */
public class Constants {
    public static final Gson gson = new Gson();
    public static final String success = "0";
    public static final String paramsWrong = "20001";
    public static final String wrongWebDriver = "40001"; //未找到上次的WebDriver对象，可能被销毁
    public static final String needImgeCode = "40002" ;  //需要图片验证码
    public static final String imageCodeWrong = "40003"; //验证码错误
    public static final String cookieWrong = "40004";
    public static final String othorWrong = "40005" ;  //流程问题
    public static final String usernameWrong = "40010" ;  //用户名或密码错误
    public static final String timeout = "40011" ;  //登录超时
    public static final String parseWrong = "50001";  //下载或解析失败

}
