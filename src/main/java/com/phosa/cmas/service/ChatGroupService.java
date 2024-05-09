package com.phosa.cmas.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phosa.cmas.mapper.ChatGroupMapper;
import com.phosa.cmas.model.ChatGroup;
import com.phosa.cmas.model.ChatGroupUserRelation;
import com.phosa.cmas.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatGroupService extends ServiceImpl<ChatGroupMapper, ChatGroup> {
    @Autowired
    ChatGroupUserRelationService chatGroupUserRelationService;
    @Autowired
    private UserService userService;

    public List<ChatGroup> list() {
        return list(Wrappers.emptyWrapper());
    }
    public List<ChatGroup> list(LambdaQueryWrapper<ChatGroup> queryWrapper) {
        return super.list(queryWrapper.eq(false, ChatGroup::getStatus, 1));

    }
    public List<ChatGroup> list(String name, Long type, int page, int pageSize) {
        LambdaQueryWrapper<ChatGroup> wrapper = Wrappers.<ChatGroup>lambdaQuery().like(ChatGroup::getName, name);
        if (type != null) {
            wrapper.eq(ChatGroup::getType, type);
        }
        if (name != null) {
            wrapper.like(ChatGroup::getName, name);
        }
        return list(wrapper.last("limit " + pageSize * (page - 1) + ", " + pageSize));
    }

    public List<ChatGroup> getByName(String name) {
        return list(Wrappers.<ChatGroup>lambdaQuery().eq(ChatGroup::getName, name));
    }

    public List<ChatGroup> listByUserId(Long userId, int page, int pageSize) {
        List<ChatGroupUserRelation> chatGroupUserRelationList = chatGroupUserRelationService.list(null, userId, page, pageSize);
        List<ChatGroup> chatGroupList = new ArrayList<>();
        chatGroupUserRelationList.forEach(r -> {
            ChatGroup chatGroup = getById(r.getGroupId());
            if (chatGroup.getType() == 0) {
                String[] split = chatGroup.getName().split("-");
                Long id = Long.parseLong(Long.parseLong(split[0]) == userId ? split[1] : split[0]);
                User user = userService.getById(id);
                chatGroup.setName(user.getUsername());
            }
            chatGroupList.add(chatGroup);
        });
        return chatGroupList;
    }

}
