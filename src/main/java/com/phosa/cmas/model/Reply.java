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
public class Reply {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String content;
    @JsonProperty("sender_id")
    private Long senderId;
    @TableField(exist = false)
    private String senderName;
    @JsonProperty("question_id")
    private Long questionId;
    @JsonProperty("parent_reply_id")
    private Long parentReplyId;
    @TableField(exist = false)
    private String parentReplyContent;
    @TableField(fill = FieldFill.INSERT)
    private Long status;
    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;
    @TableField(fill = FieldFill.UPDATE)
    private Date updatedAt;

}
