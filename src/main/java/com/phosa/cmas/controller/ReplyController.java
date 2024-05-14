package com.phosa.cmas.controller;

import com.phosa.cmas.constant.ErrorResponse;
import com.phosa.cmas.model.PointHistory;
import com.phosa.cmas.model.Reply;
import com.phosa.cmas.service.PointHistoryService;
import com.phosa.cmas.service.ReplyService;
import com.phosa.cmas.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    ReplyService replyService;

    @Autowired
    PointHistoryService pointHistoryService;

    @GetMapping("")
    public ResponseEntity<?> getReplyList(@RequestParam(required = false, name = "question_id") Long questionId,
                                          @RequestParam(required = false, name = "reply_id") Long replyId,
                                          @RequestParam(required = false, name = "sender_id") Long senderId,
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
                return ResponseUtil.getFailResponse(ErrorResponse.ALREADY_DELETED);
            }
            reply.setStatus(1L);
            replyService.updateById(reply);
            return ResponseUtil.getSuccessResponse(reply);
        }
        return ResponseUtil.getFailResponse(ErrorResponse.INVALID_ID);
    }
    @PostMapping("")
    public ResponseEntity<?> addReply(@RequestBody Reply reply) {
        boolean res = replyService.save(reply);
        if (res) {
            
            PointHistory pointHistory = new PointHistory();
            pointHistory.setChangeAmount(1L);
            pointHistory.setUserId(reply.getSenderId());
            pointHistory.setReason(2L);
            pointHistoryService.changePoint(pointHistory);
            return ResponseUtil.getSuccessResponse(reply);
        }
        return ResponseUtil.getFailResponse(ErrorResponse.SERVER_ERROR);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReply(@PathVariable Long id, @RequestBody Reply reply) {
        Reply target = replyService.getById(id);
        if (target != null) {
            reply.setId(id);
            if (replyService.updateById(reply)) {
                return ResponseUtil.getSuccessResponse(reply);
            }
            return ResponseUtil.getFailResponse(ErrorResponse.SERVER_ERROR);
        }
        return ResponseUtil.getFailResponse(ErrorResponse.INVALID_ID);
    }

}
