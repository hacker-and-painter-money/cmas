package com.phosa.cmas.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phosa.cmas.mapper.UserMapper;
import com.phosa.cmas.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Autowired
    ChatGroupUserRelationService chatGroupUserRelationService;
    @Autowired
    private ChatGroupService chatGroupService;

    public List<User> list() {
        return list(Wrappers.lambdaQuery());
    }
    public List<User> list(LambdaQueryWrapper<User> queryWrapper) {
        return super.list(queryWrapper.ne(User::getStatus, 1));
    }
    public List<User> getByUsername(String username) {
        return super.list(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
    }
    public List<User> list(String username, Long exceptFriendId, int page, int pageSize) {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if (username != null) {
            wrapper.like(User::getUsername, username);
        }
        List<User> list = list(wrapper.last("limit " + pageSize * (page - 1) + ", " + pageSize));
        if (exceptFriendId != null) {
            List<ChatGroup> exceptList = chatGroupService.list(Wrappers.<ChatGroup>lambdaQuery().eq(ChatGroup::getType, 0).like(ChatGroup::getName, exceptFriendId));
            Set<Long> exceptIds = new HashSet<>();
            for (ChatGroup exceptChatGroup : exceptList) {
                String[] split = exceptChatGroup.getName().split("-");
                exceptIds.add(Long.parseLong(split[0]));
                exceptIds.add(Long.parseLong(split[1]));
            }
            list.removeIf(user -> exceptIds.contains(user.getId()));
        }
        return list;
    }

}
