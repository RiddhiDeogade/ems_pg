package net.project.ems.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "debit")
public class Debit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Double amount;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "cashbook_id")
    private Cashbook cashbook;

    public Debit() {
    }


}
