package com.enigma.veterinaryclinic.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mst_Operator")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Operator {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User userId;
    private String name;
    @Column(updatable = false)
    private Date createdAt;
    private Date updatedAt;
    private Boolean isDeleted;
    private Date deletedAt;

    @PrePersist
    private void prePersist(){
        if (this.isDeleted == null) isDeleted = false;
        if (this.createdAt == null) createdAt = new Date();
        if (this.updatedAt == null) updatedAt = new Date();
    }

    @PreUpdate
    private void preUpdate(){
        if (this.updatedAt == null) updatedAt = new Date();
    }
}
