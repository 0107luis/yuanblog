package com.yuan.yuanblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuan.yuanblog.vo.Visitor;

/**
 *  服务类
 */
public interface VisitorService extends IService<Visitor> {
    /**
     * 通过uuid查询是否存在是该uuid的访客
     */
    boolean hasUUID(String uuid);

    /**
     * 通过uuid查询访客
     */
    Visitor getVisitorByUuid(String uuid);

    /**
     * 获取Pv
     */
    int getPv();
}
