package com.phosa.cmas.controller;

import com.phosa.cmas.constant.ErrorResponse;
import com.phosa.cmas.model.ChatGroup;
import com.phosa.cmas.model.ChatGroupUserRelation;
import com.phosa.cmas.model.User;
import com.phosa.cmas.service.ChatGroupService;
import com.phosa.cmas.service.ChatGroupUserRelationService;
import com.phosa.cmas.service.UserService;
import com.phosa.cmas.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat_group_user_relation")
public class ChatGroupUserRelationController {
    @Autowired
    ChatGroupUserRelationService chatGroupUserRelationService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatGroupService chatGroupService;


    @GetMapping("")
    public ResponseEntity<?> getChatGroupUserRelations(@RequestParam(name = "group_id", required = false) Long groupId,
                                                        @RequestParam(name = "user_id", required = false) Long userId,
                                                        @RequestParam(name = "page", defaultValue = "1") int page,
                                                        @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        List<ChatGroupUserRelation> chatGroupUserRelationList = chatGroupUserRelationService.list(groupId, userId, page, pageSize);
        return ResponseUtil.getSuccessResponse(chatGroupUserRelationList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getChatGroupUserRelation(@PathVariable Long id) {
        ChatGroupUserRelation chatGroupUserRelation = chatGroupUserRelationService.getById(id);
        return ResponseUtil.getSuccessResponse(chatGroupUserRelation);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChatGroupUserRelation(@PathVariable Long id) {
        ChatGroupUserRelation chatGroupUserRelation = chatGroupUserRelationService.getById(id);
        if (chatGroupUserRelation != null) {
            chatGroupUserRelation.setStatus(1L);
            chatGroupUserRelationService.removeById(id);
            return ResponseUtil.getSuccessResponse(chatGroupUserRelation);
        }
        return ResponseUtil.getFailResponse(ErrorResponse.INVALID_ID);
    }
    @PostMapping("")
    public ResponseEntity<?> addChatGroupUserRelation(@RequestBody ChatGroupUserRelation chatGroupUserRelation) {
        User user = userService.getById(chatGroupUserRelation.getUserId());
        if (user == null) {
            return ResponseUtil.getFailResponse(ErrorResponse.USER_ID_NOT_EXIST);
        }
        ChatGroup chatGroup = chatGroupService.getById(chatGroupUserRelation.getGroupId());
        if (chatGroup == null) {
            return ResponseUtil.getFailResponse(ErrorResponse.CHAT_GROUP_ID_NOT_EXIST);
        }
        boolean exist = chatGroupUserRelationService.exist(chatGroupUserRelation.getUserId(), chatGroupUserRelation.getGroupId());
        if (exist) {
            return ResponseUtil.getFailResponse(ErrorResponse.USER_ALREADY_JOINED);
        }
        boolean b = chatGroupUserRelationService.save(chatGroupUserRelation);
        if (b) {
            return ResponseUtil.getSuccessResponse(chatGroupUserRelation);
        }
        return ResponseUtil.getFailResponse(ErrorResponse.SERVER_ERROR);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateChatGroupUserRelation(@PathVariable Long id, @RequestBody ChatGroupUserRelation chatGroupUserRelation) {
        ChatGroupUserRelation target = chatGroupUserRelationService.getById(id);
        if (target != null && target.getId() == id) {
            chatGroupUserRelation.setId(id);
            chatGroupUserRelationService.updateById(chatGroupUserRelation);
            return ResponseUtil.getSuccessResponse(chatGroupUserRelation);
        }
        return ResponseUtil.getFailResponse(ErrorResponse.INVALID_ID);
    }

}
