package com.example.filesharing.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String originalName;

    private String storageName;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "uploaded_at")
    private Date uploadedAt;

    public void setUser(User user) {
        this.user = user;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }
}
