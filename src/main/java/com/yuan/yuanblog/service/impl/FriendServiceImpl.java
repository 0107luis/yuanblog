package com.yuan.yuanblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuan.yuanblog.entity.Friend;
import com.yuan.yuanblog.mapper.FriendMapper;
import com.yuan.yuanblog.service.FriendService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 */
@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements FriendService {

}
