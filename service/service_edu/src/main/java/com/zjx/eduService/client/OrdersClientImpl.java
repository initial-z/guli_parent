package com.zjx.eduService.client;

import org.springframework.stereotype.Service;


@Service
public class OrdersClientImpl implements OrdersClient {
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        return false;
    }
}
