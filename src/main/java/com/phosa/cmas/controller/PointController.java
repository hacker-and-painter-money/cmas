package com.phosa.cmas.controller;

import com.phosa.cmas.constant.ErrorResponse;
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
@RequestMapping("/point")
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
        Point res = null;
        System.out.println(pointList);
        if (!pointList.isEmpty()) {
            res = pointList.get(0);
        } else {
            res = new Point();
            res.setTotalPoints(0L);
            res.setUserId(userId);
            addPoint(res);
        }
        return ResponseUtil.getSuccessResponse(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPoint(@PathVariable Long id) {
        Point point = pointService.getById(id);
        return ResponseUtil.getSuccessResponse(point);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePoint(@PathVariable Long id) {
        Point point = pointService.getById(id);
        if (point != null) {
            point.setStatus(1L);
            if (pointService.updateById(point)) {
                return ResponseUtil.getSuccessResponse(point);
            }
            return ResponseUtil.getFailResponse(ErrorResponse.SERVER_ERROR);
        }
        return ResponseUtil.getFailResponse(ErrorResponse.INVALID_ID);
    }
    @PostMapping("")
    public ResponseEntity<?> addPoint(@RequestBody Point point) {
        Point target = pointService.getById(point.getUserId());
        if (target != null) {

            return ResponseUtil.getFailResponse(ErrorResponse.USER_ID_EXIST);
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
        return ResponseUtil.getFailResponse(ErrorResponse.INVALID_ID);
    }

}
