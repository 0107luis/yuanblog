package com.yuan.yuanblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuan.yuanblog.entity.Type;
import com.yuan.yuanblog.mapper.TypeMapper;
import com.yuan.yuanblog.service.TypeService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

}
