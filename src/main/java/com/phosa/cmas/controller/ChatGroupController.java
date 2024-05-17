package com.phosa.cmas.controller;

import com.phosa.cmas.constant.ErrorResponse;
import com.phosa.cmas.model.ChatGroup;
import com.phosa.cmas.model.ChatGroupUserRelation;
import com.phosa.cmas.model.User;
import com.phosa.cmas.service.ChatGroupService;
import com.phosa.cmas.service.ChatGroupUserRelationService;
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
    @Autowired
    private ChatGroupUserRelationService chatGroupUserRelationService;

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

    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getChatGroupsByUser(@PathVariable(name = "user_id") Long userId,
                                           @RequestParam(name = "page", defaultValue = "1") int page,
                                           @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {

        List<ChatGroup> chatGroupList = chatGroupService.listByUserId(userId, page, pageSize);
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
        if (chatGroup.getType() == 0) {
            chatGroup.setName(chatGroup.getOwnerId() + "-" + chatGroup.getTargetId());
            if (!chatGroupService.getByName(chatGroup.getTargetId() + "-" + chatGroup.getOwnerId()).isEmpty()) {
                return ResponseUtil.getFailResponse(ErrorResponse.NAME_EXIST);
            }
        }
        List<ChatGroup> target = chatGroupService.getByName(chatGroup.getName());
        if (!target.isEmpty()) {
            return ResponseUtil.getFailResponse(ErrorResponse.NAME_EXIST);
        }
        boolean b = chatGroupService.save(chatGroup);
        if (b) {
            ChatGroup targetGroup = chatGroupService.getByName(chatGroup.getName()).get(0);
            ChatGroupUserRelation chatGroupUserRelation = new ChatGroupUserRelation();
            chatGroupUserRelation.setGroupId(targetGroup.getId());
            chatGroupUserRelation.setUserId(chatGroup.getOwnerId());
            chatGroupUserRelation.setIdentity(2L);
            chatGroupUserRelationService.save(chatGroupUserRelation);
            if (chatGroup.getType() == 0) {
                chatGroupUserRelation.setIdentity(-1L);
                chatGroupUserRelation.setUserId(chatGroup.getTargetId());
                chatGroupUserRelationService.save(chatGroupUserRelation);
            }
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
