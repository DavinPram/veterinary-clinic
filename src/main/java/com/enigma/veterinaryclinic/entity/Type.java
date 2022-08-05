package com.enigma.veterinaryclinic.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mst_type")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Type {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String id;

  @Column(nullable = false)
  private String type;

  @CreatedDate
  @Column(updatable = false)
  private Date createdAt;

  @LastModifiedDate
  private Date updatedAt;

  private Boolean isDeleted;

  @PrePersist
  private void insertBefore() {

    if (this.createdAt == null) this.createdAt = new Date();

    if (this.updatedAt == null) this.updatedAt = new Date();

    if (isDeleted == null) isDeleted = false;
  }

  @PreUpdate
  private void createdDate() {
    if (this.createdAt == null) this.createdAt = new Date();
    if (this.updatedAt == null) this.updatedAt = new Date();
  }

  public Type(String type, Date createdAt, Date updatedAt, Boolean isDeleted) {
    this.type = type;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.isDeleted = isDeleted;
  }
}
