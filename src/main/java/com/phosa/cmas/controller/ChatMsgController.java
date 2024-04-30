package com.phosa.cmas.controller;

import com.phosa.cmas.constant.ErrorResponse;
import com.phosa.cmas.model.ChatMsg;
import com.phosa.cmas.service.ChatMsgService;
import com.phosa.cmas.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat_msg")
public class ChatMsgController {

    @Autowired
    ChatMsgService chatMsgService;


    @GetMapping("")
    public ResponseEntity<?> getChatMsgListByGroup(@RequestParam(name = "group_id") Long groupId,
                                                   @RequestParam(name = "sender_id") Long senderId,
                                                   @RequestParam(name = "content") String content,
                                                   @RequestParam(name = "parent_msg_id") Long parentMsgId,
                                                   @RequestParam(name = "page", defaultValue = "1") int page,
                                                   @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        List<ChatMsg> replies = chatMsgService.list(groupId, senderId, content, parentMsgId, page, pageSize);
        return ResponseUtil.getSuccessResponse(replies);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getChatMsg(@PathVariable int id) {
        ChatMsg chatMsg = chatMsgService.getById(id);
        return ResponseUtil.getSuccessResponse(chatMsg);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChatMsg(@PathVariable int id) {
        ChatMsg chatMsg = chatMsgService.getById(id);
        if (chatMsg != null) {
            if (chatMsg.getStatus() == 1) {
                return ResponseUtil.getFailResponse(ErrorResponse.ALREADY_DELETED);
            }
            chatMsg.setStatus(1L);
            chatMsgService.updateById(chatMsg);
            return ResponseUtil.getSuccessResponse(chatMsg);
        }
        return ResponseUtil.getFailResponse(ErrorResponse.INVALID_ID);
    }
    @PostMapping("")
    public ResponseEntity<?> addChatMsg(@RequestBody ChatMsg chatMsg) {

        boolean res = chatMsgService.save(chatMsg);
        if (res) {
            return ResponseUtil.getSuccessResponse(chatMsg);
        }
        return ResponseUtil.getFailResponse(ErrorResponse.SERVER_ERROR);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateChatMsg(@PathVariable Long id, @RequestBody ChatMsg chatMsg) {
        ChatMsg target = chatMsgService.getById(id);
        if (target != null) {
            chatMsg.setId(id);
            if (chatMsgService.updateById(chatMsg)) {
                return ResponseUtil.getSuccessResponse(chatMsg);
            }
            return ResponseUtil.getFailResponse(ErrorResponse.SERVER_ERROR);
        }
        return ResponseUtil.getFailResponse(ErrorResponse.INVALID_ID);
    }

}
