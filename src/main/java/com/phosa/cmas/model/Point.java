package com.phosa.cmas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Point {

    private long id;
    private long userId;
    private long totalPoints;
    private long status;
}
