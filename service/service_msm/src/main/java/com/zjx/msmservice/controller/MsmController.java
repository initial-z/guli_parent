package com.zjx.msmservice.controller;


import com.zjx.commonutils.R;
import com.zjx.msmservice.utils.MsmUtil;
import com.zjx.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
//@CrossOrigin
public class MsmController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("send/{phone}")
    public R send(@PathVariable String phone){
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return R.ok();
        }
        code = RandomUtil.getFourBitRandom();
        String result = MsmUtil.sendSMS(phone, code);
        if(result.equals("success")){
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok();
        }else{
            return R.error().message("发送短信失败");
        }
    }

}
