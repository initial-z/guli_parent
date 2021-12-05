package com.zjx.eduService.controller;


import com.zjx.commonutils.R;
import com.zjx.eduService.entity.subject.OneSubject;
import com.zjx.eduService.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-26
 */
@RestController
@RequestMapping("/eduService/subject")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    //获取到上传文件，读取文件内容
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {

        eduSubjectService.saveSubject(file, eduSubjectService);
        return R.ok();
    }

    //课程分类列表（树形）
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        List<OneSubject> list = eduSubjectService.getAllSOneTwoSubject();
        return R.ok().data("list", list);
    }

}

