package com.zjx.eduService.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjx.eduService.entity.EduSubject;
import com.zjx.eduService.entity.excel.SubjectData;
import com.zjx.eduService.entity.subject.OneSubject;
import com.zjx.eduService.entity.subject.TwoSubject;
import com.zjx.eduService.listener.SubjectExcelListener;
import com.zjx.eduService.mapper.EduSubjectMapper;
import com.zjx.eduService.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-11-26
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {

        try{
            InputStream is = file.getInputStream();
            EasyExcel.read(is, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //课程分类列表（树形）
    @Override
    public List<OneSubject> getAllSOneTwoSubject() {

        //查询一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", 0);
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        //查询二级分类
        QueryWrapper<EduSubject> wrapperTWO = new QueryWrapper<>();
        wrapperTWO.ne("parent_id", 0);
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTWO);

        List<OneSubject> finalSubjectList = new ArrayList<>();

        //封装一级分类
        for (int i = 0; i < oneSubjectList.size(); i++) {
            EduSubject eduSubject = oneSubjectList.get(i);
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject, oneSubject);
            finalSubjectList.add(oneSubject);

            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            for (int m = 0; m < twoSubjectList.size(); m++) {
                EduSubject tSubject = twoSubjectList.get(m);
                if(tSubject.getParentId().equals(eduSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject, twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoFinalSubjectList);
        }


        return finalSubjectList;
    }
}
