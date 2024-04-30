package com.phosa.cmas.controller;

import com.phosa.cmas.constant.ErrorResponse;
import com.phosa.cmas.model.User;
import com.phosa.cmas.service.UserService;
import com.phosa.cmas.util.AesEncryptUtil;
import com.phosa.cmas.util.JsonUtil;
import com.phosa.cmas.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

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
                                        @RequestParam(name = "page", defaultValue = "1") int page,
                                        @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        List<User> userList = userService.list(username, page, pageSize);
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
        List<User> target = userService.getByUsername(user.getUsername());
        if (target.isEmpty()) {
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
