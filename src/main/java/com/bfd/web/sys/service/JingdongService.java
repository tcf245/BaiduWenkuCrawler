package com.bfd.web.sys.service;

import com.bfd.crawl.MessageBean;
import com.bfd.crawl.jingdong.JingDongMain;
import com.bfd.crawl.util.EmptyUtil;
import com.bfd.web.util.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import  static  com.bfd.web.util.Constants.gson;

/**
 * Created by BFD_303 on 2017/5/8.
 */
@Service("jingdongService")
public class JingdongService {
    private static final Log LOG = LogFactory.getLog(JingdongService.class);

    private JingDongMain jingdongMain = JingDongMain.getINSTANCE();

    public MessageBean getResult(String username,String password, String yzm){
        Map<String,Object> request = new HashMap<String,Object>();
        request.put("sessionID", username);
        request.put("interact", false);
        request.put("validate", false);
        request.put("site", "jingdong");
        request.put("username", username);
        request.put("password", password);
        request.put("yzm", yzm);

        LOG.info("开始调用");

        try{
            if (!EmptyUtil.isEmpty(yzm)){
                //请求携带验证码
                Map<String,Object> result = jingdongMain.interactQuery(gson.toJson(request));
                LOG.info(result);
                return new MessageBean((String) result.get("msgCode"), (String) result.get("message"),result);
            }else{
                //没有验证码
                Map<String,Object> result = jingdongMain.getImage(gson.toJson(request));

                // 处理过程出错
                if (result.containsKey("msgCode")){
                    String code = (String) result.get("msgCode");
                    String message = (String) result.get("message");
                    return new MessageBean(code,message,null);
                }else{
                    return new MessageBean(Constants.success,"ok",result);
                }
            }
        }catch (Exception e){
            LOG.info("error" + e);
            return new MessageBean(Constants.othorWrong,"处理流程错误",null);
        }
    }

}
