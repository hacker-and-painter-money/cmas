package com.phosa.cmas.controller;

import com.phosa.cmas.constant.ErrorResponse;
import com.phosa.cmas.model.ChatGroup;
import com.phosa.cmas.service.ChatGroupService;
import com.phosa.cmas.util.DateUtil;
import com.phosa.cmas.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat_group")
public class ChatGroupController {

    @Autowired
    ChatGroupService chatGroupService;

    /**
     * 获取所有聊天群组列表。
     * http param： keyword 模糊查询
     */
    @GetMapping("")
    public ResponseEntity<?> getChatGroups(@RequestParam(required = false, defaultValue = "") String name,
                                            @RequestParam(required = false) Long type,
                                            @RequestParam(name = "page", defaultValue = "1") int page,
                                            @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        List<ChatGroup> chatGroupList = chatGroupService.list(name, type, page, pageSize);
        return ResponseUtil.getSuccessResponse(chatGroupList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getChatGroup(@PathVariable int id) {
        ChatGroup chatGroup = chatGroupService.getById(id);
        return ResponseUtil.getSuccessResponse(chatGroup);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChatGroup(@PathVariable int id) {
        ChatGroup chatGroup = chatGroupService.getById(id);
        if (chatGroup != null) {
            if (chatGroup.getStatus() == 1) {
                return ResponseUtil.getFailResponse(ErrorResponse.ALREADY_DELETED);
            }
            chatGroup.setStatus(1L);
            chatGroup.setUpdatedAt(DateUtil.getCurrentDate());
            chatGroupService.updateById(chatGroup);
            return ResponseUtil.getSuccessResponse(chatGroup);
        }
        return ResponseUtil.getFailResponse(ErrorResponse.INVALID_ID);
    }

    @PostMapping("")
    public ResponseEntity<?> addChatGroup(@RequestBody ChatGroup chatGroup) {
        boolean b = chatGroupService.save(chatGroup);
        if (b) {
            return ResponseUtil.getSuccessResponse(chatGroup);
        }
        return ResponseUtil.getFailResponse(ErrorResponse.SERVER_ERROR);
    }

    /**
     * @param id        目标id
     * @param chatGroup {
     *                  "name": "<name>"
     *                  "type": 0（单聊/好友）|1（群聊）
     *                  }
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateChatGroup(@PathVariable Long id, @RequestBody ChatGroup chatGroup) {
        ChatGroup target = chatGroupService.getById(id);
        if (target != null) {
            chatGroup.setId(id);
            target.setUpdatedAt(DateUtil.getCurrentDate());
            chatGroupService.updateById(chatGroup);
            return ResponseUtil.getSuccessResponse(chatGroup);
        }
        return ResponseUtil.getFailResponse(ErrorResponse.INVALID_ID);
    }

}
