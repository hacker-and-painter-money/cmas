package com.phosa.cmas.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phosa.cmas.mapper.QuestionMapper;
import com.phosa.cmas.model.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService extends ServiceImpl<QuestionMapper, Question> {
    public List<Question> list() {
        return list(Wrappers.emptyWrapper());
    }
    public List<Question> list(QueryWrapper<Question> queryWrapper) {
        return super.list(queryWrapper.lambda().eq(false, Question::getStatus, 1));
    }
    public List<Question> list(String title, String content, Long senderId, int page, int pageSize) {
        LambdaQueryWrapper<Question> wrapper = Wrappers.<Question>lambdaQuery().like(Question::getTitle, title).like(Question::getContent, content);
        if (senderId != null) {
            wrapper.eq(Question::getSenderId, senderId);
        }
        return list(wrapper.last("limit " + pageSize * (page - 1) + ", " + pageSize));
    }
}
