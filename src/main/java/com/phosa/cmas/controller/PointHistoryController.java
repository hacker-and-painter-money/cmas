package com.phosa.cmas.controller;

import com.phosa.cmas.model.PointHistory;
import com.phosa.cmas.service.PointHistoryService;
import com.phosa.cmas.service.UserService;
import com.phosa.cmas.util.JsonUtil;
import com.phosa.cmas.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/points_history")
public class PointHistoryController {
    @Autowired
    PointHistoryService pointHistoryService;

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
        return ResponseUtil.getFailResponse("错误ID");
    }

    /**
     * 删除指定记录
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePointHistory(@PathVariable int id) {
        PointHistory target = pointHistoryService.getById(id);
        if (target != null && target.getId() == id) {
            if (target.getStatus() == 0) {
                return ResponseUtil.getFailResponse(2, "已删除");
            }
            target.setStatus(1);
            if (pointHistoryService.updateById(target)) {
                return ResponseUtil.getSuccessResponse(JsonUtil.toJson(target));
            }
            return ResponseUtil.getFailResponse(3, "服务异常");
        }
        return ResponseUtil.getFailResponse("错误ID");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePointHistory(@PathVariable Long id, @RequestBody PointHistory pointHistory) {
        PointHistory target = pointHistoryService.getById(id);
        if (target != null) {
            pointHistory.setId(id);
            if (pointHistoryService.updateById(pointHistory)) {
                return ResponseUtil.getSuccessResponse(pointHistory);
            }
            return ResponseUtil.getFailResponse(2, "服务异常");
        }
        return ResponseUtil.getFailResponse("错误ID");
    }

}
