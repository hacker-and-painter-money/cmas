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
public class Question {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    @JsonProperty("sender_id")
    private Long senderId;
    @TableField(exist = false)
    private String senderName;
    @TableField(fill = FieldFill.INSERT)
    private Long status;
    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;
    @TableField(exist = false)
    private String createAtFormated;
    @TableField(fill = FieldFill.UPDATE)
    private Date updatedAt;
    @TableField(exist = false)
    private String updateAtFormated;

}
