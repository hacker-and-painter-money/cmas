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
        return super.list(queryWrapper.ne(ChatGroup::getStatus, 1));

    }
    public List<ChatGroup> list(String name, Long type) {
        LambdaQueryWrapper<ChatGroup> wrapper = Wrappers.<ChatGroup>lambdaQuery().like(ChatGroup::getName, name);
        if (type != null) {
            wrapper.eq(ChatGroup::getType, type);
        }
        if (name != null) {
            wrapper.like(ChatGroup::getName, name);
        }
        return list(wrapper);
    }

    public List<ChatGroup> getByName(String name) {
        return list(Wrappers.<ChatGroup>lambdaQuery().eq(ChatGroup::getName, name));
    }

    public List<ChatGroup> listByUserId(Long userId, boolean except) {
        List<ChatGroupUserRelation> chatGroupUserRelationList;
        chatGroupUserRelationList = chatGroupUserRelationService.list(null, userId);
        List<ChatGroup> chatGroupList = new ArrayList<>();
        List<ChatGroup> finalChatGroupList = chatGroupList;
        chatGroupUserRelationList.forEach(r -> {
            ChatGroup chatGroup = getById(r.getGroupId());
            finalChatGroupList.add(chatGroup);
        });
        if (except) {
            List<ChatGroup> list = list();
            list.removeIf(chatGroupList::contains);
            chatGroupList = list;
        }
        for (ChatGroup chatGroup : chatGroupList) {
            if (chatGroup.getType() == 0) {
                String[] split = chatGroup.getName().split("-");
                Long id = Long.parseLong(Long.parseLong(split[0]) == userId ? split[1] : split[0]);
                User user = userService.getById(id);
                chatGroup.setName(user.getUsername());
            }
        }
        return chatGroupList;
    }

}
