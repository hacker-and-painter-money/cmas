package com.phosa.cmas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentApprovalService {

    private final List<String> bannedWords;

    @Autowired
    public ContentApprovalService(List<String> bannedWords) {
        this.bannedWords = bannedWords;
    }

    public boolean isContentViolated(String... contents) {
        for (String content : contents) {
            if (content == null || content.isEmpty()) {
                continue; // 空内容视为通过审核
            }
            for (String bannedWord : bannedWords) {
                if (content.contains(bannedWord)) {
                    return true; // 如果包含违禁词，则审核不通过
                }
            }
        }
        return false; // 如果不包含任何违禁词，则审核通过
    }
}
