package com.yuan.yuanblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuan.yuanblog.entity.Tag;

import java.util.List;

/**
 * 服务类
 */
public interface TagService extends IService<Tag> {

    List<Tag> queryHotTagsByLimit(int limit);

    List<Tag> queryTagsList();
}
