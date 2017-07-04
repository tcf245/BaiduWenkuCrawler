package com.bfd.web.sys.controller;

import com.bfd.crawl.BaiduwenkuCrawler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by tcf24 on 2017/7/3.
 */
@RestController
@RequestMapping("/baidu")
public class BaiduWenkuController {
    private static final Log LOG = LogFactory.getLog(BaiduWenkuController.class);


    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @ResponseBody
    public String test(){
        return "OK!";
    }

    @RequestMapping(value = "/crawler",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity crawler(@RequestParam("u") String u) throws IOException {
        String path = "";
        try {
            path = BaiduwenkuCrawler.crawlerTxt(u);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return download(path.replace("etc/",""),new File(path));
    }

    public ResponseEntity<byte[]> download(String fileName, File file) throws IOException {
        String dfileName = new String(fileName.getBytes("gbk"), "iso8859-1");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", dfileName);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
//        return new ResponseEntity<List<String>>(FileUtils.readLines(file), headers, HttpStatus.CREATED);
    }

}
