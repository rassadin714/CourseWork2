import java.time.LocalDate;
import java.time.LocalDateTime;

public class MonthlyTask extends Task{
    public MonthlyTask(String title, String description, Type type, LocalDateTime dateTime) {
        super(title, type, dateTime, description);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        int date = localDate.getDayOfMonth();
        int dateOfTask = LocalDate.from(getDateTime()).getDayOfMonth();
        return dateOfTask == date;
    }
    @Override
    public String toString() {
        return super.toString() + ". Повторяемость - ежемесячная";
    }
}
