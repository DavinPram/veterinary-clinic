package com.enigma.veterinaryclinic.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mst_cage")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cage {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String cageName;

    private Boolean isUsed;

    @Column(updatable = false)
    private Date createdAt;

    private Date updateAt;

    private Boolean isDelete;

    private Date deleteAt;

    @PrePersist
    private void prePersist(){
        if (this.isUsed == null) isUsed = false;
        if (this.isDelete == null) isDelete = false;
        if (this.createdAt == null) createdAt = new Date();
        if (this.updateAt == null) updateAt = new Date();
    }

    @PreUpdate
    private void preUpdate(){
        if (this.createdAt == null) updateAt = new Date();
    }
}
