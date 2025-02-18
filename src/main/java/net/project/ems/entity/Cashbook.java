package net.project.ems.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cashbooks")
public class Cashbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "cashbook", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Credit> credits;

    @OneToMany(mappedBy = "cashbook" , cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Debit> debits;

    // PrePersist method to set the dateCreated to the current date before saving
    @PrePersist
    public void setDateCreated() {
        if (this.dateCreated == null) {
            this.dateCreated = LocalDate.now();
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    public List<Debit> getDebits() {
        return debits;
    }

    public void setDebits(List<Debit> debits) {
        this.debits = debits;
    }
}
