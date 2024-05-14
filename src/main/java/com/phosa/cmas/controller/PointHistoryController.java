package com.phosa.cmas.controller;

import com.phosa.cmas.constant.ErrorResponse;
import com.phosa.cmas.model.Point;
import com.phosa.cmas.model.PointHistory;
import com.phosa.cmas.model.PointHistory;
import com.phosa.cmas.service.PointHistoryService;
import com.phosa.cmas.service.PointService;
import com.phosa.cmas.service.UserService;
import com.phosa.cmas.util.JsonUtil;
import com.phosa.cmas.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/point_history")
public class PointHistoryController {
    @Autowired
    PointHistoryService pointHistoryService;

    @Autowired
    PointService pointService;

    @Autowired
    UserService userService;

    /**
     * 所有积分变化历史
     */
    @GetMapping("")
    public ResponseEntity<?> getPointHistoryList(@RequestParam(name = "user_id") Long userId,
                                                    @RequestParam(name = "page", defaultValue = "1") int page,
                                                    @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        List<PointHistory> pointHistoryList = pointHistoryService.list(userId, page, pageSize);
        return ResponseUtil.getSuccessResponse(pointHistoryList);
    }

    /**
     * 获取指定积分变化历史记录
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getPointHistory(@PathVariable int id) {
        PointHistory pointHistory = pointHistoryService.getById(id);
        if (pointHistory != null) {
            return ResponseUtil.getSuccessResponse(pointHistory);
        }
        return ResponseUtil.getFailResponse(ErrorResponse.INVALID_ID);
    }

    /**
     * 删除指定记录
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePointHistory(@PathVariable int id) {
        PointHistory target = pointHistoryService.getById(id);
        if (target != null && target.getId() == id) {
            if (target.getStatus() == 0) {
                return ResponseUtil.getFailResponse(ErrorResponse.ALREADY_DELETED);
            }
            target.setStatus(1L);
            if (pointHistoryService.updateById(target)) {
                return ResponseUtil.getSuccessResponse(JsonUtil.toJson(target));
            }
            return ResponseUtil.getFailResponse(ErrorResponse.SERVER_ERROR);
        }
        return ResponseUtil.getFailResponse(ErrorResponse.INVALID_ID);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePointHistory(@PathVariable Long id, @RequestBody PointHistory pointHistory) {
        PointHistory target = pointHistoryService.getById(id);
        if (target != null) {
            pointHistory.setId(id);
            if (pointHistoryService.updateById(pointHistory)) {
                return ResponseUtil.getSuccessResponse(pointHistory);
            }
            return ResponseUtil.getFailResponse(ErrorResponse.SERVER_ERROR);
        }
        return ResponseUtil.getFailResponse(ErrorResponse.INVALID_ID);
    }
    @PostMapping("")
    public ResponseEntity<?> addPointHistory(@RequestBody PointHistory pointHistory) {
        if (pointHistoryService.changePoint(pointHistory)) {
            return ResponseUtil.getSuccessResponse(pointHistory);
        }
        return ResponseUtil.getFailResponse(ErrorResponse.SERVER_ERROR);
    }
}
