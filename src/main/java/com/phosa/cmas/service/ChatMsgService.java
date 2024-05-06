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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatMsgService extends ServiceImpl<ChatMsgMapper, ChatMsg> {
    public List<ChatMsg> list() {
        return list(Wrappers.lambdaQuery());
    }
    public List<ChatMsg> list(Long groupId, Long senderId, String content, Long parentMsgId, int page, int pageSize) {
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
        return list(wrapper.last("limit " + pageSize * (page - 1) + ", " + pageSize));
    }
    public List<ChatMsg> list(LambdaQueryWrapper<ChatMsg> queryWrapper) {
        return super.list(queryWrapper.eq(false, ChatMsg::getStatus, 1));
    }
    public List<ChatMsg> listByGroupId(Long groupId) {
        return list(Wrappers.<ChatMsg>lambdaQuery().eq(ChatMsg::getGroupId, groupId));
    }

}
