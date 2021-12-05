package com.zjx.eduorder.service;

import com.zjx.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-12-02
 */
public interface PayLogService extends IService<PayLog> {

    Map createNatvie(String orderNo);

    Map<String, String> queryPayStatus(String orderNo);

    void updateOrdersStatus(Map<String, String> map);
}
