package com.zjx.staservice.controller;


import com.zjx.commonutils.R;
import com.zjx.staservice.client.UcenterClient;
import com.zjx.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-12-03
 */
@RestController
@RequestMapping("/staservice/sta")
//@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService staService;


    //统计某一天注册人数
    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day) {
        staService.registerCount(day);
        return R.ok();
    }

    //图表显示，获得日期数据和json数组
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type, @PathVariable String begin,
                      @PathVariable String end) {
        Map<String, Object> map = staService.getShowData(type, begin, end);

        return R.ok().data(map);
    }

}

