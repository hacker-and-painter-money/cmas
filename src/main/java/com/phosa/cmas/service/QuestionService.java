package com.phosa.cmas.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phosa.cmas.mapper.QuestionMapper;
import com.phosa.cmas.model.Question;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class QuestionService extends ServiceImpl<QuestionMapper, Question> {
    private final UserService userService;

    public QuestionService(UserService userService) {
        this.userService = userService;
    }

    public List<Question> list() {
        return list(Wrappers.lambdaQuery());
    }
    public List<Question> list(LambdaQueryWrapper<Question> queryWrapper) {
        List<Question> questionList = super.list(queryWrapper.ne(Question::getStatus, 1));
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        questionList.forEach(question -> {
            question.setSenderName(userService.getById(question.getSenderId()).getUsername());
            question.setCreateAtFormated(sdf.format(question.getCreatedAt()));
            if (question.getUpdatedAt() != null) {
                question.setUpdateAtFormated(sdf.format(question.getUpdatedAt()));
            }
        });
        return questionList;
    }
    public List<Question> list(String title, String content, Long senderId) {
        LambdaQueryWrapper<Question> wrapper = Wrappers.lambdaQuery();
        if (senderId != null) {
            wrapper.eq(Question::getSenderId, senderId);
        }
        if (title != null) {
            wrapper.like(Question::getTitle, title);
        }
        if (content != null) {
            wrapper.like(Question::getContent, content);
        }
        return list(wrapper);
    }
}
