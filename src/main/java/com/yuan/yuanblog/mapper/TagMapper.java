package com.yuan.yuanblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuan.yuanblog.entity.Tag;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 */
public interface TagMapper extends BaseMapper<Tag> {

    List<Long> selectHotTagsIdsByLimit(int limit);

    List<Tag> selectHotTagsByHotTagsId(List<Long> hotTagsIds);
}
