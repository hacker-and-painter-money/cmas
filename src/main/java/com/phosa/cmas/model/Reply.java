package com.phosa.cmas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {

    private long id;
    private String content;
    private long senderId;
    private long questionId;
    private long parentReplyId;
    private long status;
    private Date createdAt;
    private Date updatedAt;

}
