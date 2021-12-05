package com.zjx.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjx.eduService.entity.EduChapter;
import com.zjx.eduService.entity.EduVideo;
import com.zjx.eduService.entity.chapter.ChapterVo;
import com.zjx.eduService.entity.chapter.VideoVo;
import com.zjx.eduService.mapper.EduChapterMapper;
import com.zjx.eduService.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjx.eduService.service.EduVideoService;
import com.zjx.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-11-27
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;
    //课程大纲列表，根据id进行查询
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        //根据课程Id查询所有章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        //根据课程id查询课程里面所有小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(wrapperVideo);

        List<ChapterVo> finalList = new ArrayList<>();
        //遍历查询章节list集合进行封装
        for (int i = 0; i < eduChapterList.size(); i++) {
            EduChapter eduChapter = eduChapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            finalList.add(chapterVo);

            List<VideoVo> videoVoList = new ArrayList<>();
            //遍历小节List集合进行封装
            for (int m = 0; m < eduVideoList.size(); m++) {
                EduVideo eduVideo = eduVideoList.get(m);
                if(eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVoList.add(videoVo);
                }
            }

            chapterVo.setChildren(videoVoList);
        }


        return finalList;
    }

    //删除章节

    @Override
    public boolean deleteChapter(String chapterId) {
        //根据章节ID查询小节表， 如果查询数据， 不删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = eduVideoService.count(wrapper);

        if(count > 0) {
            throw new GuliException(20001, "不能删除");
        } else {
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }
    }

    //根据课程ID删除章节
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
