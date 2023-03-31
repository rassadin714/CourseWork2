import java.time.LocalDate;
import java.time.LocalDateTime;

public class YearlyTask extends Task {
    public YearlyTask(String title, String description, Type type, LocalDateTime dateTime) {
        super(title, type, dateTime, description);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        int date = localDate.getDayOfYear();
        int dateOfTask = LocalDate.from(getDateTime()).getDayOfYear();
        return dateOfTask == date;
    }

    @Override
    public String toString() {
        return super.toString() + ". Повторяемость - ежегодная";
    }
}
