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
@Table(name = "mst_doctor")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Doctor {
  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String id;

  @Column(nullable = false)
  private String name;

  private String alumni;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(columnDefinition = "TEXT")
  private String experience;

  private String sip;

  @Lob
  private byte[] image;

  @CreatedDate
  @Column(updatable = false)
  private Date createdAt;

  @LastModifiedDate
  private Date updatedAt;

  private Boolean isDeleted;

  @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User userId;

  @PrePersist
  private void insertBefore() {

    if (this.createdAt == null) this.createdAt = new Date();

    if (this.updatedAt == null) this.updatedAt = new Date();

    if (isDeleted == null) isDeleted = false;
  }

  @PreUpdate
  private void createdDate() {
    if (this.updatedAt == null) this.updatedAt = new Date();
  }

  public Doctor(String name, String alumni, String description, String experience, String sip, byte[] image, Date createdAt, Date updatedAt, Boolean isDeleted) {
    this.name = name;
    this.alumni = alumni;
    this.description = description;
    this.experience = experience;
    this.sip = sip;
    this.image = image;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.isDeleted = isDeleted;
  }
}
