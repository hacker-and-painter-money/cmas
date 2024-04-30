package com.phosa.cmas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatGroup {

    private Long id;
    private String name;
    //0:私聊，1：群聊
    private Long type;
    private Long status;
    private Date createdAt;
    private Date updatedAt;


}
