package com.zjx.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjx.commonutils.R;
import com.zjx.educms.entity.CrmBanner;
import com.zjx.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-30
 */
@RestController
@RequestMapping("/educms/banneradmin")
//@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;

    //分页查询banner
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit) {
        Page<CrmBanner> pageBanner = new Page<>(page, limit);
        crmBannerService.page(pageBanner, null);

        return R.ok().data("items", pageBanner.getRecords()).data("total", pageBanner.getTotal());
    }

    //添加Banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        crmBannerService.save(crmBanner);
        return R.ok();
    }

    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = crmBannerService.getById(id);
        return R.ok().data("item", banner);
    }

    @PutMapping("update")
    public R updateById(@RequestBody CrmBanner banner) {
        crmBannerService.updateById(banner);
        return R.ok();
    }

    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        crmBannerService.removeById(id);
        return R.ok();
    }
}

