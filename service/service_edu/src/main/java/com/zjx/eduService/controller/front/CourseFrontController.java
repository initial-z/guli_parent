package com.zjx.eduService.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjx.commonutils.JwtUtils;
import com.zjx.commonutils.R;
import com.zjx.commonutils.ordervo.CourseWebVoOrder;
import com.zjx.eduService.client.OrdersClient;
import com.zjx.eduService.entity.EduCourse;
import com.zjx.eduService.entity.chapter.ChapterVo;
import com.zjx.eduService.entity.frontvo.CourseFrontVo;
import com.zjx.eduService.entity.frontvo.CourseWebVo;
import com.zjx.eduService.service.EduChapterService;
import com.zjx.eduService.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduService/coursefront")
//@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private OrdersClient ordersClient;

    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page, limit);
        Map<String, Object> map = eduCourseService.getCourseFrontList(pageCourse, courseFrontVo);


        return R.ok().data(map);
    }

    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request) {
        CourseWebVo courseWebVo = eduCourseService.getBaseCourseInfo(courseId);
        List<ChapterVo> chapterVideoList = eduChapterService.getChapterVideoByCourseId(courseId);

        boolean buyCourse = ordersClient.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVideoList).data("isBuy", buyCourse);
    }

    //根据课程id查询课程信息
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id) {
        CourseWebVo courseInfo = eduCourseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }
}
