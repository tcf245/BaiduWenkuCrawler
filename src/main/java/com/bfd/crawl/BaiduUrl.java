package com.bfd.crawl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tcf24 on 2017/7/3.
 */
public class BaiduUrl {
    String url = "https://wapwenku.baidu.com/view/cfcbe0926bec0975f465e294.html?pn=1&pu=###";
    private final String REG = "/view/(\\w+).html";

    private String host;
    private String hashCode;
    private int pageNum;

    public BaiduUrl(String url) {
        Matcher m = Pattern.compile(REG).matcher(url);
        if (m.find()){
            this.host = "wapwenku.baidu.com";
            this.hashCode = m.group(1);
            this.pageNum = 1;
        }else{
            this.pageNum = 1;
            System.out.println("init url error");
        }
    }

    public BaiduUrl pageNext(){
        this.pageNum++;
        return this;
    }

    @Override
    public String toString() {
        return "https://" +
                this.host +
                "/view/" +
                this.hashCode +
                ".html?pn=" +
                this.pageNum +
                "&pu=###";
    }

    public static void main(String[] args) {
        String url = "https://wenku.baidu.com/view/cfcbe0926bec0975f465e294.html?pn=%22%20+%20i%20+%20%22&pu=###";
        BaiduUrl bdUrl = new BaiduUrl(url);
        System.out.println(bdUrl.toString());
        for (int i = 0; i < 60; i++) {
            System.out.println(bdUrl.pageNext());
        }

    }

}
