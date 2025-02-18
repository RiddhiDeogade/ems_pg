package net.project.ems.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.project.ems.entity.Credit;

public class CreditDTO {
    private Long id;
    private String title;
    private Double amount;

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


    @JsonCreator
    public CreditDTO(@JsonProperty("id") Long id,
                     @JsonProperty("title") String title,
                     @JsonProperty("amount") Double amount) {
        this.id = id;
        this.title = title;
        this.amount = amount;
    }

    // Getters and setters
}

