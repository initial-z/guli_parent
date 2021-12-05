package com.zjx.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.zjx.commonutils.R;
import com.zjx.servicebase.exceptionhandler.GuliException;
import com.zjx.vod.service.VodService;
import com.zjx.vod.utils.ConstantVodUtils;
import com.zjx.vod.utils.InitVodCilent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.POST;
import java.util.List;

import static com.zjx.vod.utils.InitVodCilent.initVodClient;

@RestController
@RequestMapping("/eduvod/video")
//@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    //上传视频到阿里云
    @PostMapping("uploadAliyunVideo")
    public R uploadAliyunVideo(MultipartFile file) {
        String videoId = vodService.uploadVideoAly(file);
        return R.ok().data("videoId", videoId);
    }

    //根据视频ID删除视频
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id) {
        try {
            DefaultAcsClient client = InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
            return R.ok();
        }catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }

    }

    //删除多个阿里云视频的方法
    //参数多个视频id  List videoIdList
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMoreAlyVideo(videoIdList);
        return R.ok();
    }

    //根据视频id播放视频
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            DefaultAcsClient client
                    = initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth", playAuth);
        }catch (Exception e) {
            throw new GuliException(20001, "获取凭证失败");
        }
    }
}
