package com.phosa.cmas.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phosa.cmas.mapper.ChatMsgMapper;
import com.phosa.cmas.model.ChatGroup;
import com.phosa.cmas.model.ChatGroupUserRelation;
import com.phosa.cmas.model.ChatMsg;
import com.phosa.cmas.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatMsgService extends ServiceImpl<ChatMsgMapper, ChatMsg> {
    private final UserService userService;

    public ChatMsgService(UserService userService) {
        this.userService = userService;
    }

    public List<ChatMsg> list() {
        return list(Wrappers.lambdaQuery());
    }
    public List<ChatMsg> list(Long groupId, Long senderId, String content, Long parentMsgId) {
        LambdaQueryWrapper<ChatMsg> wrapper = Wrappers.lambdaQuery();
        if (groupId != null) {
            wrapper.eq(ChatMsg::getGroupId, groupId);
        }
        if (senderId != null) {
            wrapper.eq(ChatMsg::getSenderId, senderId);
        }
        if (content != null) {
            wrapper.like(ChatMsg::getContent, content);
        }
        if (parentMsgId != null) {
            wrapper.eq(ChatMsg::getParentMsgId, parentMsgId);
        }
        return list(wrapper);
    }
    public List<ChatMsg> list(LambdaQueryWrapper<ChatMsg> queryWrapper) {
        List<ChatMsg> chatMsgList = super.list(queryWrapper.eq(false, ChatMsg::getStatus, 1));
        chatMsgList.forEach(chatMsg -> {
            User user = userService.getById(chatMsg.getSenderId());
            chatMsg.setSenderName(user.getUsername());
            if (chatMsg.getParentMsgId() != null) {
                chatMsg.setParentMsgContent(getById(chatMsg.getParentMsgId()).getContent());
            }
        });
        return chatMsgList;
    }


}
