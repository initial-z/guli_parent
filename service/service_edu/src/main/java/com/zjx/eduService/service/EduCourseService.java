package com.zjx.eduService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjx.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjx.eduService.entity.frontvo.CourseFrontVo;
import com.zjx.eduService.entity.frontvo.CourseWebVo;
import com.zjx.eduService.entity.vo.CourseInfoVo;
import com.zjx.eduService.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-11-27
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);

    void removeCourse(String courseId);

    Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
