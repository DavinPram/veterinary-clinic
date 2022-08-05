package com.enigma.veterinaryclinic.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "trx_inpatient")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InPatient {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @ManyToOne(targetEntity = Doctor.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(targetEntity = Operator.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "operator_id")
    private Operator operator;

    @ManyToOne(targetEntity = Animal.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @OneToOne(targetEntity = Cage.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "cage_id")
    private Cage cage;

    private String description;

    private Boolean payment;

    private Integer price;

    private Date checkoutAt;

    private String status;

    @CreatedDate
    private Date createdAt;

    @PrePersist
    private void prePersist(){
        this.checkoutAt = null;
        this.payment = null;
        if (this.createdAt == null) createdAt = new Date();
    }

    @Override
    public String toString() {
        return "InPatient{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", payment=" + payment +
                ", price=" + price +
                ", checkoutAt=" + checkoutAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
