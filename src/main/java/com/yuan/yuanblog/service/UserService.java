package com.yuan.yuanblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuan.yuanblog.vo.UserInfoVo;
import com.yuan.yuanblog.entity.User;

import java.util.List;

/**
 * 服务类
 */
public interface UserService extends IService<User> {
    /**
     * 查询所有用户（只含有部分信息）
     */
    List<UserInfoVo> getUserInfoList();

}
