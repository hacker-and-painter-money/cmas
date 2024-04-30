package com.phosa.cmas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointHistory {

    private long id;
    private long userId;
    private long changeAmount;
    private String reason;
    private long status;
    private Date createdAt;


}
