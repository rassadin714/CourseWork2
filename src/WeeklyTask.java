import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class WeeklyTask extends  Task{

    public WeeklyTask(String title, String description, Type type, LocalDateTime dateTime) {
        super(title, type, dateTime, description);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        DayOfWeek dateOfTask = LocalDate.from(getDateTime()).getDayOfWeek();
        DayOfWeek date = localDate.getDayOfWeek();
        return dateOfTask.equals(date);
    }
    @Override
    public String toString() {
        return super.toString() + ". Повторяемость - еженедельная";
    }
}
