package net.project.ems.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.project.ems.entity.Debit;

@Getter
@Setter
@NoArgsConstructor
public class DebitDTO {
    private Long id;
    private String title;
    private Double amount;

    public DebitDTO(@JsonProperty("id") Long id,
                     @JsonProperty("title") String title,
                     @JsonProperty("amount") Double amount) {
        this.id = id;
        this.title = title;
        this.amount = amount;
    }


    //  Constructor to Convert Entity -> DTO
    public DebitDTO(Debit debit) {
        this.id = debit.getId();
        this.title = debit.getTitle();
        this.amount = debit.getAmount();
    }
}
