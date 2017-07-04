package com.bfd.crawl;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

/**
 * Created by tcf24 on 2017/6/29.
 */
public class BaiduwenkuCrawler {

    public static String crawlerTxt(String u) throws IOException, InterruptedException {
        StringBuffer sb = new StringBuffer();
        BaiduUrl url = new BaiduUrl(u);
        String title = "";

        boolean isNext = true;
        while(isNext){
            Document doc = Jsoup.connect(url.toString()).get();
            Elements content = doc.select("div.reader div.content p.txt");
            if ("".equals(title))
                title = doc.select("div.doctitlebar h1").text();
            content.forEach(e -> sb.append(e.text().replace("第\\d+ / \\d+页","") + "\n"));

            try{
                if (doc.select("div.pagebox").text().contains("下页")){
                    url = url.pageNext();
                    System.out.println(url);
                }else{
                    isNext = false;
                }
            }catch (Exception e){
                isNext = false;
            }

            Thread.sleep(1000);
        }

        System.out.println(sb.toString());
        if ("".equals(title)){
            return "";
        }
        FileUtils.writeStringToFile(new File("etc/" + title + ".txt"),sb.toString(),"utf-8");
        return "etc/" + title + ".txt";
    }


    public static void main(String[] args) throws InterruptedException, IOException {
        crawlerTxt("https://wapwenku.baidu.com/view/cfcbe0926bec0975f465e294.html?pn=%22%20+%20i%20+%20%22&pu=###");
    }
}
