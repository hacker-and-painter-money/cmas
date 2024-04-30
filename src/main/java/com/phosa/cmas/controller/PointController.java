package com.phosa.cmas.controller;

import com.phosa.cmas.model.Point;
import com.phosa.cmas.service.PointService;
import com.phosa.cmas.util.JsonUtil;
import com.phosa.cmas.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/points")
public class PointController {

    @Autowired
    PointService pointService;


    @GetMapping("")
    public ResponseEntity<?> getPointList() {
        List<Point> pointList = pointService.list();
        return ResponseUtil.getSuccessResponse(pointList);
    }
    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getPointList(@PathVariable(name = "user_id") Long userId) {
        List<Point> pointList = pointService.getByUserId(userId);
        return ResponseUtil.getSuccessResponse(pointList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPoint(@PathVariable Long id) {
        Point point = pointService.getById(id);
        return ResponseUtil.getSuccessResponse(point);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePoint(@PathVariable Long id) {
        Point point = pointService.getById(id);
        if (point != null && point.getId() == id) {
            point.setStatus(1);
            pointService.updateById(point);
            return ResponseUtil.getSuccessResponse(point);
        }
        return ResponseUtil.getFailResponse("错误ID");
    }
    @PostMapping("")
    public ResponseEntity<?> addPoint(@RequestBody Point point) {
        Point target = pointService.getById(point.getUserId());
        if (target != null) {

            return ResponseUtil.getFailResponse("信息已存在");
        }
        pointService.save(point);
        return ResponseUtil.getSuccessResponse(point);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePoint(@PathVariable Long id, @RequestBody Point point) {
        Point target = pointService.getById(id);
        if (target != null && target.getId() == id) {
            point.setId(id);
            pointService.updateById(point);
            return ResponseUtil.getSuccessResponse(point);
        }
        return ResponseUtil.getFailResponse("错误ID");
    }

}
