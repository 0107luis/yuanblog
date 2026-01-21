package com.yuan.yuanblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuan.yuanblog.vo.PageCommentVo;
import com.yuan.yuanblog.entity.Comment;
import com.yuan.yuanblog.mapper.CommentMapper;
import com.yuan.yuanblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    /**
     * 通过博客id和父评论id查找所有子评论 并按照时间倒序排序
     */
    @Override
    public List<PageCommentVo> getPageCommentListByDesc(Long blogId, Long parentCommentId) {
        return commentMapper.getPageCommentListByPageAndParentCommentIdByDesc(blogId, parentCommentId);
    }

    /**
     * 通过博客id和父评论id查找所有子评论
     */
    @Override
    public List<PageCommentVo> getPageCommentList(Long blogId, Long parentCommentId) {
        return commentMapper.getPageCommentListByPageAndParentCommentId(blogId, parentCommentId);
    }

    @Override
    public Double getCommentCount(Long blogId) {
        return commentMapper.queryCountNum(blogId);
    }

}
