package com.phosa.cmas.controller;

import com.phosa.cmas.constant.ErrorResponse;
import com.phosa.cmas.model.PointHistory;
import com.phosa.cmas.model.Question;
import com.phosa.cmas.model.Question;
import com.phosa.cmas.service.PointHistoryService;
import com.phosa.cmas.service.QuestionService;
import com.phosa.cmas.util.JsonUtil;
import com.phosa.cmas.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    PointHistoryService pointHistoryService;

    @GetMapping("")
    public ResponseEntity<?> getQuestionList(@RequestParam(required = false) String title,
                                            @RequestParam(required = false) String content,
                                            @RequestParam(required = false, name = "sender_id") Long senderId,
                                            @RequestParam(name = "page", defaultValue = "1") int page,
                                            @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        List<Question> questionList = questionService.list(title, content, senderId, page, pageSize);
        return ResponseUtil.getSuccessResponse(questionList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestion(@PathVariable int id) {
        Question question = questionService.getById(id);
        return ResponseUtil.getSuccessResponse(question);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable int id) {
        Question question = questionService.getById(id);
        if (question != null) {
            if (question.getStatus() == 1) {
                return ResponseUtil.getFailResponse(ErrorResponse.ALREADY_DELETED);
            }
            question.setStatus(1L);
            questionService.updateById(question);
            return ResponseUtil.getSuccessResponse(question);
        }
        return ResponseUtil.getFailResponse(ErrorResponse.INVALID_ID);
    }
    @PostMapping("")
    public ResponseEntity<?> addQuestion(@RequestBody Question question) {
        boolean res = questionService.save(question);
        if (res) {
            PointHistory pointHistory = new PointHistory();
            pointHistory.setChangeAmount(1L);
            pointHistory.setUserId(question.getSenderId());
            pointHistory.setReason(1L);
            pointHistoryService.changePoint(pointHistory);
            return ResponseUtil.getSuccessResponse(question);
        }
        return ResponseUtil.getFailResponse(ErrorResponse.SERVER_ERROR);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
        Question target = questionService.getById(id);
        if (target != null) {
            question.setId(id);
            if (questionService.updateById(question)) {
                return ResponseUtil.getSuccessResponse(question);
            }
            return ResponseUtil.getFailResponse(ErrorResponse.SERVER_ERROR);
        }
        return ResponseUtil.getFailResponse(ErrorResponse.INVALID_ID);
    }

}
