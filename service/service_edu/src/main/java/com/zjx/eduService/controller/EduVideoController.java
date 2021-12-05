package com.zjx.eduService.controller;


import com.zjx.commonutils.R;
import com.zjx.eduService.client.VodClient;
import com.zjx.eduService.entity.EduChapter;
import com.zjx.eduService.entity.EduVideo;
import com.zjx.eduService.service.EduVideoService;
import com.zjx.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-27
 */
@RestController
@RequestMapping("/eduService/video")
//@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private VodClient vodClient;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        EduVideo eduVideo = eduVideoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();

        if(!StringUtils.isEmpty(videoSourceId)) {
            vodClient.removeAlyVideo(videoSourceId);
        }

        eduVideoService.removeById(id);
        return R.ok();
    }

    //根据小节ID查询
    @GetMapping("getVideoInfo/{id}")
    public R getVideoInfo(@PathVariable String id) {

        EduVideo eduVideo = eduVideoService.getById(id);
        return R.ok().data("eduVideo", eduVideo);
    }

    //修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }


}

