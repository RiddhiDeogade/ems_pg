package net.project.ems.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.project.ems.entity.Cashbook;
import net.project.ems.dto.CreditDTO;
import net.project.ems.dto.DebitDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor  // Add this for proper deserialization
public class CashbookDto {

    private Long id;
    private String name;
    private LocalDate dateCreated;
    private List<CreditDTO> credits;  // Separate DTOs to avoid lazy loading issues
    private List<DebitDTO> debits;

    // Constructor to map from Entity to DTO
    public CashbookDto(Cashbook cashbook) {
        this.id = cashbook.getId();
        this.name = cashbook.getName();
        this.dateCreated = cashbook.getDateCreated();
        this.credits = cashbook.getCredits().stream().map(CreditDTO::new).collect(Collectors.toList());
        this.debits = cashbook.getDebits().stream().map(DebitDTO::new).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "CashbookDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
