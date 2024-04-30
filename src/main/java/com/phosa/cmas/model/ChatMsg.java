package com.phosa.cmas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMsg {

    private Long id;
    private Long groupId;
    private Long senderId;
    private String content;
    private Long parentMsgId;
    private Long status;
    private Date createdAt;
    private Date updatedAt;

}
