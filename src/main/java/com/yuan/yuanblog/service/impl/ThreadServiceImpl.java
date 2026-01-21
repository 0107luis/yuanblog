package com.yuan.yuanblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuan.yuanblog.entity.Blog;
import com.yuan.yuanblog.service.BlogService;
import com.yuan.yuanblog.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class ThreadServiceImpl implements ThreadService {

    @Autowired
    private BlogService blogService;

    @Override
    @Async("taskExecutor")
    public void updateViewCount(Blog blog) {
        Blog blogUpdate = new Blog();
        blogUpdate.setViews(blog.getViews() + 1);
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Blog::getId, blog.getId());
        queryWrapper.eq(Blog::getViews, blog.getViews());
        blogService.update(blogUpdate, queryWrapper);
    }
}
