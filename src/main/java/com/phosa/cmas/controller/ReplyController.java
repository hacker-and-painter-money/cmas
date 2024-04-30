package com.phosa.cmas.controller;

import com.phosa.cmas.model.Reply;
import com.phosa.cmas.service.ReplyService;
import com.phosa.cmas.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    ReplyService replyService;

    @GetMapping("")
    public ResponseEntity<?> getReplyList(@RequestParam(name = "question_id") Long questionId,
                                                    @RequestParam(name = "reply_id") Long replyId,
                                                    @RequestParam(name = "sender_id") Long senderId,
                                                    @RequestParam(name = "page", defaultValue = "1") int page,
                                                    @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        List<Reply> replies = replyService.list(questionId, replyId, senderId, page, pageSize);
        return ResponseUtil.getSuccessResponse(replies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReply(@PathVariable int id) {
        Reply reply = replyService.getById(id);
        return ResponseUtil.getSuccessResponse(reply);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReply(@PathVariable int id) {
        Reply reply = replyService.getById(id);
        if (reply != null) {
            if (reply.getStatus() == 1) {
                return ResponseUtil.getFailResponse(2, "已删除");
            }
            reply.setStatus(1);
            replyService.updateById(reply);
            return ResponseUtil.getSuccessResponse(reply);
        }
        return ResponseUtil.getFailResponse("错误ID");
    }
    @PostMapping("")
    public ResponseEntity<?> addReply(@RequestBody Reply reply) {

        boolean res = replyService.save(reply);
        if (res) {
            return ResponseUtil.getSuccessResponse(reply);
        }
        return ResponseUtil.getFailResponse("服务异常");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReply(@PathVariable Long id, @RequestBody Reply reply) {
        Reply target = replyService.getById(id);
        if (target != null) {
            reply.setId(id);
            if (replyService.updateById(reply)) {
                return ResponseUtil.getSuccessResponse(reply);
            }
            return ResponseUtil.getFailResponse(2, "服务异常");
        }
        return ResponseUtil.getFailResponse("错误ID");
    }

}
