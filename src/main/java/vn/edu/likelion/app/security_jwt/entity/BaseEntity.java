package vn.edu.likelion.app.security_jwt.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column()
    Boolean isDeleted = false;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createAt;

    @UpdateTimestamp
    @Column(nullable = true, insertable = false)
    LocalDateTime updateAt;

//    @PrePersist
//    protected void onCreate() {
//        this.createAt =LocalDateTime.now();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        updateAt = LocalDateTime.now();
//    }
}
