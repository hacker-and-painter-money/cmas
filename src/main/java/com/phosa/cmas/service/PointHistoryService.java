package com.phosa.cmas.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phosa.cmas.mapper.PointHistoryMapper;
import com.phosa.cmas.model.PointHistory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointHistoryService extends ServiceImpl<PointHistoryMapper, PointHistory> {
    public List<PointHistory> list() {
        return list(Wrappers.emptyWrapper());
    }
    public List<PointHistory> list(QueryWrapper<PointHistory> queryWrapper) {
        return super.list(queryWrapper.lambda().eq(false, PointHistory::getStatus, 1));
    }
    public List<PointHistory> list(Long userId, int page, int pageSize) {
        LambdaQueryWrapper<PointHistory> wrapper = Wrappers.<PointHistory>lambdaQuery();
        if (userId != null) {
            wrapper.eq(PointHistory::getUserId, userId);
        }
        return list(wrapper.last("limit " + pageSize * (page - 1) + ", " + pageSize));
    }


}
