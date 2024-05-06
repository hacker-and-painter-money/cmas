package com.phosa.cmas.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phosa.cmas.mapper.UserMapper;
import com.phosa.cmas.model.PointHistory;
import com.phosa.cmas.model.Resource;
import com.phosa.cmas.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    public List<User> list() {
        return list(Wrappers.lambdaQuery());
    }
    public List<User> list(LambdaQueryWrapper<User> queryWrapper) {
        return super.list(queryWrapper.eq(false, User::getStatus, 1));
    }
    public List<User> getByUsername(String username) {
        return super.list(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
    }
    public List<User> list(String username, int page, int pageSize) {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if (username != null) {
            wrapper.like(User::getUsername, username);
        }
        return list(wrapper.last("limit " + pageSize * (page - 1) + ", " + pageSize));
    }

}
