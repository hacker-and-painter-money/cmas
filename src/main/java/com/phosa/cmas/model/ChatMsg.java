package com.phosa.cmas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMsg {

    private long id;
    private long groupId;
    private long senderId;
    private String content;
    private long parentMsgId;
    private long status;
    private Date createdAt;
    private Date updatedAt;

}
