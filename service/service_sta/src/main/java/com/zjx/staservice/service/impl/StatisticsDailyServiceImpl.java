package com.zjx.staservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjx.commonutils.R;
import com.zjx.staservice.client.UcenterClient;
import com.zjx.staservice.entity.StatisticsDaily;
import com.zjx.staservice.mapper.StatisticsDailyMapper;
import com.zjx.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-12-03
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void registerCount(String day) {
        //添加记录之前删除相同日期的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);
        //远程调用得到某一人数
        R registerR = ucenterClient.countRegister(day);
        Integer countRegister = (Integer) registerR.getData().get("countRegister");
        //把获取信息添加到数据库中，统计分析表里面
        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(countRegister);
        sta.setDateCalculated(day);

        sta.setVideoViewNum(RandomUtils.nextInt(100,200));
        sta.setLoginNum(RandomUtils.nextInt(100,200));
        sta.setCourseNum(RandomUtils.nextInt(100,200));
        baseMapper.insert(sta);
    }

    //图表显示
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated", type);
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);
        //创建两个集合：日期和数量
        List<String> dateList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        //遍历集合，添加数据
        for (int i = 0; i < staList.size(); i++) {
            StatisticsDaily daily = staList.get(i);
            dateList.add(daily.getDateCalculated());
            switch (type) {
                case "login_num":
                    numList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    numList.add(daily.getVideoViewNum());
                    break;
                case "course_num ":
                    numList.add(daily.getCourseNum());
                    break;
                case "register_num":
                    numList.add(daily.getRegisterNum());
                    break;
                default:
                    break;
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("date_calculatedList", dateList);
        map.put("numDataList", numList);
        return map;
    }
}
