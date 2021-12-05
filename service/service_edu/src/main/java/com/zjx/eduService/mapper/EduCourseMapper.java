package com.zjx.eduService.mapper;

import com.zjx.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjx.eduService.entity.frontvo.CourseWebVo;
import com.zjx.eduService.entity.vo.CoursePublishVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-11-27
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVo getCoursePublishVo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
