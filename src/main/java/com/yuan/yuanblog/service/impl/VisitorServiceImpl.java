package com.yuan.yuanblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuan.yuanblog.vo.Visitor;
import com.yuan.yuanblog.mapper.VisitorMapper;
import com.yuan.yuanblog.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 */
@Service
public class VisitorServiceImpl extends ServiceImpl<VisitorMapper, Visitor> implements VisitorService {
    @Autowired
    private VisitorMapper visitorMapper;

    /**
     * 通过uuid查询是否存在是该uuid的访客
     */
    @Override
    public boolean hasUUID(String uuid) {
        return visitorMapper.hasUUID(uuid) != 0;
    }

    /**
     * 通过uuid查询访客
     */
    @Override
    public Visitor getVisitorByUuid(String uuid){
        return visitorMapper.selectByUuid(uuid);
    }

    /**
     * 获取Pv
     */
    @Override
    public int getPv(){
        return  visitorMapper.getPv();
    }


}
