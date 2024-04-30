package com.phosa.cmas.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phosa.cmas.mapper.ChatGroupMapper;
import com.phosa.cmas.model.ChatGroup;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatGroupService extends ServiceImpl<ChatGroupMapper, ChatGroup> {
    public List<ChatGroup> list() {
        return list(Wrappers.emptyWrapper());
    }
    public List<ChatGroup> list(QueryWrapper<ChatGroup> queryWrapper) {
        return super.list(queryWrapper.lambda().eq(false, ChatGroup::getStatus, 1));

    }
    public List<ChatGroup> list(String name, Long type, int page, int pageSize) {
        LambdaQueryWrapper<ChatGroup> wrapper = Wrappers.<ChatGroup>lambdaQuery().like(ChatGroup::getName, name);
        if (type != null) {
            wrapper.eq(ChatGroup::getType, type);
        }
        return list(wrapper.last("limit " + pageSize * (page - 1) + ", " + pageSize));
    }

    public ChatGroup getByName(String name) {
        return list(Wrappers.<ChatGroup>lambdaQuery().eq(ChatGroup::getName, name)).get(0);
    }


}
