package pl.finances.finances_app.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ReceiptData {
    private final String title;
    private final double amount;
    private final String rawText;
    private final LocalDateTime date;
}
