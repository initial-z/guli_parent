package com.zjx.educms.controller;


import com.zjx.commonutils.R;
import com.zjx.educms.entity.CrmBanner;
import com.zjx.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-30
 */
@RestController
@RequestMapping("/educms/bannerfront")
//@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = crmBannerService.selectAllBanner();
        return R.ok().data("list", list);
    }

}

