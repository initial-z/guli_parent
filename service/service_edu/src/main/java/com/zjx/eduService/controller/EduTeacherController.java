package com.zjx.eduService.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjx.commonutils.R;
import com.zjx.eduService.entity.EduTeacher;
import com.zjx.eduService.entity.vo.TeacherQuery;
import com.zjx.eduService.service.EduTeacherService;
import com.zjx.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-23
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduService/teacher")
//@CrossOrigin
public class EduTeacherController {

    //把service注入
    @Autowired
    private EduTeacherService eduTeacherService;

    //查询讲师表所有数据
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

    //逻辑删除讲师的方法
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)@PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        if(flag) {
            return R.ok();
        }  else {
            return R.error();
        }
    }

    //分页查询方法
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit) {

        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        eduTeacherService.page(pageTeacher,null);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        Map map = new HashMap();
        map.put("total",total);
        map.put("records",records);

        return R.ok().data(map);
    }

    //条件查询带分页的方法
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level) ) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }

        //排序
        wrapper.orderByDesc("gmt_create");
        eduTeacherService.page(pageTeacher, wrapper);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        return R.ok().data("total",total).data("records", records);

    }

    //添加讲师接口的方法
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        if(save) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    //根据讲师ID查询
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {

        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);
    }

    //讲师修改功能
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {

        try {
            int a = 1/0;
        }catch (Exception e) {
            throw new GuliException(20001, "执行了自定义异常处理");
        }
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if(flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }

}

