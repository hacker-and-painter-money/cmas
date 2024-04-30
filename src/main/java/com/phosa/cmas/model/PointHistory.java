package com.phosa.cmas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointHistory {

    private Long id;
    private Long userId;
    private Long changeAmount;
    private String reason;
    private Long status;
    private Date createdAt;


}
