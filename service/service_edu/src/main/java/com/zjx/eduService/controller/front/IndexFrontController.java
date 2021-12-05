package com.zjx.eduService.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjx.commonutils.R;
import com.zjx.eduService.entity.EduCourse;
import com.zjx.eduService.entity.EduTeacher;
import com.zjx.eduService.service.EduCourseService;
import com.zjx.eduService.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduService/indexfront")
//@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduTeacherService eduTeacherService;

    //查询前8条热门记录， 查询前四条名师
    @GetMapping("index")
    public R index() {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> eduCoursesList = eduCourseService.list(wrapper);

        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> eduTeacherList = eduTeacherService.list(wrapperTeacher);

        return R.ok().data("eduCourseList", eduCoursesList).data("eduTeacherList",eduTeacherList);
    }
}
