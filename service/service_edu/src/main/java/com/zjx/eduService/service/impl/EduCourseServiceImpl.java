package com.zjx.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjx.eduService.entity.EduCourse;
import com.zjx.eduService.entity.EduCourseDescription;
import com.zjx.eduService.entity.frontvo.CourseFrontVo;
import com.zjx.eduService.entity.frontvo.CourseWebVo;
import com.zjx.eduService.entity.vo.CourseInfoVo;
import com.zjx.eduService.entity.vo.CoursePublishVo;
import com.zjx.eduService.mapper.EduCourseMapper;
import com.zjx.eduService.service.EduChapterService;
import com.zjx.eduService.service.EduCourseDescriptionService;
import com.zjx.eduService.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjx.eduService.service.EduVideoService;
import com.zjx.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-11-27
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private EduChapterService eduChapterService;
    //添加课程方法
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //向课程表添加信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int rows = baseMapper.insert(eduCourse);
        if(rows == 0) {
            throw new GuliException(20001, "添加课程失败");
        }
        String cid = eduCourse.getId();//获取添加课程之后id
        //向课程简介表添加信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(cid);//设置id相同
        eduCourseDescriptionService.save(eduCourseDescription);
        return cid;
    }

    //根据课程id查询课程基本信息

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

        //查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        //查询描述表
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    //修改课程信息

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update == 0) {
            throw new GuliException(20001, "修改课程信息失败");
        }

        //修改描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    //根据课程id查询课程确认信息
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = baseMapper.getCoursePublishVo(id);
        return publishCourseInfo;
    }

    //删除课程
    @Override
    public void removeCourse(String courseId) {
        eduVideoService.removeVideoByCourseId(courseId);
        eduChapterService.removeChapterByCourseId(courseId);
        eduCourseDescriptionService.removeById(courseId);
        int result = baseMapper.deleteById(courseId);
        if(result == 0) {
            throw new GuliException(20001, "删除失败");
        }
    }

    //条件查询带分页前台
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            wrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            wrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageParam, wrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;

    }

    //根据课程ID，编写SQL语句查询课程信息
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
