package pl.finances.finances_app.dto.requestsAndResponsesDto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterAccountDTO {
    private String username;
    private String password;
    private Double balance;
}
