package com.phosa.cmas.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phosa.cmas.mapper.ResourceMapper;
import com.phosa.cmas.model.PointHistory;
import com.phosa.cmas.model.Question;
import com.phosa.cmas.model.Reply;
import com.phosa.cmas.model.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ResourceService extends ServiceImpl<ResourceMapper, Resource> {
    public List<Resource> list() {
        return list(Wrappers.lambdaQuery());
    }
    public List<Resource> list(LambdaQueryWrapper<Resource> queryWrapper) {
        return super.list(queryWrapper.ne(Resource::getStatus, 1));
    }
    public List<Resource> list(String title, String tag, String ownerId) {
        LambdaQueryWrapper<Resource> wrapper = Wrappers.<Resource>lambdaQuery();
        if (title != null) {
            wrapper.like(Resource::getTitle, title);
        }
        if (tag != null) {
            wrapper.eq(Resource::getTag, tag);
        }
        if (ownerId != null) {
            wrapper.eq(Resource::getOwnerId, ownerId);
        }
        return list(wrapper);

    }
}
