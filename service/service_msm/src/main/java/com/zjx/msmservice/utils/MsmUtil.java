package com.zjx.msmservice.utils;

import java.io.IOException;
import com.alibaba.fastjson.JSONException;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

public class MsmUtil {
    public static String sendSMS(String phoneNumber,String code) {
        String reStr = ""; //定义返回值
        // 短信应用SDK AppID   // 1400开头       
        int appid = 0000;
        // 短信应用SDK AppKey        
        String appkey = "";
        // 短信模板ID，需要在短信应用中申请       
        int templateId = 0000;
        // 签名，使用的是`签名内容`，而不是`签名ID`        
        String smsSign = "0000";
        try {
            //参数，一定要对应短信模板中的参数顺序和个数， 
            String[] params = {code, "5"};

            //创建ssender对象
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            //发送
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumber,templateId, params, smsSign, "", "");
            // 签名参数未提供或者为空时，会使用默认签名发送短信            
            System.out.println(result.toString());
            if(result.result==0){
                reStr = "success";
            }else{
                reStr = "error";
            }
        } catch (HTTPException e) {
            // HTTP响应码错误           
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误            
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误            
            e.printStackTrace();
        }catch (Exception e) {
            // 网络IO错误            
            e.printStackTrace();
        }
        return reStr;
    }

}
