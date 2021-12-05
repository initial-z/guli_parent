package com.zjx.educenter.controller;


import com.zjx.commonutils.JwtUtils;
import com.zjx.commonutils.R;
import com.zjx.commonutils.ordervo.UcenterMemberOrder;
import com.zjx.educenter.entity.UcenterMember;
import com.zjx.educenter.entity.vo.RegisterVo;
import com.zjx.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-30
 */
@RestController
@RequestMapping("/educenter/member")
//@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    //登录
    @PostMapping ("login")
    public R loginUser(@RequestBody UcenterMember ucenterMember) {
        String token = ucenterMemberService.login(ucenterMember);
        return R.ok().data("token", token);
    }

    //注册
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        ucenterMemberService.register(registerVo);
        return R.ok();
    }

    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {

        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = ucenterMemberService.getById(memberId);
        return R.ok().data("userInfo", member);
    }

    //根据token字符串获取用户信息
    @PostMapping("getInfoUc/{courseId}")
    public UcenterMember getInfo(@PathVariable String courseId) {
        //根据用户id获取用户信息
        UcenterMember member = ucenterMemberService.getById(courseId);
        return member;

    }

    @GetMapping("getUcenter/{memberId}")
    public UcenterMember getUcenterById(@PathVariable("memberId") String memberId){
        UcenterMember member = ucenterMemberService.getById(memberId);
        return member;
    }

    //根据用户id获取用户信息
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id) {
        UcenterMember member = ucenterMemberService.getById(id);
        //把member对象里面值复制给UcenterMemberOrder对象
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    //查询某一天注册人数
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day) {
        Integer count = ucenterMemberService.countRegisterDay(day);
        return R.ok().data("countRegister", count);
    }
}

