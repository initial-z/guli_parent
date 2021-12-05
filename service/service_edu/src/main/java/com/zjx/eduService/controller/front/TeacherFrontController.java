package com.zjx.eduService.controller.front;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjx.commonutils.R;
import com.zjx.eduService.entity.EduCourse;
import com.zjx.eduService.entity.EduTeacher;
import com.zjx.eduService.service.EduCourseService;
import com.zjx.eduService.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduService/teacherfront")
//@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;

    //分页查询讲师
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page,  @PathVariable long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(page, limit);
        Map<String, Object> map = eduTeacherService.getTeacherFrontList(pageTeacher);
        return R.ok().data(map);
    }

    //讲师详情的功能
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId) {
        EduTeacher teacher = eduTeacherService.getById(teacherId);

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<EduCourse> courseList = eduCourseService.list(wrapper);


        return R.ok().data("teacher",teacher).data("courseList", courseList);
    }
}
