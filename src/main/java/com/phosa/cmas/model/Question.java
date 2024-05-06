package com.phosa.cmas.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    private Long id;
    private String title;
    private String content;
    private Long senderId;
    @TableField(exist = false)
    private String senderName;
    private Long status;
    private Date createdAt;
    private Date updatedAt;

}
