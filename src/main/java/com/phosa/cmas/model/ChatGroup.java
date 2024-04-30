package com.phosa.cmas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatGroup {

    private long id;
    private String name;
    //0:私聊，1：群聊
    private long type;
    private long status;
    private Date createdAt;
    private Date updatedAt;


}
