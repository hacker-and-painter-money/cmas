package com.phosa.cmas.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phosa.cmas.mapper.ReplyMapper;
import com.phosa.cmas.model.Reply;
import com.phosa.cmas.model.User;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ReplyService extends ServiceImpl<ReplyMapper, Reply> {
    private final UserService userService;

    public ReplyService(UserService userService) {
        this.userService = userService;
    }

    public List<Reply> list() {
        return list(Wrappers.lambdaQuery());
    }
    public List<Reply> list(LambdaQueryWrapper<Reply> queryWrapper) {
        List<Reply> replyList = super.list(queryWrapper.ne(Reply::getStatus, 1));
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        replyList.forEach(reply -> {
            User user = userService.getById(reply.getSenderId());
            reply.setSenderName(user.getUsername());
            if (reply.getParentReplyId() != null) {
                reply.setParentReplyContent(getById(reply.getParentReplyId()).getContent());
            }
            reply.setCreateAtFormated(sdf.format(reply.getCreatedAt()));
            if (reply.getUpdatedAt() != null) {
                reply.setUpdateAtFormated(sdf.format(reply.getUpdatedAt()));
            }
        });
        return replyList;
    }
    public List<Reply> list(Long questionId, Long replyId, Long senderId) {
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
        return list(wrapper);
    }
}
