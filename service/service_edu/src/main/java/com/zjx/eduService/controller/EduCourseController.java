package com.zjx.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjx.commonutils.R;
import com.zjx.eduService.entity.EduCourse;
import com.zjx.eduService.entity.EduTeacher;
import com.zjx.eduService.entity.vo.CourseInfoVo;
import com.zjx.eduService.entity.vo.CoursePublishVo;
import com.zjx.eduService.entity.vo.CourseQuery;
import com.zjx.eduService.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-27
 */
@RestController
@RequestMapping("/eduService/course")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    //课程列表 基本实现
    @GetMapping
    public R getCourseList() {
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("list", list);
    }

    //分页查询方法
    @GetMapping("pageCourse/{current}/{limit}")
    public R pageListCourse(@PathVariable long current,
                             @PathVariable long limit) {

        Page<EduCourse> pageCourse = new Page<>(current, limit);

        eduCourseService.page(pageCourse, null);

        long total = pageCourse.getTotal();
        List<EduCourse> records = pageCourse.getRecords();


        Map map = new HashMap();
        map.put("total",total);
        map.put("records",records);

        return R.ok().data(map);
    }

    //条件查询带分页的方法
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable long current, @PathVariable long limit,
                                 @RequestBody(required = false) CourseQuery courseQuery) {
        Page<EduCourse> pageCourse = new Page<>(current, limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();

        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status) ) {
            wrapper.eq("status", status);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }

        //排序
        wrapper.orderByDesc("gmt_create");
        eduCourseService.page(pageCourse,wrapper);

        long total = pageCourse.getTotal();
        List<EduCourse> records = pageCourse.getRecords();

        return R.ok().data("total",total).data("records", records);

    }

    //添加课程基本信息方法
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {

        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    //根据id查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = eduCourseService.publishCourseInfo(id);
        return R.ok().data("publishCourse", coursePublishVo);
    }

    //课程最终发布 修改课程状态
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    //删除课程
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        eduCourseService.removeCourse(courseId);
        return R.ok();
    }
}

