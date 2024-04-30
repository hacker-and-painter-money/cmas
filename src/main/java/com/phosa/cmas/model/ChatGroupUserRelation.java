package com.phosa.cmas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatGroupUserRelation {

    private long id;
    private long groupId;
    private long userId;
    private long identity;
    private long status;
    private Date createdAt;
    private Date updatedAt;

}
