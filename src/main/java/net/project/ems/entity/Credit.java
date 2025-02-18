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

    public Cashbook getCashbook() {
        return cashbook;
    }

    public void setCashbook(Cashbook cashbook) {
        this.cashbook = cashbook;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Credit() {
    }

    public Credit(String title, Double amount, Cashbook cashbook) {
        this.title = title;
        this.amount = amount;
        this.cashbook = cashbook;
    }
}
