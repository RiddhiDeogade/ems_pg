package net.project.ems.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "credit")
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Double amount;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "cashbook_id", nullable = false)
    private Cashbook cashbook;

    public Credit() {
    }

}
