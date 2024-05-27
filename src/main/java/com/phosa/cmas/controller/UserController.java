package com.phosa.cmas.controller;

import com.phosa.cmas.constant.ErrorResponse;
import com.phosa.cmas.model.User;
import com.phosa.cmas.service.UserService;
import com.phosa.cmas.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    EmailUtil emailUtil;

    @GetMapping("/send_verification_code")
    public ResponseEntity<?> sendVerificationCode(@RequestParam String email) {
        String key = "cmas:vc:" + email;
        if (redisUtil.hasKey(key)) {
            return ResponseUtil.getFailResponse(ErrorResponse.VERIFICATION_CODE_ALREADY_SEND);
        }
        String vc = RandomGeneratorUtil.generateVerificationCode();
        redisUtil.set(key, vc, 120);
        try {
            emailUtil.sendEmail(email, "CMAS注册验证码", "您的注册码是:" + vc);
        } catch (MessagingException e) {
            return ResponseUtil.getResponse(-1, e.getMessage(), null);
        }
        log.info("邮箱：{}，验证码：{}", email, vc);
        return ResponseUtil.getSuccessResponse("发送成功");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        List<User> userList = userService.getByUsername(user.getUsername());
        if (!userList.isEmpty()) {
            User target = userList.get(0);
            if (target.getStatus() == 1) {
                return ResponseUtil.getFailResponse(ErrorResponse.ALREADY_DELETED);
            }
            try {
                if (target.getPassword().equals(AesEncryptUtil.encrypt(user.getPassword()))) {
                    return ResponseUtil.getSuccessResponse(target);
                }
            } catch (Exception e) {
                return ResponseUtil.getFailResponse(ErrorResponse.SERVER_ERROR);
            }
            return ResponseUtil.getFailResponse(ErrorResponse.WRONG_PASSWORD);
        }
        return ResponseUtil.getFailResponse(ErrorResponse.USERNAME_NOT_EXIST);
    }

    @GetMapping("")
    public ResponseEntity<?> getUserList(@RequestParam(required = false, defaultValue = "") String username,
                                        @RequestParam(required = false, name = "except_friend_id") Long exceptFriendId,
                                        @RequestParam(name = "page", defaultValue = "1") int page,
                                        @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        List<User> userList = userService.list(username, exceptFriendId);
        return ResponseUtil.getSuccessResponse(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id) {
        User user = userService.getById(id);
        return ResponseUtil.getSuccessResponse(user);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        User user = userService.getById(id);
        if (user != null) {
            if (user.getStatus() == 1) {
                return ResponseUtil.getFailResponse(ErrorResponse.ALREADY_DELETED);
            }
            user.setStatus(1L);
            if (userService.updateById(user)) {
                return ResponseUtil.getSuccessResponse(user);
            }
            return ResponseUtil.getFailResponse(ErrorResponse.SERVER_ERROR);
        }
        return ResponseUtil.getFailResponse(ErrorResponse.INVALID_ID);
    }
    @PostMapping("")
    public ResponseEntity<?> addUser(@RequestBody User user) {

        String key = "cmas:vc:" + user.getEmail();
        if (!redisUtil.hasKey(key) || user.getVerificationCode() == null || !redisUtil.get(key).equals(user.getVerificationCode())) {
            return ResponseUtil.getFailResponse(ErrorResponse.VERIFICATION_CODE_ERROR);
        }
        redisUtil.del(key);
        List<User> target = userService.getByUsername(user.getUsername());
        if (!target.isEmpty()) {
            return ResponseUtil.getFailResponse(ErrorResponse.USERNAME_EXIST);
        }
        try {
            user.setPassword(AesEncryptUtil.encrypt(user.getPassword()));
        } catch (Exception e) {
            return ResponseUtil.getFailResponse(ErrorResponse.SERVER_ERROR);
        }
        if (userService.save(user)) {
            return ResponseUtil.getSuccessResponse(user);
        }
        return ResponseUtil.getFailResponse(ErrorResponse.SERVER_ERROR);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        User target = userService.getById(id);
        if (target != null) {
            target.setId(id);
            if (userService.updateById(user)) {
                return ResponseUtil.getSuccessResponse(user);
            }
            return ResponseUtil.getFailResponse(ErrorResponse.SERVER_ERROR);

        }
        return ResponseUtil.getFailResponse(ErrorResponse.INVALID_ID);
    }

}
