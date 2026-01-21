package com.yuan.yuanblog.controller.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.yuanblog.common.constant.RedisKeyConfig;
import com.yuan.yuanblog.vo.Result;
import com.yuan.yuanblog.entity.Type;
import com.yuan.yuanblog.service.RedisService;
import com.yuan.yuanblog.service.TypeService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类前端控制器
 */
@RestController
public class TypeController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private RedisService redisService;

    /**
     * 查询所有分类
     */
    @GetMapping("/types")
    public Result queryTypesList() {
        if (redisService.hasHashKey(RedisKeyConfig.CATEGORY_NAME_CACHE, RedisKeyConfig.All)) {
            return Result.succ(redisService.getValueByHashKey(RedisKeyConfig.CATEGORY_NAME_CACHE, RedisKeyConfig.All));
        }
        List<Type> list = typeService.list(new QueryWrapper<Type>());
        redisService.saveKVToHash(RedisKeyConfig.CATEGORY_NAME_CACHE, RedisKeyConfig.All, list);
        return Result.succ(list);
    }

    /**
     * 后台 - 分类管理， 分页查询分类
     */
    @RequiresAuthentication
    @RequiresPermissions("user:read")
    @GetMapping("/type/list")
    public Result queryTypesListByPage(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page page = new Page(currentPage, pageSize);
        IPage pageData = typeService.page(page, new QueryWrapper<Type>());
        return Result.succ(pageData);
    }

    /**
     * 后台 - 分类管理 - 添加分类， 增加分类
     */
    @RequiresAuthentication
    @RequiresPermissions("user:create")
    @PostMapping("/type/create")
    public Result saveType(@Validated @RequestBody Type type){
        if(type==null){
            return Result.fail("不能为空");
        } else{
            typeService.saveOrUpdate(type);
            redisService.deleteCacheByKey(RedisKeyConfig.CATEGORY_NAME_CACHE);
            return Result.succ(null);
        }
    }

    /**
     * 后台 - 分类管理 - 编辑， 修改分类
     */
    @RequiresAuthentication
    @RequiresPermissions("user:update")
    @PostMapping("/type/update")
    public Result updateType(@Validated @RequestBody Type type){
        if(type==null){
            return Result.fail("不能为空");
        } else{
            typeService.saveOrUpdate(type);
            redisService.deleteCacheByKey(RedisKeyConfig.CATEGORY_NAME_CACHE);
            return Result.succ(null);
        }
    }

    /**
     * 后台 - 分类管理 - 删除， 删除分类
     */
    @RequiresRoles("role_root")
    @RequiresAuthentication
    @RequiresPermissions("user:delete")
    @GetMapping("/type/delete/{id}")
    public Result deleteTypeById(@PathVariable(name = "id") Long id) {
        if (typeService.removeById(id)) {
            redisService.deleteCacheByKey(RedisKeyConfig.CATEGORY_NAME_CACHE);
            return Result.succ(null);
        } else {
            return Result.fail("删除失败");
        }
    }

}
