package com.paf.socialmedia.document.progress;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "workout_share")
public class ProgressShare {
    @Id
    private String id;
    private String caption;
    private String userId;
    @DBRef
    private Progress workout;
    private Date createdAt;
    private Date updatedAt;
}
