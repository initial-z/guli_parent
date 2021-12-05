package com.zjx.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjx.commonutils.JwtUtils;
import com.zjx.commonutils.MD5;
import com.zjx.educenter.entity.UcenterMember;
import com.zjx.educenter.entity.vo.RegisterVo;
import com.zjx.educenter.mapper.UcenterMemberMapper;
import com.zjx.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjx.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-11-30
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(UcenterMember ucenterMember) {

        String mobile = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();

        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "登录失败");
        }

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        if(mobileMember == null) {
            throw new GuliException(20001, "手机号不存在");
        }

        if(!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new GuliException(20001, "密码错误");
        }

        if(mobileMember.getIsDisabled()) {
            throw new GuliException(20001, "该用户已被禁用");
        }

        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
             || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)) {
            throw new GuliException(20001, "注册失败");
        }

        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)) {
            throw new GuliException(20001, "验证码错误");
        }

        QueryWrapper<UcenterMember>  wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0) {
            throw new GuliException(20001, "不能重复注册");
        }

        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setNickname(nickname);
        ucenterMember.setIsDisabled(false);
        ucenterMember.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(ucenterMember);

    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    //查询一天注册人数

    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
