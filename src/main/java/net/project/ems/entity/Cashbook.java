package net.project.ems.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@Table(name = "cashbook")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Add this
public class Cashbook {

    // Getters and Setters
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String name;

    @Column(name = "date_created")
    private LocalDate dateCreated;


    @Setter
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @Setter
    @OneToMany(mappedBy = "cashbook", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("cashbook")  // Ignore circular reference
    private List<Credit> credits;

    @Setter
    @OneToMany(mappedBy = "cashbook", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("cashbook")  // Ignore circular reference
    private List<Debit> debits;

    // PrePersist method to set the dateCreated to the current date before saving
    @PrePersist
    public void setDateCreated() {
        if (this.dateCreated == null) {
            this.dateCreated = LocalDate.now();
        }
    }


}
