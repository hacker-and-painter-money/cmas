package com.phosa.cmas.controller;

import com.phosa.cmas.model.Resource;
import com.phosa.cmas.model.Resource;
import com.phosa.cmas.service.ResourceService;
import com.phosa.cmas.util.JsonUtil;
import com.phosa.cmas.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    ResourceService resourceService;


    @GetMapping("")
    public ResponseEntity<?> getResourceList(@RequestParam(required = false) String title, 
                                            @RequestParam(required = false) String tag, 
                                            @RequestParam(required = false, name = "owner_id")String ownerId,
                                            @RequestParam(name = "page", defaultValue = "1") int page,
                                            @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        List<Resource> resourceList = resourceService.list(title, tag, ownerId, page, pageSize);
        return ResponseUtil.getSuccessResponse(resourceList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getResource(@PathVariable int id) {
        Resource resource = resourceService.getById(id);
        return ResponseUtil.getSuccessResponse(resource);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResource(@PathVariable int id) {
        Resource resource = resourceService.getById(id);
        if (resource != null) {
            if (resource.getStatus() == 1) {
                return ResponseUtil.getFailResponse(2, "已删除");
            }
            resource.setStatus(1);
            resourceService.updateById(resource);
            return ResponseUtil.getSuccessResponse(resource);
        }
        return ResponseUtil.getFailResponse("错误ID");
    }
    @PostMapping("")
    public ResponseEntity<?> addResource(@RequestBody Resource resource) {

        boolean res = resourceService.save(resource);
        if (res) {
            return ResponseUtil.getSuccessResponse(resource);
        }
        return ResponseUtil.getFailResponse("服务异常");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateResource(@PathVariable Long id, @RequestBody Resource resource) {
        Resource target = resourceService.getById(id);
        if (target != null) {
            resource.setId(id);
            if (resourceService.updateById(resource)) {
                return ResponseUtil.getSuccessResponse(resource);
            }
            return ResponseUtil.getFailResponse(2, "服务异常");
        }
        return ResponseUtil.getFailResponse("错误ID");
    }

}
