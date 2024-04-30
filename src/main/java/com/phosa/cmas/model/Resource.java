package com.phosa.cmas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resource {

    private long id;
    private String title;
    private String introduction;
    private String tag;
    private String filePath;
    private long ownerId;
    private long status;
    private Date createdAt;
    private Date updatedAt;

}
