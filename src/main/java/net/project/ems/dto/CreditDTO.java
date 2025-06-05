package net.project.ems.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.project.ems.entity.Credit;

@Getter
@Setter
@NoArgsConstructor
public class CreditDTO {
    private Long id;
    private String title;
    private Double amount;

    // Constructor for JSON deserialization
    @JsonCreator
    public CreditDTO(@JsonProperty("id") Long id,
                     @JsonProperty("title") String title,
                     @JsonProperty("amount") Double amount) {
        this.id = id;
        this.title = title;
        this.amount = amount;
    }

    // Added Constructor to Convert Entity -> DTO
    public CreditDTO(Credit credit) {
        this.id = credit.getId();
        this.title = credit.getTitle();
        this.amount = credit.getAmount();
    }
}
