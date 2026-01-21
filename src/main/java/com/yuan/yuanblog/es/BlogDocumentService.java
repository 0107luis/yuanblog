package com.yuan.yuanblog.es;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.yuanblog.entity.Blog;
import com.yuan.yuanblog.mq.BlogMQIndexMessage;

import java.util.List;

public interface BlogDocumentService {
    /*
        分页搜索
     */
    IPage searchData(Page page, String keyword);

    /*
        es初始化
     */
    int initData(List<Blog> records);

    /*
        添加或更新索引
     */
    void createOrUpdateIndex(BlogMQIndexMessage message);

    /*
        删除索引
     */
    void deleteIndex(BlogMQIndexMessage message);
}
