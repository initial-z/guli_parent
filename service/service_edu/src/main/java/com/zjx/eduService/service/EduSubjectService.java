package com.zjx.eduService.service;

import com.zjx.eduService.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjx.eduService.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-11-26
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    List<OneSubject> getAllSOneTwoSubject();
}
