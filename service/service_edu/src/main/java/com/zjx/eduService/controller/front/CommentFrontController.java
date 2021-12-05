package com.zjx.eduService.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjx.commonutils.JwtUtils;
import com.zjx.commonutils.R;
import com.zjx.eduService.client.UcenterClient;
import com.zjx.eduService.entity.EduComment;
import com.zjx.eduService.service.EduCommentService;
import com.zjx.educenter.entity.UcenterMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduService/comment")
//@CrossOrigin
public class CommentFrontController {

    @Autowired
    private EduCommentService eduCommentService;
    @Autowired
    private UcenterClient ucenterClient;

    //评论分页查询方法
    @GetMapping("{page}/{limit}")
    public R pageListComment(@PathVariable long page,
                                 @PathVariable long limit,
                             String courseId) {

        Page<EduComment> pageParam = new Page<>(page, limit);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        eduCommentService.page(pageParam,wrapper);


        List<EduComment> commentList = pageParam.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());

        return R.ok().data(map);
    }

    //添加评论
    @PostMapping("auth/save")
    public R save(@RequestBody EduComment comment, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)) {
            return R.error().code(28004).message("请登录");
        }
        comment.setMemberId(memberId);
        UcenterMember ucenterInfo = ucenterClient.getUcenterById(memberId);
        comment.setNickname(ucenterInfo.getNickname());
        comment.setAvatar(ucenterInfo.getAvatar());
        eduCommentService.save(comment);
        return R.ok();
    }

}
