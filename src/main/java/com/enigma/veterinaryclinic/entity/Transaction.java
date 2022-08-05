package com.enigma.veterinaryclinic.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "trx_service")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String description;

    private Boolean payment;

    @Column(nullable = true)
    private Integer totalPrice;

    private Date checkoutAt;

    private String status;

    private Integer totalDuration;

    @CreatedDate
    @Column(updatable = false)
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    @ManyToOne(targetEntity = Operator.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "operator_id")
    private Operator operator;

    @ManyToOne(targetEntity = Doctor.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(targetEntity = TransactionDetail.class, fetch = FetchType.EAGER)
    @JsonManagedReference
    List<TransactionDetail> transactionDetails;

    @PrePersist
    private void insertBefore() {
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }
    }

}
