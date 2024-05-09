package com.phosa.cmas.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatGroup {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    //0:私聊，1：群聊
    private Long type;
    @JsonProperty("owner_id")
    @TableField(exist = false)
    private Long ownerId;
    @JsonProperty("target_id")
    @TableField(exist = false)
    private Long targetId;
    @TableField(fill = FieldFill.INSERT)
    private Long status;
    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;
    @TableField(fill = FieldFill.UPDATE)
    private Date updatedAt;


}
