package com.phosa.cmas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatGroupUserRelation {

    private Long id;
    private Long groupId;
    private Long userId;
    private Long identity;
    private Long status;
    private Date createdAt;
    private Date updatedAt;

}
