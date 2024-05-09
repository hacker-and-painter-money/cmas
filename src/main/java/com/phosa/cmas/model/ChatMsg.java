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
public class ChatMsg {

    @TableId(type = IdType.AUTO)
    private Long id;
    @JsonProperty("group_id")
    private Long groupId;
    @JsonProperty("sender_id")
    private Long senderId;
    @TableField(exist = false)
    private String senderName;
    private String content;
    @JsonProperty("parent_msg_id")
    private Long parentMsgId;
    @TableField(exist = false)
    private String parentMsgContent;
    @TableField(fill = FieldFill.INSERT)
    private Long status;
    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;
    @TableField(fill = FieldFill.UPDATE)
    private Date updatedAt;

}
