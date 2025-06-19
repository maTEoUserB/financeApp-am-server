package pl.finances.finances_app.services;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ReceiptParser {
    public ParsedReceipt parse(String rawText) {
        String[] lines = rawText.split("\\n");

        // 1. Szukamy największej liczby (kwoty)
        double maxAmount = 0;
        for (String line : lines) {
            Matcher matcher = Pattern.compile("(\\d+[.,]\\d{2})").matcher(line);
            while (matcher.find()) {
                try {
                    double value = Double.parseDouble(matcher.group(1).replace(",", "."));
                    if (value > maxAmount) maxAmount = value;
                } catch (NumberFormatException ignored) {}
            }
        }

        // 2. Pierwsza niepusta linia jako tytuł
        String title = "Zakup";
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                title = line.trim();
                break;
            }
        }

        return new ParsedReceipt(title, maxAmount);
    }

    public record ParsedReceipt(String title, double amount) {}

}
