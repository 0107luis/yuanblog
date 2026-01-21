package com.yuan.yuanblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuan.yuanblog.vo.UserInfoVo;
import com.yuan.yuanblog.entity.User;
import com.yuan.yuanblog.mapper.UserMapper;
import com.yuan.yuanblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 查询所有用户（只含有部分信息）
     */
    @Override
    public List<UserInfoVo> getUserInfoList(){
        return userMapper.getUserInfo();
    }
}
