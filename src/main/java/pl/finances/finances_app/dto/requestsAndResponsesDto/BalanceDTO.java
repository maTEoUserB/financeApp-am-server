package pl.finances.finances_app.dto.requestsAndResponsesDto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceDTO {
    private Double balance;
    private Double euroBalance;
}
