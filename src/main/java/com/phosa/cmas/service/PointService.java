package com.phosa.cmas.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phosa.cmas.mapper.PointMapper;
import com.phosa.cmas.model.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointService extends ServiceImpl<PointMapper, Point> {
    public List<Point> list() {
        return list(Wrappers.lambdaQuery());
    }
    public List<Point> list(LambdaQueryWrapper<Point> queryWrapper) {
        return super.list(queryWrapper.ne(Point::getStatus, 1));
    }
    public List<Point> getByUserId(Long userId) {
        QueryWrapper<Point> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return list(wrapper);
    }
}
