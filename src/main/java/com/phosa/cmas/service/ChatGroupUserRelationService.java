package com.phosa.cmas.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phosa.cmas.mapper.ChatGroupUserRelationMapper;
import com.phosa.cmas.model.ChatGroupUserRelation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatGroupUserRelationService extends ServiceImpl<ChatGroupUserRelationMapper, ChatGroupUserRelation> {
    public List<ChatGroupUserRelation> list() {
        return list(Wrappers.emptyWrapper());
    }
    public List<ChatGroupUserRelation> list(QueryWrapper<ChatGroupUserRelation> queryWrapper) {
        return super.list(queryWrapper.lambda().ne(ChatGroupUserRelation::getStatus, 1));
    }
    public List<ChatGroupUserRelation> list(Long groupId, Long userId, int page, int pageSize) {
        LambdaQueryWrapper<ChatGroupUserRelation> wrapper = Wrappers.<ChatGroupUserRelation>lambdaQuery();
        if (groupId != null) {
            wrapper.like(ChatGroupUserRelation::getGroupId, groupId);
        }
        if (userId != null) {
            wrapper.eq(ChatGroupUserRelation::getUserId, userId); 
        }
        return list(wrapper.last("limit " + pageSize * (page - 1) + ", " + pageSize));
    }

    public boolean exist(Long userId, Long groupId) {
        return !list(
                    Wrappers.<ChatGroupUserRelation>lambdaQuery()
                    .eq(ChatGroupUserRelation::getGroupId, groupId)
                    .eq(ChatGroupUserRelation::getUserId, userId)
                )
                .isEmpty();
    }
}
