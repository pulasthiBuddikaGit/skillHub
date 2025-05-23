package com.paf.socialmedia.document.progress;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "progress")
public class Progress {
    @Id
    private String id;
    private String userId;
    private List<String> imgLink;
    private String caption;
    private List<String> likedby;
    private Date createdAt;
    private Date updatedAt;
}