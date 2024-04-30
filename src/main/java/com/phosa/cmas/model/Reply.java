package com.phosa.cmas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {

    private Long id;
    private String content;
    private Long senderId;
    private Long questionId;
    private Long parentReplyId;
    private Long status;
    private Date createdAt;
    private Date updatedAt;

}
