package com.phosa.cmas.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phosa.cmas.mapper.ReplyMapper;
import com.phosa.cmas.model.Reply;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService extends ServiceImpl<ReplyMapper, Reply> {
    public List<Reply> list() {
        return list(Wrappers.emptyWrapper());
    }
    public List<Reply> list(QueryWrapper<Reply> queryWrapper) {
        return super.list(queryWrapper.lambda().eq(false, Reply::getStatus, 1));
    }
    public List<Reply> list(Long questionId, Long replyId, Long senderId, int page, int pageSize) {
        LambdaQueryWrapper<Reply> wrapper = Wrappers.<Reply>lambdaQuery();
        if (questionId != null) {
            wrapper.eq(Reply::getQuestionId, questionId);
        }
        if (replyId != null) {
            wrapper.eq(Reply::getParentReplyId, replyId); 
        }
        if (senderId != null) {
            wrapper.eq(Reply::getSenderId, senderId);
        }
        return list(wrapper.last("limit " + pageSize * (page - 1) + ", " + pageSize));
    }
}
