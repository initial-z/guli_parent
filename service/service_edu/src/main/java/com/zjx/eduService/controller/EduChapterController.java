package com.zjx.eduService.controller;


import com.zjx.commonutils.R;
import com.zjx.eduService.entity.EduChapter;
import com.zjx.eduService.entity.chapter.ChapterVo;
import com.zjx.eduService.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-27
 */
@RestController
@RequestMapping("/eduService/chapter")
//@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    //课程大纲列表,根据课程id查询
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {

        List<ChapterVo> list = eduChapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("AllChapterVideo", list);
    }

    //添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {

        eduChapterService.save(eduChapter);
        return R.ok();
    }

    //根据章节ID查询
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {

        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("eduChapter", eduChapter);
    }

    //修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    //删除方法
    @DeleteMapping("{chapterId}")
    public R deleteChapterId(@PathVariable String chapterId) {
        boolean flag = eduChapterService.deleteChapter(chapterId);
        if(flag) {
            return R.ok();
        }else {
            return R.error();
        }

    }

}

