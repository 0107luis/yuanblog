package com.yuan.yuanblog.controller.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.yuanblog.common.constant.RedisKeyConfig;
import com.yuan.yuanblog.vo.Result;
import com.yuan.yuanblog.entity.Tag;
import com.yuan.yuanblog.service.RedisService;
import com.yuan.yuanblog.service.TagService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签前端控制器
 */
@RestController
public class TagController {

    @Autowired
    private TagService tagService;
    @Autowired
    private RedisService redisService;

    /**
     * 查询所有标签
     */
    @GetMapping("/tags/all")
    public Result queryTagsList() {
        if (redisService.hasHashKey(RedisKeyConfig.TAG_NAME_CACHE, RedisKeyConfig.All)) {
            return Result.succ(redisService.getValueByHashKey(RedisKeyConfig.TAG_NAME_CACHE, RedisKeyConfig.All));
        }
        List<Tag> tagList = tagService.queryTagsList();
        redisService.saveKVToHash(RedisKeyConfig.TAG_NAME_CACHE, RedisKeyConfig.All, tagList);
        return Result.succ(tagList);
    }

    /**
     * 后台 - 标签管理， 分页查询标签
     */
    @RequiresAuthentication
    @RequiresPermissions("user:read")
    @GetMapping("/tags/list")
    public Result queryTagsListByPage(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page page = new Page(currentPage, pageSize);
        IPage pageData = tagService.page(page, new QueryWrapper<>());
        return Result.succ(pageData);
    }

    /**
     * 后台 - 标签管理 - 添加标签， 增加标签
     */
    @RequiresAuthentication
    @RequiresPermissions("user:create")
    @PostMapping("/tag/create")
    public Result saveTag(@Validated @RequestBody Tag tag) {
        if (tag == null) {
            return Result.fail("不能为空");
        } else {
            tagService.saveOrUpdate(tag);
            redisService.deleteCacheByKey(RedisKeyConfig.TAG_NAME_CACHE);
            return Result.succ(null);
        }
    }

    /**
     * 后台 - 标签管理 - 编辑， 修改标签
     */
    @RequiresAuthentication
    @RequiresPermissions("user:update")
    @PostMapping("/tag/update")
    public Result updateTag(@Validated @RequestBody Tag tag) {
        if (tag == null) {
            return Result.fail("不能为空");
        } else {
            tagService.saveOrUpdate(tag);
            redisService.deleteCacheByKey(RedisKeyConfig.TAG_NAME_CACHE);
            return Result.succ(null);
        }
    }

    /**
     * 后台 - 标签管理 - 删除， 删除标签
     */
    @RequiresRoles("role_root")
    @RequiresAuthentication
    @RequiresPermissions("user:delete")
    @PostMapping("/tag/delete/{id}")
    public Result deleteTagById(@PathVariable("id") Long id) {
        if (tagService.removeById(id)) {
            redisService.deleteCacheByKey(RedisKeyConfig.TAG_NAME_CACHE);
            return Result.succ(null);
        } else {
            return Result.fail("删除失败");
        }
    }
}
