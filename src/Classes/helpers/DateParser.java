package Classes.helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateParser {
    public final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate parse(String date) {
        return LocalDate.parse(date, FORMATTER);
    }

    public static LocalDate parse(String date, String pattern) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
    }
}